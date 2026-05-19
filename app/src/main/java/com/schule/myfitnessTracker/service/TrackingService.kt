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
import com.schule.myfitnessTracker.util.ProfileManager
import kotlinx.coroutines.*
import java.util.Random
import kotlin.math.roundToInt
import kotlin.random.Random as KotlinRandom

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
        const val ACTION_ACTIVITY_UPDATE = "ACTION_ACTIVITY_UPDATE"

        private const val CHANNEL_ID = "fitness_tracking_channel"
        private const val NOTIFICATION_ID = 1

        // GPS Intervalle
        private const val INTERVAL_ACTIVE_MS = 1000L   // 1 Sekunde für Sport

        // Statische LiveData – bleibt über Fragment-Wechsel hinweg erhalten
        val isTracking   = MutableLiveData(false)      // Läuft IRGENDWAS (Service an)?
        val currentRunId = MutableLiveData<Long?>(null)
        val distanceM    = MutableLiveData(0f)        // Meter
        val speedKmh     = MutableLiveData(0f)
        val stepCount    = MutableLiveData(0)
        val elapsedSec   = MutableLiveData(0L)
        val lastLocation = MutableLiveData<Location?>(null)
        val isPaused     = MutableLiveData(false)
        val isSimulationActive = MutableLiveData(false)
        val heartRate    = MutableLiveData(0) // Platzhalter für Bluetooth-Brustgurt
        val activityType = MutableLiveData("RUNNING")
        val currentStartTime = MutableLiveData(0L)
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var sensorManager: SensorManager
    private lateinit var repository: FitnessRepository
    private lateinit var profileManager: ProfileManager

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private var prevLocation: Location? = null
    private var simulationJob: Job? = null
    private var totalDistance = 0f
    private var totalElevationGain = 0f
    private var startTime = 0L
    private var timerJob: Job? = null
    private var stepsAtStart = 0
    private var totalStepsRaw = 0

    // ── Lifecycle ────────────────────────────────────────────────────────────

    override fun onCreate() {
        super.onCreate()
        val db = FitnessDatabase.getInstance(applicationContext)
        repository = FitnessRepository(db)
        profileManager = ProfileManager(applicationContext)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        setupLocationClient()
        setupSensors()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        when (intent?.action) {
            ACTION_START -> {
                val simulate = intent.getBooleanExtra("SIMULATE", false)
                val type = intent.getStringExtra("ACTIVITY_TYPE") ?: "RUNNING"
                val mode = intent.getStringExtra("TRACKING_MODE") ?: "ACTIVE"
                startTracking(simulate, type, mode)
            }
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
                if (isPaused.value != true) {
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

    private fun startTracking(simulate: Boolean = false, type: String = "RUNNING", mode: String = "ACTIVE") {
        startForeground(NOTIFICATION_ID, buildNotification("GPS wird verbunden…", "Training aktiv"))

        totalDistance = 0f
        totalElevationGain = 0f
        prevLocation  = null
        startTime     = System.currentTimeMillis()
        currentStartTime.postValue(startTime)
        activityType.postValue(type)
        stepsAtStart  = totalStepsRaw
        isPaused.postValue(false)
        isSimulationActive.postValue(simulate)

        isTracking.postValue(true)
        distanceM.postValue(0f)
        speedKmh.postValue(0f)
        stepCount.postValue(0)

        // Neuen Run in DB anlegen
        serviceScope.launch {
            try {
                val runId = repository.startNewRun(
                    userId = profileManager.currentUserId,
                    isMock = simulate,
                    trackingMode = mode,
                    activityType = type
                )
                currentRunId.postValue(runId)
            } catch (e: Exception) {
                e.printStackTrace()
                // Falls der User nicht existiert (z.B. nach Wipe), stoppen wir den Service
                withContext(Dispatchers.Main) {
                    stopTracking()
                }
            }
        }

        if (simulate) {
            startGpsSimulation()
        } else {
            requestLocationUpdates(INTERVAL_ACTIVE_MS)
        }
        startTimer()
    }

    private fun startGpsSimulation() {
        simulationJob?.cancel()
        simulationJob = serviceScope.launch {
            // Startpunkt: Berlin-Mitte
            var curLat = 52.5200
            var curLng = 13.4050
            
            while (isActive) {
                if (isPaused.value != true) {
                    val loc = Location("simulation").apply {
                        latitude = curLat
                        longitude = curLng
                        speed = 3.5f + KotlinRandom.nextFloat() // ca. 12 km/h
                        accuracy = 2f
                        time = System.currentTimeMillis()
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            verticalAccuracyMeters = 1f
                        }
                    }
                    
                    withContext(Dispatchers.Main) {
                        handleNewLocation(loc)
                    }

                    // Bewege den Punkt ein kleines Stück
                    curLat += 0.0001
                    curLng += (KotlinRandom.nextDouble() - 0.5) * 0.0001
                    
                    // Simuliere Schritte (ca. 2 Schritte pro Sekunde)
                    val currentSteps = stepCount.value ?: 0
                    stepCount.postValue(currentSteps + 2)
                }
                delay(1000L)
            }
        }
    }

    private fun stopTracking() {
        // 2. Daten SOFORT einfrieren (Snapshot erstellen)
        val finalRunId = currentRunId.value
        val finalDistance = totalDistance
        val finalSteps = stepCount.value ?: 0
        val finalElapsed = elapsedSec.value ?: 0L
        val finalElevation = totalElevationGain
        
        // 2. Sofortiger UI-Reset (damit die App sauber aussieht)
        isTracking.postValue(false)
        isPaused.postValue(false)
        isSimulationActive.postValue(false)
        timerJob?.cancel()
        simulationJob?.cancel()
        fusedLocationClient.removeLocationUpdates(locationCallback)

        // 3. Speichern und Beenden im Hintergrund-Scope
        serviceScope.launch {
            try {
                if (finalRunId != null) {
                    val calories = profileManager.calculateCalories(finalDistance)
                    val avgSpeed = if (finalElapsed > 0)
                        (finalDistance / 1000f) / (finalElapsed / 3600f)
                    else 0f

                    // Datenbank-Operation abwarten
                    repository.finishRun(
                        runId          = finalRunId,
                        distanceMeters = finalDistance,
                        avgSpeedKmh    = avgSpeed,
                        steps          = finalSteps,
                        calories       = calories,
                        elevationGain  = finalElevation
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                // Erst wenn alles fertig ist, Service stoppen und Werte nullen
                withContext(Dispatchers.Main) {
                    showFinishedNotification(finalDistance)
                    resetTrackingValues()
                    stopForeground(STOP_FOREGROUND_REMOVE)
                    stopSelf()
                }
            }
        }
    }

    private fun resetTrackingValues() {
        currentRunId.postValue(null)
        distanceM.postValue(0f)
        speedKmh.postValue(0f)
        stepCount.postValue(0)
        elapsedSec.postValue(0L)
        lastLocation.postValue(null)
        totalDistance = 0f
        totalStepsRaw = 0
        stepsAtStart = 0
        totalElevationGain = 0f
        startTime = 0L
        currentStartTime.postValue(0L)
        prevLocation = null
    }

    private fun pauseTracking() {
        val currentlyPaused = isPaused.value ?: false
        isPaused.postValue(!currentlyPaused)
        
        if (!currentlyPaused) {
            // Pausieren
            timerJob?.cancel()
        } else {
            // Fortsetzen - Startzeit anpassen, um Pause zu "überspringen"
            // (Einfachheitshalber starten wir den Timer-Job neu)
            startTimer()
        }
    }

    // ── GPS ──────────────────────────────────────────────────────────────────

    private fun requestLocationUpdates(intervalMs: Long) {
        val request = LocationRequest.Builder(intervalMs)
            .setMinUpdateIntervalMillis(intervalMs / 2)
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .build()

        try {
            // Erstmal alte Updates entfernen, um Intervall sicher zu ändern
            fusedLocationClient.removeLocationUpdates(locationCallback)
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
        if (isPaused.value == true) return
        
        lastLocation.postValue(location)

        // Distanz & Höhenmeter berechnen
        prevLocation?.let { prev ->
            val delta = prev.distanceTo(location)
            if (delta > 1f) {   // < 1m ignorieren (GPS-Rauschen)
                totalDistance += delta
                distanceM.postValue(totalDistance)

                // Höhenmeter-Zuwachs (nur wenn gestiegen)
                if (location.hasAltitude() && prev.hasAltitude()) {
                    val altDelta = location.altitude - prev.altitude
                    if (altDelta > 0.5) { // Kleiner Filter gegen Rauschen
                        totalElevationGain += altDelta.toFloat()
                    }
                }
            }
        }
        prevLocation = location

        // Geschwindigkeit (m/s → km/h)
        val speed = if (location.hasSpeed()) location.speed * 3.6f else 0f
        speedKmh.postValue(speed)

        // GPS-Punkt in DB speichern
        serviceScope.launch {
            val runId = currentRunId.value ?: return@launch
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
        timerJob?.cancel() // Sicherstellen, dass kein alter Job läuft
        timerJob = serviceScope.launch {
            while (isActive) {
                delay(1000L)
                if (isPaused.value != true) {
                    val elapsed = elapsedSec.value ?: 0L
                    elapsedSec.postValue(elapsed + 1)
                    // Benachrichtigung jede Sekunde aktualisieren
                    updateNotification()
                }
            }
        }
    }

    // ── Benachrichtigung ─────────────────────────────────────────────────────

    private fun buildNotification(text: String, title: String? = null) = run {
        createNotificationChannel()

        val openAppIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, openAppIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Pause/Fortsetzen Action
        val isCurrentlyPaused = isPaused.value ?: false
        val pauseActionIntent = Intent(this, TrackingService::class.java).apply { action = ACTION_PAUSE }
        val pausePendingIntent = PendingIntent.getService(this, 1, pauseActionIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        val pauseAction = NotificationCompat.Action(
            if (isCurrentlyPaused) R.drawable.ic_play else R.drawable.ic_pause,
            if (isCurrentlyPaused) "Fortsetzen" else "Pause",
            pausePendingIntent
        )

        // Stopp Action
        val stopActionIntent = Intent(this, TrackingService::class.java).apply { action = ACTION_STOP }
        val stopPendingIntent = PendingIntent.getService(this, 2, stopActionIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        val stopAction = NotificationCompat.Action(R.drawable.ic_stop, "Stoppen", stopPendingIntent)

        NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_run)
            .setContentTitle(title ?: "Training aktiv")
            .setContentText(text)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .setSilent(true)
            .addAction(pauseAction)
            .addAction(stopAction)
            .setStyle(androidx.media.app.NotificationCompat.MediaStyle()
                .setShowActionsInCompactView(0, 1))
            .build()
    }

    private fun showFinishedNotification(distance: Float) {
        val distText = if (distance < 1000f) "%.0f m".format(distance) else "%.2f km".format(distance / 1000f)
        val notif = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_run)
            .setContentTitle("Training abgeschlossen!")
            .setContentText("Super! Du bist insgesamt $distText gelaufen.")
            .setAutoCancel(true)
            .build()
        
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(NOTIFICATION_ID + 100, notif)
    }

    private fun updateNotification() {
        val dist  = if (totalDistance < 1000f) "%.0f m".format(totalDistance) else "%.2f km".format(totalDistance / 1000f)
        val time  = formatTime(elapsedSec.value ?: 0L)
        val status = if (isPaused.value == true) "(Pausiert)" else ""
        
        val notif = buildNotification("$dist  •  $time  $status")
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(NOTIFICATION_ID, notif)
    }

    private fun formatTime(sec: Long): String {
        val m = sec / 60
        val s = sec % 60
        return "%02d:%02d".format(m, s)
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
