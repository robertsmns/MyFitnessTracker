package com.schule.myfitnessTracker.ui.dashboard;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020\u000bJ\u001c\u0010&\u001a\b\u0012\u0004\u0012\u00020\'0\u00072\u0006\u0010(\u001a\u00020)H\u0086@\u00a2\u0006\u0002\u0010*J\u0006\u0010+\u001a\u00020$J\u0016\u0010,\u001a\u00020$2\u0006\u0010-\u001a\u00020\u001b2\u0006\u0010.\u001a\u00020\u000fR\u001a\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\u00070\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\rR\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u000f0\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\rR\u0017\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\rR\u001f\u0010\u001a\u001a\u0010\u0012\f\u0012\n \u001c*\u0004\u0018\u00010\u001b0\u001b0\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u001f\u0010\u001f\u001a\u0010\u0012\f\u0012\n \u001c*\u0004\u0018\u00010\u000f0\u000f0\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u001eR\u001d\u0010!\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\r\u00a8\u0006/"}, d2 = {"Lcom/schule/myfitnessTracker/ui/dashboard/DashboardViewModel;", "Landroidx/lifecycle/AndroidViewModel;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "_weeklyStats", "Landroidx/lifecycle/MutableLiveData;", "", "Lcom/schule/myfitnessTracker/data/db/DailyStats;", "allRuns", "Landroidx/lifecycle/LiveData;", "Lcom/schule/myfitnessTracker/data/model/Run;", "getAllRuns", "()Landroidx/lifecycle/LiveData;", "avgSpeed", "", "getAvgSpeed", "profileManager", "Lcom/schule/myfitnessTracker/util/ProfileManager;", "repository", "Lcom/schule/myfitnessTracker/data/db/FitnessRepository;", "todayDistance", "getTodayDistance", "todaySteps", "", "getTodaySteps", "userName", "", "kotlin.jvm.PlatformType", "getUserName", "()Landroidx/lifecycle/MutableLiveData;", "userWeight", "getUserWeight", "weeklyStats", "getWeeklyStats", "deleteRun", "", "run", "getRoutePoints", "Lcom/schule/myfitnessTracker/data/model/RoutePoint;", "runId", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "loadWeeklyStats", "updateProfile", "name", "weight", "app_debug"})
public final class DashboardViewModel extends androidx.lifecycle.AndroidViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.schule.myfitnessTracker.data.db.FitnessRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.schule.myfitnessTracker.util.ProfileManager profileManager = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.lang.Float> todayDistance = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.lang.Integer> todaySteps = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.util.List<com.schule.myfitnessTracker.data.model.Run>> allRuns = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.lang.Float> avgSpeed = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.lang.String> userName = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.lang.Float> userWeight = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.util.List<com.schule.myfitnessTracker.data.db.DailyStats>> _weeklyStats = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.util.List<com.schule.myfitnessTracker.data.db.DailyStats>> weeklyStats = null;
    
    public DashboardViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super(null);
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
    public final androidx.lifecycle.LiveData<java.util.List<com.schule.myfitnessTracker.data.model.Run>> getAllRuns() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.lang.Float> getAvgSpeed() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.lang.String> getUserName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.lang.Float> getUserWeight() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.schule.myfitnessTracker.data.db.DailyStats>> getWeeklyStats() {
        return null;
    }
    
    public final void loadWeeklyStats() {
    }
    
    public final void deleteRun(@org.jetbrains.annotations.NotNull()
    com.schule.myfitnessTracker.data.model.Run run) {
    }
    
    public final void updateProfile(@org.jetbrains.annotations.NotNull()
    java.lang.String name, float weight) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getRoutePoints(long runId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.schule.myfitnessTracker.data.model.RoutePoint>> $completion) {
        return null;
    }
}