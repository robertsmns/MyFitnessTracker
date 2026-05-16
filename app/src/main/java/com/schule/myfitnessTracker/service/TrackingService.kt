package com.schule.myfitnessTracker.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.os.Looper
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.*
import com.schule.myfitnessTracker.R
import com.schule.myfitnessTracker.data.db.FitnessDatabase
import com.schule.myfitnessTracker.data.db.FitnessRepository
import com.schule.myfitnessTracker.data.model.RoutePoint
import com.schule.myfitnessTracker.ui.MainActivity
import kotlinx.coroutines.*
import kotlin.math.roundToInt

/**
 * Foreground Service für GPS-Tracking.
 *
 * Läuft im Hintergrund und:
 *  - Empfängt GPS-Updates (alle 3 Sekunden)
 *  - Zählt Schritte via Step Detector Sensor
 *  - Berechnet Distanz, Geschwindigkeit und Kalorien
 *  - Speichert jeden GPS-Punkt in der Room-DB
 *  - Zeigt eine persistente Benachrichtigung
 */
class TrackingService : LifecycleService(), SensorEventListener {

    companion object {
        const val ACTION_START = "ACTION_START_TRACKING"
        const val ACTION_STOP  = "ACTION_STOP_TRACKING"
        const val ACTION_PAUSE = "ACTION_PAUSE_TRACKING"

        private const val CHANNEL_ID = "fitness_tracking_channel"
        private const val NOTIFICATION_ID = 1

        // GPS Intervall (Millisekunden)
        private const val LOCATION_INTERVAL_MS = 3000L
        private const val LOCATION_FASTEST_MS  = 1500L

        // Statische LiveData – bleibt über Fragment-Wechsel hinweg erhalten
        val isTracking   = MutableLiveData(false)
        val currentRunId = MutableLiveData<Long?>(null)
        val distanceM    = MutableLiveData(0f)        // Meter
        val speedKmh     = MutableLiveData(0f)
        val stepCount    = MutableLiveData(0)
        val elapsedSec   = MutableLiveData(0L)
        val lastLocation = MutableLiveData<Location?>(null)
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var sensorManager: SensorManager
    private lateinit var repository: FitnessRepository

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private var prevLocation: Location? = null
    private var totalDistance = 0f
    private var startTime = 0L
    private var timerJob: Job? = null
    private var stepsAtStart = 0
    private var totalStepsRaw = 0
    private var isPaused = false

    // ── Lifecycle ────────────────────────────────────────────────────────────

    override fun onCreate() {
        super.onCreate()
        val db = FitnessDatabase.getInstance(applicationContext)
        repository = FitnessRepository(db)

        setupLocationClient()
        setupSensors()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        when (intent?.action) {
            ACTION_START -> startTracking()
            ACTION_STOP  -> stopTracking()
            ACTION_PAUSE -> pauseTracking()
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
        sensorManager.unregisterListener(this)
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    // ── Setup ────────────────────────────────────────────────────────────────

    private fun setupLocationClient() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                if (!isPaused) {
                    result.lastLocation?.let { location ->
                        handleNewLocation(location)
                    }
                }
            }
        }
    }

    private fun setupSensors() {
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        // Step Counter: gibt Gesamtschrittzahl seit letztem Neustart zurück
        val stepCounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        stepCounter?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    // ── Tracking starten ─────────────────────────────────────────────────────

    private fun startTracking() {
        startForeground(NOTIFICATION_ID, buildNotification("GPS wird verbunden…"))

        totalDistance = 0f
        prevLocation  = null
        startTime     = System.currentTimeMillis()
        stepsAtStart  = totalStepsRaw
        isPaused      = false

        isTracking.postValue(true)
        distanceM.postValue(0f)
        speedKmh.postValue(0f)
        stepCount.postValue(0)

        // Neuen Run in DB anlegen
        serviceScope.launch {
            val runId = repository.startNewRun()
            currentRunId.postValue(runId)
        }

        requestLocationUpdates()
        startTimer()
    }

    private fun stopTracking() {
        isPaused = false
        isTracking.postValue(false)
        timerJob?.cancel()
        fusedLocationClient.removeLocationUpdates(locationCallback)

        // Run in DB abschließen
        val runId = currentRunId.value ?: return
        serviceScope.launch {
            val steps    = stepCount.value ?: 0
            val calories = calculateCalories(totalDistance, steps)
            val avgSpeed = if (elapsedSec.value!! > 0)
                (totalDistance / 1000f) / (elapsedSec.value!! / 3600f)
            else 0f

            repository.finishRun(
                runId          = runId,
                distanceMeters = totalDistance,
                avgSpeedKmh    = avgSpeed,
                steps          = steps,
                calories       = calories,
                elevationGain  = 0f   // Optional: aus RoutePoints berechnen
            )
        }

        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    private fun pauseTracking() {
        isPaused = !isPaused
        if (isPaused) {
            timerJob?.cancel()
        } else {
            startTimer()
        }
    }

    // ── GPS ──────────────────────────────────────────────────────────────────

    private fun requestLocationUpdates() {
        val request = LocationRequest.Builder(LOCATION_INTERVAL_MS)
            .setMinUpdateIntervalMillis(LOCATION_FASTEST_MS)
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .build()

        try {
            fusedLocationClient.requestLocationUpdates(
                request,
                locationCallback,
                Looper.getMainLooper()
            )
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    private fun handleNewLocation(location: Location) {
        lastLocation.postValue(location)

        // Distanz berechnen
        prevLocation?.let { prev ->
            val delta = prev.distanceTo(location)
            if (delta > 1f) {   // < 1m ignorieren (GPS-Rauschen)
                totalDistance += delta
                distanceM.postValue(totalDistance)
            }
        }
        prevLocation = location

        // Geschwindigkeit (m/s → km/h)
        val speed = if (location.hasSpeed()) location.speed * 3.6f else 0f
        speedKmh.postValue(speed)

        // GPS-Punkt in DB speichern
        val runId = currentRunId.value ?: return
        serviceScope.launch {
            repository.addRoutePoint(
                RoutePoint(
                    runId     = runId,
                    latitude  = location.latitude,
                    longitude = location.longitude,
                    altitude  = location.altitude,
                    speed     = location.speed,
                    accuracy  = location.accuracy
                )
            )
        }

        // Benachrichtigung aktualisieren
        updateNotification()
    }

    // ── Sensor Callbacks ─────────────────────────────────────────────────────

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_STEP_COUNTER) {
            val total = event.values[0].roundToInt()
            if (stepsAtStart == 0) stepsAtStart = total
            totalStepsRaw = total
            val steps = total - stepsAtStart
            stepCount.postValue(steps)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) { /* nicht benötigt */ }

    // ── Timer ────────────────────────────────────────────────────────────────

    private fun startTimer() {
        timerJob = serviceScope.launch {
            while (isActive) {
                delay(1000L)
                val elapsed = (System.currentTimeMillis() - startTime) / 1000L
                elapsedSec.postValue(elapsed)
            }
        }
    }

    // ── Kalorien-Schätzung ───────────────────────────────────────────────────

    /**
     * Grobe Schätzung: ~60 kcal pro km (für ~70 kg Person).
     * Für ein Schulprojekt ausreichend genau.
     */
    private fun calculateCalories(distanceMeters: Float, steps: Int): Int {
        val km = distanceMeters / 1000f
        return (km * 60).roundToInt()
    }

    // ── Benachrichtigung ─────────────────────────────────────────────────────

    private fun buildNotification(text: String) = run {
        createNotificationChannel()

        val openAppIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, openAppIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_run)
            .setContentTitle("MyFitnessTracker – Tracking aktiv")
            .setContentText(text)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .setSilent(true)
            .build()
    }

    private fun updateNotification() {
        val dist  = "%.2f km".format(totalDistance / 1000f)
        val speed = "%.1f km/h".format(speedKmh.value ?: 0f)
        val notif = buildNotification("$dist  •  $speed")
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(NOTIFICATION_ID, notif)
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "GPS-Tracking",
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            description = "Zeigt aktive Tracking-Session an"
        }
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }
}
