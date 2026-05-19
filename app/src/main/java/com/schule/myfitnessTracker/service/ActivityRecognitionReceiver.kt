package com.schule.myfitnessTracker.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.android.gms.location.ActivityRecognitionResult
import com.google.android.gms.location.DetectedActivity

class ActivityRecognitionReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (ActivityRecognitionResult.hasResult(intent)) {
            val result = ActivityRecognitionResult.extractResult(intent)
            result?.let {
                val mostProbableActivity = it.mostProbableActivity
                val activityTypeStr = when (mostProbableActivity.type) {
                    DetectedActivity.STILL -> "STILL"
                    DetectedActivity.WALKING -> "WALKING"
                    DetectedActivity.RUNNING -> "RUNNING"
                    DetectedActivity.ON_BICYCLE -> "BICYCLE"
                    DetectedActivity.IN_VEHICLE -> "VEHICLE"
                    else -> "UNKNOWN"
                }
                
                // Sende Update an den Service
                val serviceIntent = Intent(context, TrackingService::class.java).apply {
                    action = TrackingService.ACTION_ACTIVITY_UPDATE
                    putExtra("ACTIVITY_TYPE", activityTypeStr)
                }
                context.startService(serviceIntent)
            }
        }
    }
}
