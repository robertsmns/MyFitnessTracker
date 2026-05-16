package com.schule.myfitnessTracker.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.schule.myfitnessTracker.data.model.RoutePoint
import com.schule.myfitnessTracker.data.model.Run

// ─────────────────────────────────────────────────────────────────────────────
// DAO für Trainings-Einheiten
// ─────────────────────────────────────────────────────────────────────────────

@Dao
interface RunDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRun(run: Run): Long

    @Update
    suspend fun updateRun(run: Run)

    @Delete
    suspend fun deleteRun(run: Run)

    /** Alle Runs neueste zuerst – als LiveData für automatische UI-Updates */
    @Query("SELECT * FROM runs ORDER BY startTime DESC")
    fun getAllRuns(): LiveData<List<Run>>

    /** Heute gelaufene Distanz in Metern */
    @Query("""
        SELECT COALESCE(SUM(distanceMeters), 0) 
        FROM runs 
        WHERE DATE(startTime / 1000, 'unixepoch', 'localtime') = DATE('now', 'localtime')
          AND isActive = 0
    """)
    fun getTodayDistanceLive(): LiveData<Float>

    /** Aktiven Run laden (isActive = true) */
    @Query("SELECT * FROM runs WHERE isActive = 1 LIMIT 1")
    suspend fun getActiveRun(): Run?

    /** Letzten Run holen */
    @Query("SELECT * FROM runs ORDER BY startTime DESC LIMIT 1")
    fun getLastRun(): LiveData<Run?>

    /** Wöchentliche Distanz (letzte 7 Tage) */
    @Query("""
        SELECT COALESCE(SUM(distanceMeters), 0) 
        FROM runs 
        WHERE startTime >= :since AND isActive = 0
    """)
    fun getDistanceSince(since: Long): LiveData<Float>

    /** Schritte heute */
    @Query("""
        SELECT COALESCE(SUM(steps), 0) 
        FROM runs 
        WHERE DATE(startTime / 1000, 'unixepoch', 'localtime') = DATE('now', 'localtime')
    """)
    fun getTodayStepsLive(): LiveData<Int>

    /** Durchschnittliche Geschwindigkeit aller beendeten Runs */
    @Query("SELECT COALESCE(AVG(avgSpeedKmh), 0) FROM runs WHERE isActive = 0")
    fun getAvgSpeedLive(): LiveData<Float>

    /** Run nach ID */
    @Query("SELECT * FROM runs WHERE id = :runId")
    suspend fun getRunById(runId: Long): Run?

    /** Distanz pro Tag der letzten 7 Tage (für Diagramm) */
    @Query("""
        SELECT DATE(startTime / 1000, 'unixepoch', 'localtime') AS day,
               SUM(distanceMeters) / 1000.0 AS distanceKm
        FROM runs
        WHERE startTime >= :since AND isActive = 0
        GROUP BY day
        ORDER BY day ASC
    """)
    suspend fun getWeeklyStats(since: Long): List<DailyStats>
}

/** Hilfsdatenklasse für Tages-Statistiken */
data class DailyStats(
    val day: String,
    val distanceKm: Float
)

// ─────────────────────────────────────────────────────────────────────────────
// DAO für GPS-Punkte
// ─────────────────────────────────────────────────────────────────────────────

@Dao
interface RoutePointDao {

    @Insert
    suspend fun insertPoint(point: RoutePoint)

    @Insert
    suspend fun insertPoints(points: List<RoutePoint>)

    /** Alle GPS-Punkte eines Runs */
    @Query("SELECT * FROM route_points WHERE runId = :runId ORDER BY timestamp ASC")
    suspend fun getPointsForRun(runId: Long): List<RoutePoint>

    /** GPS-Punkte als LiveData (für Live-Karte während Tracking) */
    @Query("SELECT * FROM route_points WHERE runId = :runId ORDER BY timestamp ASC")
    fun getPointsForRunLive(runId: Long): LiveData<List<RoutePoint>>

    @Query("DELETE FROM route_points WHERE runId = :runId")
    suspend fun deletePointsForRun(runId: Long)
}
