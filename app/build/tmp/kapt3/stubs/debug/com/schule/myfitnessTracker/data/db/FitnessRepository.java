package com.schule.myfitnessTracker.data.db;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\t\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0086@\u00a2\u0006\u0002\u0010\u001fJ\u0016\u0010 \u001a\u00020\u001c2\u0006\u0010!\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\"J\u0014\u0010#\u001a\b\u0012\u0004\u0012\u00020\f0\u00062\u0006\u0010$\u001a\u00020%J>\u0010&\u001a\u00020\u001c2\u0006\u0010\'\u001a\u00020%2\u0006\u0010(\u001a\u00020\f2\u0006\u0010)\u001a\u00020\f2\u0006\u0010*\u001a\u00020\u00152\u0006\u0010+\u001a\u00020\u00152\u0006\u0010,\u001a\u00020\fH\u0086@\u00a2\u0006\u0002\u0010-J\u0010\u0010.\u001a\u0004\u0018\u00010\bH\u0086@\u00a2\u0006\u0002\u0010/J\u001c\u00100\u001a\b\u0012\u0004\u0012\u00020\u001e0\u00072\u0006\u0010\'\u001a\u00020%H\u0086@\u00a2\u0006\u0002\u00101J\u001a\u00102\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001e0\u00070\u00062\u0006\u0010\'\u001a\u00020%J\u0018\u00103\u001a\u0004\u0018\u00010\b2\u0006\u00104\u001a\u00020%H\u0086@\u00a2\u0006\u0002\u00101J\u0014\u00105\u001a\b\u0012\u0004\u0012\u0002060\u0007H\u0086@\u00a2\u0006\u0002\u0010/J\u000e\u00107\u001a\u00020%H\u0086@\u00a2\u0006\u0002\u0010/R\u001d\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u000e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\nR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\nR\u0017\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\f0\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\nR\u0017\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00150\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\n\u00a8\u00068"}, d2 = {"Lcom/schule/myfitnessTracker/data/db/FitnessRepository;", "", "db", "Lcom/schule/myfitnessTracker/data/db/FitnessDatabase;", "(Lcom/schule/myfitnessTracker/data/db/FitnessDatabase;)V", "allRuns", "Landroidx/lifecycle/LiveData;", "", "Lcom/schule/myfitnessTracker/data/model/Run;", "getAllRuns", "()Landroidx/lifecycle/LiveData;", "avgSpeed", "", "getAvgSpeed", "lastRun", "getLastRun", "pointDao", "Lcom/schule/myfitnessTracker/data/db/RoutePointDao;", "runDao", "Lcom/schule/myfitnessTracker/data/db/RunDao;", "todayCalories", "", "getTodayCalories", "todayDistance", "getTodayDistance", "todaySteps", "getTodaySteps", "addRoutePoint", "", "point", "Lcom/schule/myfitnessTracker/data/model/RoutePoint;", "(Lcom/schule/myfitnessTracker/data/model/RoutePoint;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteRun", "run", "(Lcom/schule/myfitnessTracker/data/model/Run;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "distanceSince", "since", "", "finishRun", "runId", "distanceMeters", "avgSpeedKmh", "steps", "calories", "elevationGain", "(JFFIIFLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getActiveRun", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getRouteForRun", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getRouteForRunLive", "getRunById", "id", "getWeeklyStats", "Lcom/schule/myfitnessTracker/data/db/DailyStats;", "startNewRun", "app_debug"})
public final class FitnessRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.schule.myfitnessTracker.data.db.FitnessDatabase db = null;
    @org.jetbrains.annotations.NotNull()
    private final com.schule.myfitnessTracker.data.db.RunDao runDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.schule.myfitnessTracker.data.db.RoutePointDao pointDao = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.util.List<com.schule.myfitnessTracker.data.model.Run>> allRuns = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.lang.Float> todayDistance = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.lang.Integer> todaySteps = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.lang.Integer> todayCalories = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<com.schule.myfitnessTracker.data.model.Run> lastRun = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.lang.Float> avgSpeed = null;
    
    public FitnessRepository(@org.jetbrains.annotations.NotNull()
    com.schule.myfitnessTracker.data.db.FitnessDatabase db) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.schule.myfitnessTracker.data.model.Run>> getAllRuns() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.Float> getTodayDistance() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.Integer> getTodaySteps() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.Integer> getTodayCalories() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.schule.myfitnessTracker.data.model.Run> getLastRun() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.Float> getAvgSpeed() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.Float> distanceSince(long since) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object startNewRun(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object finishRun(long runId, float distanceMeters, float avgSpeedKmh, int steps, int calories, float elevationGain, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getActiveRun(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.schule.myfitnessTracker.data.model.Run> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getRunById(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.schule.myfitnessTracker.data.model.Run> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getWeeklyStats(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.schule.myfitnessTracker.data.db.DailyStats>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object addRoutePoint(@org.jetbrains.annotations.NotNull()
    com.schule.myfitnessTracker.data.model.RoutePoint point, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getRouteForRun(long runId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.schule.myfitnessTracker.data.model.RoutePoint>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.schule.myfitnessTracker.data.model.RoutePoint>> getRouteForRunLive(long runId) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteRun(@org.jetbrains.annotations.NotNull()
    com.schule.myfitnessTracker.data.model.Run run, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}