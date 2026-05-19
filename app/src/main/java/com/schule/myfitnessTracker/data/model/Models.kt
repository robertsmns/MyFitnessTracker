package com.schule.myfitnessTracker.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Repräsentiert einen Benutzer der App.
 */
@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val username: String,
    val email: String,
    val passwordHash: String,
    val role: String = "USER", // "ADMIN" oder "USER"
    val profilePicturePath: String? = null,
    val weight: Float = 75f,
    val targetDistanceKm: Float = 5f
)

/**
 * Repräsentiert eine einzelne Trainingseinheit (Lauf/Spaziergang).
 *
 * Wird in der Room-Datenbank in der Tabelle "runs" gespeichert.
 */
@Entity(
    tableName = "runs",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("userId")]
)
data class Run(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    /** Zu welchem User gehört dieser Run */
    val userId: Long,

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
    val isActive: Boolean = true,

    /** Ist dies ein simulierter Lauf (Mock-Daten)? */
    val isMock: Boolean = false,

    /** Tracking Modus: "ACTIVE" (Training) oder "PASSIVE" (Hintergrund/Alltag) */
    val trackingMode: String = "ACTIVE",

    /** Typ der Aktivität (WALKING, RUNNING, BICYCLE, etc.) */
    val activityType: String = "RUNNING"
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

    /** Distanz formatiert (m oder km) */
    val distanceFormatted: String
        get() = if (distanceMeters < 1000f) "%.0f m".format(distanceMeters)
                else "%.2f km".format(distanceMeters / 1000f)
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
