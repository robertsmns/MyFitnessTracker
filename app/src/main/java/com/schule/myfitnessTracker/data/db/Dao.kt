package com.schule.myfitnessTracker.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.schule.myfitnessTracker.data.model.RoutePoint
import com.schule.myfitnessTracker.data.model.Run
import com.schule.myfitnessTracker.data.model.User

// ─────────────────────────────────────────────────────────────────────────────
// DAO für Benutzer
// ─────────────────────────────────────────────────────────────────────────────

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: User): Long

    @Update
    suspend fun updateUser(user: User)

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    suspend fun getUserByUsername(username: String): User?

    @Query("SELECT * FROM users WHERE email = :identifier OR username = :identifier LIMIT 1")
    suspend fun getUserByIdentifier(identifier: String): User?

    @Query("SELECT * FROM users WHERE id = :userId LIMIT 1")
    suspend fun getUserById(userId: Long): User?

    @Query("SELECT * FROM users WHERE id = :userId LIMIT 1")
    fun getUserByIdLive(userId: Long): LiveData<User?>

    @Query("SELECT COUNT(*) FROM users")
    suspend fun getUserCount(): Int
}

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
    @Query("SELECT * FROM runs WHERE userId = :userId AND isMock = :isMock ORDER BY startTime DESC")
    fun getAllRuns(userId: Long, isMock: Boolean): LiveData<List<Run>>

    /** Heute gelaufene Distanz in Metern */
    @Query("""
        SELECT COALESCE(SUM(distanceMeters), 0) 
        FROM runs 
        WHERE userId = :userId AND DATE(startTime / 1000, 'unixepoch', 'localtime') = DATE('now', 'localtime')
          AND isActive = 0 AND isMock = :isMock
    """)
    fun getTodayDistanceLive(userId: Long, isMock: Boolean): LiveData<Float>

    /** Heute verbrannte Kalorien */
    @Query("""
        SELECT COALESCE(SUM(calories), 0) 
        FROM runs 
        WHERE userId = :userId AND DATE(startTime / 1000, 'unixepoch', 'localtime') = DATE('now', 'localtime')
          AND isMock = :isMock
    """)
    fun getTodayCaloriesLive(userId: Long, isMock: Boolean): LiveData<Int>

    /** Aktiven Run laden (isActive = true) */
    @Query("SELECT * FROM runs WHERE userId = :userId AND isActive = 1 AND isMock = :isMock LIMIT 1")
    suspend fun getActiveRun(userId: Long, isMock: Boolean): Run?

    /** Letzten Run holen */
    @Query("SELECT * FROM runs WHERE userId = :userId AND isMock = :isMock ORDER BY startTime DESC LIMIT 1")
    fun getLastRun(userId: Long, isMock: Boolean): LiveData<Run?>

    /** Wöchentliche Distanz (letzte 7 Tage) */
    @Query("""
        SELECT COALESCE(SUM(distanceMeters), 0) 
        FROM runs 
        WHERE userId = :userId AND startTime >= :since AND isActive = 0 AND isMock = :isMock
    """)
    fun getDistanceSince(userId: Long, since: Long, isMock: Boolean): LiveData<Float>

    /** Schritte heute */
    @Query("""
        SELECT COALESCE(SUM(steps), 0) 
        FROM runs 
        WHERE userId = :userId AND DATE(startTime / 1000, 'unixepoch', 'localtime') = DATE('now', 'localtime')
          AND isMock = :isMock
    """)
    fun getTodayStepsLive(userId: Long, isMock: Boolean): LiveData<Int>

    /** Durchschnittliche Geschwindigkeit aller beendeten Runs */
    @Query("SELECT COALESCE(AVG(avgSpeedKmh), 0) FROM runs WHERE userId = :userId AND isActive = 0 AND isMock = :isMock")
    fun getAvgSpeedLive(userId: Long, isMock: Boolean): LiveData<Float>

    /** Run nach ID */
    @Query("SELECT * FROM runs WHERE id = :runId")
    suspend fun getRunById(runId: Long): Run?

    /** Distanz pro Tag der letzten 7 Tage (für Diagramm) */
    @Query("""
        SELECT DATE(startTime / 1000, 'unixepoch', 'localtime') AS day,
               SUM(distanceMeters) / 1000.0 AS distanceKm
        FROM runs
        WHERE userId = :userId AND startTime >= :since AND isActive = 0 AND isMock = :isMock
        GROUP BY day
        ORDER BY day ASC
    """)
    suspend fun getWeeklyStats(userId: Long, since: Long, isMock: Boolean): List<DailyStats>
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
