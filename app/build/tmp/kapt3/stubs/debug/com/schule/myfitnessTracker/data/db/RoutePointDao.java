package com.schule.myfitnessTracker.data.db;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u001c\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u001c\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u000b2\u0006\u0010\u0004\u001a\u00020\u0005H\'J\u0016\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\u000eJ\u001c\u0010\u000f\u001a\u00020\u00032\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u00a7@\u00a2\u0006\u0002\u0010\u0011\u00a8\u0006\u0012"}, d2 = {"Lcom/schule/myfitnessTracker/data/db/RoutePointDao;", "", "deletePointsForRun", "", "runId", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getPointsForRun", "", "Lcom/schule/myfitnessTracker/data/model/RoutePoint;", "getPointsForRunLive", "Landroidx/lifecycle/LiveData;", "insertPoint", "point", "(Lcom/schule/myfitnessTracker/data/model/RoutePoint;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertPoints", "points", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao()
public abstract interface RoutePointDao {
    
    @androidx.room.Insert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertPoint(@org.jetbrains.annotations.NotNull()
    com.schule.myfitnessTracker.data.model.RoutePoint point, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Insert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertPoints(@org.jetbrains.annotations.NotNull()
    java.util.List<com.schule.myfitnessTracker.data.model.RoutePoint> points, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Alle GPS-Punkte eines Runs
     */
    @androidx.room.Query(value = "SELECT * FROM route_points WHERE runId = :runId ORDER BY timestamp ASC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getPointsForRun(long runId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.schule.myfitnessTracker.data.model.RoutePoint>> $completion);
    
    /**
     * GPS-Punkte als LiveData (für Live-Karte während Tracking)
     */
    @androidx.room.Query(value = "SELECT * FROM route_points WHERE runId = :runId ORDER BY timestamp ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract androidx.lifecycle.LiveData<java.util.List<com.schule.myfitnessTracker.data.model.RoutePoint>> getPointsForRunLive(long runId);
    
    @androidx.room.Query(value = "DELETE FROM route_points WHERE runId = :runId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deletePointsForRun(long runId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}