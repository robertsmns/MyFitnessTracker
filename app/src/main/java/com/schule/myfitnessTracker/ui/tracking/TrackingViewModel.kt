package com.schule.myfitnessTracker.ui.tracking

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.schule.myfitnessTracker.data.db.FitnessDatabase
import com.schule.myfitnessTracker.data.db.FitnessRepository
import com.schule.myfitnessTracker.service.TrackingService
import kotlinx.coroutines.launch

/**
 * ViewModel für die Tracking-Session.
 *
 * Verbindet TrackingService-LiveData mit der UI und
 * startet/stoppt den Service über Intents.
 */
class TrackingViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: FitnessRepository by lazy {
        FitnessRepository(FitnessDatabase.getInstance(application))
    }
    
    private val profileManager = com.schule.myfitnessTracker.util.ProfileManager(application)

    // ── Service LiveData (direkte Referenzen) ─────────────────────────────────

    val isTracking   = TrackingService.isTracking
    val currentRunId = TrackingService.currentRunId
    val distanceM    = TrackingService.distanceM
    val speedKmh     = TrackingService.speedKmh
    val stepCount    = TrackingService.stepCount
    val elapsedSec   = TrackingService.elapsedSec
    val lastLocation = TrackingService.lastLocation
    val isPaused     = TrackingService.isPaused

    // ── Formatierte Werte ─────────────────────────────────────────────────────

    val distanceFormatted = MediatorLiveData<String>().apply {
        addSource(distanceM) { dist ->
            value = if (dist >= 1000f) "%.2f km".format(dist / 1000f)
                    else "%.0f m".format(dist)
        }
    }

    val timerFormatted = MediatorLiveData<String>().apply {
        addSource(elapsedSec) { sec ->
            val h = sec / 3600
            val m = (sec % 3600) / 60
            val s = sec % 60
            value = if (h > 0) "%d:%02d:%02d".format(h, m, s)
                    else "%02d:%02d".format(m, s)
        }
    }

    // ── Route Points für Karte ────────────────────────────────────────────────

    fun getRoutePoints(runId: Long) = repository.getRouteForRunLive(runId)

    // ── Service Control ───────────────────────────────────────────────────────

    fun toggleTracking() {
        val context = getApplication<Application>()
        val intent = Intent(context, TrackingService::class.java)

        if (isTracking.value == true) {
            // Sport beenden
            intent.action = TrackingService.ACTION_STOP
        } else {
            // Sport neu starten (auch wenn passiv im Hintergrund läuft)
            intent.action = TrackingService.ACTION_START
            intent.putExtra("SIMULATE", profileManager.isSimulationMode)
            context.startForegroundService(intent)
            return
        }
        context.startService(intent)
    }

    fun pauseTracking() {
        val context = getApplication<Application>()
        val intent = Intent(context, TrackingService::class.java).apply {
            action = TrackingService.ACTION_PAUSE
        }
        context.startService(intent)
    }
}
