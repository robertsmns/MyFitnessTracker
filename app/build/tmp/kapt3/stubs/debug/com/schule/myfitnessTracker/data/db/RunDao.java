package com.schule.myfitnessTracker.data.db;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J \u0010\u0007\u001a\u0004\u0018\u00010\u00052\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\fJ$\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u000f0\u000e2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\'J\u001e\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u000e2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\'J&\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00110\u000e2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\'J \u0010\u0014\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u000e2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\'J\u0018\u0010\u0015\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0016\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\u0017J\u001e\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\u000e2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\'J\u001e\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00110\u000e2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\'J\u001e\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00190\u000e2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\'J,\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\u000f2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\u001eJ\u0016\u0010\u001f\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010 \u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006\u00a8\u0006!"}, d2 = {"Lcom/schule/myfitnessTracker/data/db/RunDao;", "", "deleteRun", "", "run", "Lcom/schule/myfitnessTracker/data/model/Run;", "(Lcom/schule/myfitnessTracker/data/model/Run;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getActiveRun", "userId", "", "isMock", "", "(JZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllRuns", "Landroidx/lifecycle/LiveData;", "", "getAvgSpeedLive", "", "getDistanceSince", "since", "getLastRun", "getRunById", "runId", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getTodayCaloriesLive", "", "getTodayDistanceLive", "getTodayStepsLive", "getWeeklyStats", "Lcom/schule/myfitnessTracker/data/db/DailyStats;", "(JJZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertRun", "updateRun", "app_debug"})
@androidx.room.Dao()
public abstract interface RunDao {
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertRun(@org.jetbrains.annotations.NotNull()
    com.schule.myfitnessTracker.data.model.Run run, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateRun(@org.jetbrains.annotations.NotNull()
    com.schule.myfitnessTracker.data.model.Run run, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteRun(@org.jetbrains.annotations.NotNull()
    com.schule.myfitnessTracker.data.model.Run run, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Alle Runs neueste zuerst – als LiveData für automatische UI-Updates
     */
    @androidx.room.Query(value = "SELECT * FROM runs WHERE userId = :userId AND isMock = :isMock ORDER BY startTime DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract androidx.lifecycle.LiveData<java.util.List<com.schule.myfitnessTracker.data.model.Run>> getAllRuns(long userId, boolean isMock);
    
    /**
     * Heute gelaufene Distanz in Metern
     */
    @androidx.room.Query(value = "\n        SELECT COALESCE(SUM(distanceMeters), 0) \n        FROM runs \n        WHERE userId = :userId AND DATE(startTime / 1000, \'unixepoch\', \'localtime\') = DATE(\'now\', \'localtime\')\n          AND isActive = 0 AND isMock = :isMock\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract androidx.lifecycle.LiveData<java.lang.Float> getTodayDistanceLive(long userId, boolean isMock);
    
    /**
     * Heute verbrannte Kalorien
     */
    @androidx.room.Query(value = "\n        SELECT COALESCE(SUM(calories), 0) \n        FROM runs \n        WHERE userId = :userId AND DATE(startTime / 1000, \'unixepoch\', \'localtime\') = DATE(\'now\', \'localtime\')\n          AND isMock = :isMock\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract androidx.lifecycle.LiveData<java.lang.Integer> getTodayCaloriesLive(long userId, boolean isMock);
    
    /**
     * Aktiven Run laden (isActive = true)
     */
    @androidx.room.Query(value = "SELECT * FROM runs WHERE userId = :userId AND isActive = 1 AND isMock = :isMock LIMIT 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getActiveRun(long userId, boolean isMock, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.schule.myfitnessTracker.data.model.Run> $completion);
    
    /**
     * Letzten Run holen
     */
    @androidx.room.Query(value = "SELECT * FROM runs WHERE userId = :userId AND isMock = :isMock ORDER BY startTime DESC LIMIT 1")
    @org.jetbrains.annotations.NotNull()
    public abstract androidx.lifecycle.LiveData<com.schule.myfitnessTracker.data.model.Run> getLastRun(long userId, boolean isMock);
    
    /**
     * Wöchentliche Distanz (letzte 7 Tage)
     */
    @androidx.room.Query(value = "\n        SELECT COALESCE(SUM(distanceMeters), 0) \n        FROM runs \n        WHERE userId = :userId AND startTime >= :since AND isActive = 0 AND isMock = :isMock\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract androidx.lifecycle.LiveData<java.lang.Float> getDistanceSince(long userId, long since, boolean isMock);
    
    /**
     * Schritte heute
     */
    @androidx.room.Query(value = "\n        SELECT COALESCE(SUM(steps), 0) \n        FROM runs \n        WHERE userId = :userId AND DATE(startTime / 1000, \'unixepoch\', \'localtime\') = DATE(\'now\', \'localtime\')\n          AND isMock = :isMock\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract androidx.lifecycle.LiveData<java.lang.Integer> getTodayStepsLive(long userId, boolean isMock);
    
    /**
     * Durchschnittliche Geschwindigkeit aller beendeten Runs
     */
    @androidx.room.Query(value = "SELECT COALESCE(AVG(avgSpeedKmh), 0) FROM runs WHERE userId = :userId AND isActive = 0 AND isMock = :isMock")
    @org.jetbrains.annotations.NotNull()
    public abstract androidx.lifecycle.LiveData<java.lang.Float> getAvgSpeedLive(long userId, boolean isMock);
    
    /**
     * Run nach ID
     */
    @androidx.room.Query(value = "SELECT * FROM runs WHERE id = :runId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getRunById(long runId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.schule.myfitnessTracker.data.model.Run> $completion);
    
    /**
     * Distanz pro Tag der letzten 7 Tage (für Diagramm)
     */
    @androidx.room.Query(value = "\n        SELECT DATE(startTime / 1000, \'unixepoch\', \'localtime\') AS day,\n               SUM(distanceMeters) / 1000.0 AS distanceKm\n        FROM runs\n        WHERE userId = :userId AND startTime >= :since AND isActive = 0 AND isMock = :isMock\n        GROUP BY day\n        ORDER BY day ASC\n    ")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getWeeklyStats(long userId, long since, boolean isMock, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.schule.myfitnessTracker.data.db.DailyStats>> $completion);
}