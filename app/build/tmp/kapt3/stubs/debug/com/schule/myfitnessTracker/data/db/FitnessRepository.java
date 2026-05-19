package com.schule.myfitnessTracker.data.db;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0086@\u00a2\u0006\u0002\u0010\u000fJ\u0016\u0010\u0010\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\u0012H\u0086@\u00a2\u0006\u0002\u0010\u0013J>\u0010\u0014\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u00182\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001b2\u0006\u0010\u001d\u001a\u00020\u0018H\u0086@\u00a2\u0006\u0002\u0010\u001eJ \u0010\u001f\u001a\u0004\u0018\u00010\u00122\u0006\u0010 \u001a\u00020\u00162\u0006\u0010!\u001a\u00020\"H\u0086@\u00a2\u0006\u0002\u0010#J\"\u0010$\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120&0%2\u0006\u0010 \u001a\u00020\u00162\u0006\u0010!\u001a\u00020\"J\u001c\u0010\'\u001a\b\u0012\u0004\u0012\u00020\u00180%2\u0006\u0010 \u001a\u00020\u00162\u0006\u0010!\u001a\u00020\"J\u001e\u0010(\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00120%2\u0006\u0010 \u001a\u00020\u00162\u0006\u0010!\u001a\u00020\"J\u001c\u0010)\u001a\b\u0012\u0004\u0012\u00020\u000e0&2\u0006\u0010\u0015\u001a\u00020\u0016H\u0086@\u00a2\u0006\u0002\u0010*J\u001a\u0010+\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0&0%2\u0006\u0010\u0015\u001a\u00020\u0016J\u0018\u0010,\u001a\u0004\u0018\u00010\u00122\u0006\u0010-\u001a\u00020\u0016H\u0086@\u00a2\u0006\u0002\u0010*J\u001c\u0010.\u001a\b\u0012\u0004\u0012\u00020\u001b0%2\u0006\u0010 \u001a\u00020\u00162\u0006\u0010!\u001a\u00020\"J\u001c\u0010/\u001a\b\u0012\u0004\u0012\u00020\u00180%2\u0006\u0010 \u001a\u00020\u00162\u0006\u0010!\u001a\u00020\"J\u001c\u00100\u001a\b\u0012\u0004\u0012\u00020\u001b0%2\u0006\u0010 \u001a\u00020\u00162\u0006\u0010!\u001a\u00020\"J\u0018\u00101\u001a\u0004\u0018\u0001022\u0006\u00103\u001a\u000204H\u0086@\u00a2\u0006\u0002\u00105J\u0018\u00106\u001a\u0004\u0018\u0001022\u0006\u0010 \u001a\u00020\u0016H\u0086@\u00a2\u0006\u0002\u0010*J\u0016\u00107\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001020%2\u0006\u0010 \u001a\u00020\u0016J\u0018\u00108\u001a\u0004\u0018\u0001022\u0006\u00109\u001a\u000204H\u0086@\u00a2\u0006\u0002\u00105J\u0018\u0010:\u001a\u0004\u0018\u0001022\u0006\u0010;\u001a\u000204H\u0086@\u00a2\u0006\u0002\u00105J\u000e\u0010<\u001a\u00020\u001bH\u0086@\u00a2\u0006\u0002\u0010=J$\u0010>\u001a\b\u0012\u0004\u0012\u00020?0&2\u0006\u0010 \u001a\u00020\u00162\u0006\u0010!\u001a\u00020\"H\u0086@\u00a2\u0006\u0002\u0010#J\u0016\u0010@\u001a\u00020\u00162\u0006\u0010\u0011\u001a\u00020\u0012H\u0086@\u00a2\u0006\u0002\u0010\u0013J\u0016\u0010A\u001a\u00020\u00162\u0006\u0010B\u001a\u000202H\u0086@\u00a2\u0006\u0002\u0010CJ4\u0010D\u001a\u00020\u00162\u0006\u0010 \u001a\u00020\u00162\b\b\u0002\u0010!\u001a\u00020\"2\b\b\u0002\u0010E\u001a\u0002042\b\b\u0002\u0010F\u001a\u000204H\u0086@\u00a2\u0006\u0002\u0010GJ\u0016\u0010H\u001a\u00020\f2\u0006\u0010B\u001a\u000202H\u0086@\u00a2\u0006\u0002\u0010CR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006I"}, d2 = {"Lcom/schule/myfitnessTracker/data/db/FitnessRepository;", "", "db", "Lcom/schule/myfitnessTracker/data/db/FitnessDatabase;", "(Lcom/schule/myfitnessTracker/data/db/FitnessDatabase;)V", "pointDao", "Lcom/schule/myfitnessTracker/data/db/RoutePointDao;", "runDao", "Lcom/schule/myfitnessTracker/data/db/RunDao;", "userDao", "Lcom/schule/myfitnessTracker/data/db/UserDao;", "addRoutePoint", "", "point", "Lcom/schule/myfitnessTracker/data/model/RoutePoint;", "(Lcom/schule/myfitnessTracker/data/model/RoutePoint;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteRun", "run", "Lcom/schule/myfitnessTracker/data/model/Run;", "(Lcom/schule/myfitnessTracker/data/model/Run;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "finishRun", "runId", "", "distanceMeters", "", "avgSpeedKmh", "steps", "", "calories", "elevationGain", "(JFFIIFLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getActiveRun", "userId", "isMock", "", "(JZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllRuns", "Landroidx/lifecycle/LiveData;", "", "getAvgSpeed", "getLastRun", "getRouteForRun", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getRouteForRunLive", "getRunById", "id", "getTodayCalories", "getTodayDistance", "getTodaySteps", "getUserByEmail", "Lcom/schule/myfitnessTracker/data/model/User;", "email", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getUserById", "getUserByIdLive", "getUserByIdentifier", "identifier", "getUserByUsername", "username", "getUserCount", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getWeeklyStats", "Lcom/schule/myfitnessTracker/data/db/DailyStats;", "insertFullRun", "insertUser", "user", "(Lcom/schule/myfitnessTracker/data/model/User;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "startNewRun", "trackingMode", "activityType", "(JZLjava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateUser", "app_debug"})
public final class FitnessRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.schule.myfitnessTracker.data.db.FitnessDatabase db = null;
    @org.jetbrains.annotations.NotNull()
    private final com.schule.myfitnessTracker.data.db.UserDao userDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.schule.myfitnessTracker.data.db.RunDao runDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.schule.myfitnessTracker.data.db.RoutePointDao pointDao = null;
    
    public FitnessRepository(@org.jetbrains.annotations.NotNull()
    com.schule.myfitnessTracker.data.db.FitnessDatabase db) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object insertUser(@org.jetbrains.annotations.NotNull()
    com.schule.myfitnessTracker.data.model.User user, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateUser(@org.jetbrains.annotations.NotNull()
    com.schule.myfitnessTracker.data.model.User user, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getUserByEmail(@org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.schule.myfitnessTracker.data.model.User> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getUserByUsername(@org.jetbrains.annotations.NotNull()
    java.lang.String username, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.schule.myfitnessTracker.data.model.User> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getUserByIdentifier(@org.jetbrains.annotations.NotNull()
    java.lang.String identifier, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.schule.myfitnessTracker.data.model.User> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getUserById(long userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.schule.myfitnessTracker.data.model.User> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.schule.myfitnessTracker.data.model.User> getUserByIdLive(long userId) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getUserCount(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.schule.myfitnessTracker.data.model.Run>> getAllRuns(long userId, boolean isMock) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.Float> getTodayDistance(long userId, boolean isMock) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.Integer> getTodaySteps(long userId, boolean isMock) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.Integer> getTodayCalories(long userId, boolean isMock) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.schule.myfitnessTracker.data.model.Run> getLastRun(long userId, boolean isMock) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.Float> getAvgSpeed(long userId, boolean isMock) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object startNewRun(long userId, boolean isMock, @org.jetbrains.annotations.NotNull()
    java.lang.String trackingMode, @org.jetbrains.annotations.NotNull()
    java.lang.String activityType, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object insertFullRun(@org.jetbrains.annotations.NotNull()
    com.schule.myfitnessTracker.data.model.Run run, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object finishRun(long runId, float distanceMeters, float avgSpeedKmh, int steps, int calories, float elevationGain, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getActiveRun(long userId, boolean isMock, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.schule.myfitnessTracker.data.model.Run> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getRunById(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.schule.myfitnessTracker.data.model.Run> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getWeeklyStats(long userId, boolean isMock, @org.jetbrains.annotations.NotNull()
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