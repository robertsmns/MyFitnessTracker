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

    suspend fun insertSimulationData(userId: Long) = withContext(Dispatchers.IO) {
        val calendar = Calendar.getInstance()
        
        // Erzeuge 10 Läufe verteilt über die letzten 14 Tage
        for (i in 0..13) {
            // Nicht an jedem Tag ein Lauf (Zufall)
            if (Random.nextInt(10) > 7 && i != 0) continue 

            calendar.time = Date()
            calendar.add(Calendar.DAY_OF_YEAR, -i)
            
            // Zufällige Uhrzeit am Tag (zwischen 07:00 und 19:00)
            calendar.set(Calendar.HOUR_OF_DAY, 7 + Random.nextInt(12))
            calendar.set(Calendar.MINUTE, Random.nextInt(60))
            
            val startTime = calendar.timeInMillis
            
            // Zufällige Werte für Distanz und Dauer
            val distanceM = (2000 + Random.nextInt(6000)).toFloat() // 2 - 8 km
            val durationMin = (distanceM / 1000f * (5 + Random.nextFloat() * 2)).toLong() // 5-7 min/km
            val endTime = startTime + (durationMin * 60 * 1000)
            
            val steps = (distanceM * 1.25f + Random.nextInt(500)).toInt()
            val avgSpeed = (distanceM / 1000f) / (durationMin / 60f)

            val run = Run(
                userId = userId,
                startTime = startTime,
                endTime = endTime,
                distanceMeters = distanceM,
                avgSpeedKmh = avgSpeed,
                steps = steps,
                calories = (distanceM / 1000f * 70 * 0.9f).toInt(),
                elevationGain = Random.nextInt(50).toFloat(),
                isActive = false
            )
            
            // Direkt einfügen mit den korrekten Zeiten!
            val runId = repository.insertFullRun(run)

            // Erzeuge ein paar RoutePoints für die Karte (Zick-Zack-Route)
            // Berlin-Mitte als Basis
            val baseLat = 52.5200 
            val baseLng = 13.4050
            
            val points = mutableListOf<RoutePoint>()
            val numPoints = 15
            for (p in 0 until numPoints) {
                val pointTime = startTime + (p * (durationMin * 60 * 1000 / numPoints))
                points.add(
                    RoutePoint(
                        runId = runId,
                        latitude = baseLat + (i * 0.005) + (p * 0.0008), // Versatz pro Tag und Punkt
                        longitude = baseLng + (p * 0.0008 * (if(p%2==0) 1 else -1)),
                        altitude = 40.0 + Random.nextDouble() * 5.0,
                        speed = 3f,
                        accuracy = 3f,
                        timestamp = pointTime
                    )
                )
            }
            points.forEach { repository.addRoutePoint(it) }
        }
    }
}
