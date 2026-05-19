package com.schule.myfitnessTracker.ui.history;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\bJ\u001c\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\u00072\u0006\u0010\u0019\u001a\u00020\u000eH\u0086@\u00a2\u0006\u0002\u0010\u001aJ\u0006\u0010\u001b\u001a\u00020\u0015R\u001d\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR \u0010\u000b\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001c"}, d2 = {"Lcom/schule/myfitnessTracker/ui/history/HistoryViewModel;", "Landroidx/lifecycle/AndroidViewModel;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "allRuns", "Landroidx/lifecycle/LiveData;", "", "Lcom/schule/myfitnessTracker/data/model/Run;", "getAllRuns", "()Landroidx/lifecycle/LiveData;", "modeTrigger", "Landroidx/lifecycle/MutableLiveData;", "Lkotlin/Pair;", "", "", "profileManager", "Lcom/schule/myfitnessTracker/util/ProfileManager;", "repository", "Lcom/schule/myfitnessTracker/data/db/FitnessRepository;", "deleteRun", "", "run", "getRoutePoints", "Lcom/schule/myfitnessTracker/data/model/RoutePoint;", "runId", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "refreshMode", "app_debug"})
public final class HistoryViewModel extends androidx.lifecycle.AndroidViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.schule.myfitnessTracker.data.db.FitnessRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.schule.myfitnessTracker.util.ProfileManager profileManager = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<kotlin.Pair<java.lang.Long, java.lang.Boolean>> modeTrigger = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<java.util.List<com.schule.myfitnessTracker.data.model.Run>> allRuns = null;
    
    public HistoryViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super(null);
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.schule.myfitnessTracker.data.model.Run>> getAllRuns() {
        return null;
    }
    
    public final void refreshMode() {
    }
    
    public final void deleteRun(@org.jetbrains.annotations.NotNull()
    com.schule.myfitnessTracker.data.model.Run run) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getRoutePoints(long runId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.schule.myfitnessTracker.data.model.RoutePoint>> $completion) {
        return null;
    }
}