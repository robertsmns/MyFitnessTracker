package com.schule.myfitnessTracker.util

import com.schule.myfitnessTracker.data.db.FitnessRepository
import com.schule.myfitnessTracker.data.model.RoutePoint
import com.schule.myfitnessTracker.data.model.Run
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.random.Random

/**
 * Hilfsklasse zum Erzeugen von Simulationsdaten (Mock-Daten).
 * Erlaubt es, die App zu testen, ohne echten Sport zu treiben.
 */
class MockDataManager(private val repository: FitnessRepository) {

    suspend fun insertSimulationData() = withContext(Dispatchers.IO) {
        val calendar = Calendar.getInstance()
        
        // Erzeuge 7 Runs für die letzte Woche
        for (i in 0..6) {
            calendar.time = Date()
            calendar.add(Calendar.DAY_OF_YEAR, -i)
            val startTime = calendar.timeInMillis
            
            // Zufällige Werte
            val distanceM = (1000 + Random.nextInt(5000)).toFloat() // 1 - 6 km
            val steps = (distanceM * 1.2f).toInt()
            val durationMin = (distanceM / 1000f * 6f).toLong() // ca 6 min/km
            val endTime = startTime + (durationMin * 60 * 1000)
            
            val run = Run(
                startTime = startTime,
                endTime = endTime,
                distanceMeters = distanceM,
                avgSpeedKmh = 10f + Random.nextFloat() * 2,
                steps = steps,
                calories = (distanceM / 1000f * 75 * 0.9f).toInt(),
                isActive = false
            )
            
            val runId = repository.startNewRun().let { id ->
                // Wir müssen den gerade erstellten Run updaten, da startNewRun nur ein leeres Objekt erstellt
                repository.finishRun(id, run.distanceMeters, run.avgSpeedKmh, run.steps, run.calories, 0f)
                id
            }

            // Erzeuge ein paar RoutePoints für die Karte (Zick-Zack-Route)
            val baseLat = 52.5200 // Berlin Koordinaten als Beispiel
            val baseLng = 13.4050
            
            val points = mutableListOf<RoutePoint>()
            for (p in 0..10) {
                points.add(
                    RoutePoint(
                        runId = runId,
                        latitude = baseLat + (p * 0.001),
                        longitude = baseLng + (Random.nextDouble() * 0.002),
                        altitude = 40.0 + Random.nextDouble() * 5.0,
                        speed = 3f,
                        accuracy = 5f,
                        timestamp = startTime + (p * 60000)
                    )
                )
            }
            points.forEach { repository.addRoutePoint(it) }
        }
    }
}
