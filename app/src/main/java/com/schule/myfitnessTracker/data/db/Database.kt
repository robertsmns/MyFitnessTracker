package com.schule.myfitnessTracker.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.schule.myfitnessTracker.data.model.RoutePoint
import com.schule.myfitnessTracker.data.model.Run

/**
 * Room-Datenbank – Singleton-Instanz.
 *
 * Speichert alle Trainingseinheiten und GPS-Routen lokal auf dem Gerät.
 */
@Database(
    entities = [Run::class, RoutePoint::class],
    version = 1,
    exportSchema = false
)
abstract class FitnessDatabase : RoomDatabase() {

    abstract fun runDao(): RunDao
    abstract fun routePointDao(): RoutePointDao

    companion object {
        @Volatile
        private var INSTANCE: FitnessDatabase? = null

        fun getInstance(context: Context): FitnessDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FitnessDatabase::class.java,
                    "fitness_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Repository – Einzige Datenquelle für ViewModels
// ─────────────────────────────────────────────────────────────────────────────

class FitnessRepository(private val db: FitnessDatabase) {

    private val runDao = db.runDao()
    private val pointDao = db.routePointDao()

    // ── Runs ──────────────────────────────────────────────────────────────────

    val allRuns = runDao.getAllRuns()
    val todayDistance = runDao.getTodayDistanceLive()
    val todaySteps = runDao.getTodayStepsLive()
    val lastRun = runDao.getLastRun()
    val avgSpeed = runDao.getAvgSpeedLive()

    fun distanceSince(since: Long) = runDao.getDistanceSince(since)

    suspend fun startNewRun(): Long {
        val run = Run(isActive = true)
        return runDao.insertRun(run)
    }

    suspend fun finishRun(
        runId: Long,
        distanceMeters: Float,
        avgSpeedKmh: Float,
        steps: Int,
        calories: Int,
        elevationGain: Float
    ) {
        val run = runDao.getRunById(runId) ?: return
        runDao.updateRun(
            run.copy(
                endTime = System.currentTimeMillis(),
                distanceMeters = distanceMeters,
                avgSpeedKmh = avgSpeedKmh,
                steps = steps,
                calories = calories,
                elevationGain = elevationGain,
                isActive = false
            )
        )
    }

    suspend fun getActiveRun() = runDao.getActiveRun()
    suspend fun getRunById(id: Long) = runDao.getRunById(id)

    suspend fun getWeeklyStats(): List<DailyStats> {
        val sevenDaysAgo = System.currentTimeMillis() - 7L * 24 * 60 * 60 * 1000
        return runDao.getWeeklyStats(sevenDaysAgo)
    }

    // ── Route Points ──────────────────────────────────────────────────────────

    suspend fun addRoutePoint(point: RoutePoint) = pointDao.insertPoint(point)

    suspend fun getRouteForRun(runId: Long) = pointDao.getPointsForRun(runId)

    fun getRouteForRunLive(runId: Long) = pointDao.getPointsForRunLive(runId)

    suspend fun deleteRun(run: com.schule.myfitnessTracker.data.model.Run) {
        runDao.deleteRun(run)
    }
}
