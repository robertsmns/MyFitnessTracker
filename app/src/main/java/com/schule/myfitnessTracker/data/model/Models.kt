package com.schule.myfitnessTracker.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Repräsentiert eine einzelne Trainingseinheit (Lauf/Spaziergang).
 *
 * Wird in der Room-Datenbank in der Tabelle "runs" gespeichert.
 */
@Entity(tableName = "runs")
data class Run(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    /** Startzeit in Millisekunden (Unix-Timestamp) */
    val startTime: Long = System.currentTimeMillis(),

    /** Endzeit in Millisekunden */
    val endTime: Long = 0L,

    /** Zurückgelegte Distanz in Metern */
    val distanceMeters: Float = 0f,

    /** Durchschnittsgeschwindigkeit in km/h */
    val avgSpeedKmh: Float = 0f,

    /** Schritte (Step Counter Sensor) */
    val steps: Int = 0,

    /** Verbrannte Kalorien (Schätzung) */
    val calories: Int = 0,

    /** Höhenmeter gesamt (kumuliert) */
    val elevationGain: Float = 0f,

    /** Ist die Session noch aktiv? */
    val isActive: Boolean = true
) {
    /** Dauer in Sekunden */
    val durationSeconds: Long
        get() = if (endTime > 0) (endTime - startTime) / 1000 else 0

    /** Formatierte Dauer "mm:ss" */
    val durationFormatted: String
        get() {
            val minutes = durationSeconds / 60
            val seconds = durationSeconds % 60
            return "%02d:%02d".format(minutes, seconds)
        }

    /** Distanz in km (2 Nachkommastellen) */
    val distanceKm: String
        get() = "%.2f km".format(distanceMeters / 1000f)
}

/**
 * Einzelner GPS-Punkt einer Route.
 *
 * Gehört immer zu einem Run (Fremdschlüssel).
 */
@Entity(
    tableName = "route_points",
    foreignKeys = [
        ForeignKey(
            entity = Run::class,
            parentColumns = ["id"],
            childColumns = ["runId"],
            onDelete = ForeignKey.CASCADE   // Punkte löschen wenn Run gelöscht
        )
    ],
    indices = [Index("runId")]
)
data class RoutePoint(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    /** Zu welchem Run gehört dieser Punkt */
    val runId: Long,

    val latitude: Double,
    val longitude: Double,

    /** Höhe über Meeresspiegel in Metern */
    val altitude: Double,

    /** Geschwindigkeit an diesem Punkt in m/s */
    val speed: Float,

    /** Genauigkeit des GPS-Signals in Metern */
    val accuracy: Float,

    val timestamp: Long = System.currentTimeMillis()
)
