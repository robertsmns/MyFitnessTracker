package com.schule.myfitnessTracker.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.schule.myfitnessTracker.data.model.RoutePoint
import com.schule.myfitnessTracker.data.model.Run
import com.schule.myfitnessTracker.data.model.User

/**
 * Room-Datenbank – Singleton-Instanz.
 */
@Database(
    entities = [User::class, Run::class, RoutePoint::class],
    version = 4,
    exportSchema = false
)
abstract class FitnessDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
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

    private val userDao = db.userDao()
    private val runDao = db.runDao()
    private val pointDao = db.routePointDao()

    // ── User ──────────────────────────────────────────────────────────────────

    suspend fun insertUser(user: User) = userDao.insertUser(user)
    suspend fun updateUser(user: User) = userDao.updateUser(user)
    suspend fun getUserByEmail(email: String) = userDao.getUserByEmail(email)
    suspend fun getUserByUsername(username: String) = userDao.getUserByUsername(username)
    suspend fun getUserByIdentifier(identifier: String) = userDao.getUserByIdentifier(identifier)
    suspend fun getUserById(userId: Long) = userDao.getUserById(userId)
    fun getUserByIdLive(userId: Long) = userDao.getUserByIdLive(userId)
    suspend fun getUserCount() = userDao.getUserCount()

    // ── Runs (Gefiltert nach User) ────────────────────────────────────────────

    fun getAllRuns(userId: Long, isMock: Boolean) = runDao.getAllRuns(userId, isMock)
    fun getTodayDistance(userId: Long, isMock: Boolean) = runDao.getTodayDistanceLive(userId, isMock)
    fun getTodaySteps(userId: Long, isMock: Boolean) = runDao.getTodayStepsLive(userId, isMock)
    fun getTodayCalories(userId: Long, isMock: Boolean) = runDao.getTodayCaloriesLive(userId, isMock)
    fun getLastRun(userId: Long, isMock: Boolean) = runDao.getLastRun(userId, isMock)
    fun getAvgSpeed(userId: Long, isMock: Boolean) = runDao.getAvgSpeedLive(userId, isMock)

    suspend fun startNewRun(
        userId: Long,
        isMock: Boolean = false,
        trackingMode: String = "ACTIVE",
        activityType: String = "RUNNING"
    ): Long {
        val run = Run(
            userId = userId,
            isActive = true,
            isMock = isMock,
            trackingMode = trackingMode,
            activityType = activityType
        )
        return runDao.insertRun(run)
    }

    suspend fun insertFullRun(run: Run): Long {
        return runDao.insertRun(run.copy(isMock = true))
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

    suspend fun getActiveRun(userId: Long, isMock: Boolean) = runDao.getActiveRun(userId, isMock)
    suspend fun getRunById(id: Long) = runDao.getRunById(id)

    suspend fun getWeeklyStats(userId: Long, isMock: Boolean): List<DailyStats> {
        val sevenDaysAgo = System.currentTimeMillis() - 7L * 24 * 60 * 60 * 1000
        return runDao.getWeeklyStats(userId, sevenDaysAgo, isMock)
    }

    // ── Route Points ──────────────────────────────────────────────────────────

    suspend fun addRoutePoint(point: RoutePoint) = pointDao.insertPoint(point)
    suspend fun getRouteForRun(runId: Long) = pointDao.getPointsForRun(runId)
    fun getRouteForRunLive(runId: Long) = pointDao.getPointsForRunLive(runId)

    suspend fun deleteRun(run: Run) {
        runDao.deleteRun(run)
    }
}
