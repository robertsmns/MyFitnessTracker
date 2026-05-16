package com.schule.myfitnessTracker.ui.tracking;

/**
 * ViewModel für die Tracking-Session.
 *
 * Verbindet TrackingService-LiveData mit der UI und
 * startet/stoppt den Service über Intents.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001a\u0010(\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020+0*0)2\u0006\u0010,\u001a\u00020\u0007J\u0006\u0010-\u001a\u00020.J\u0006\u0010/\u001a\u00020.R\u0019\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001f\u0010\u000f\u001a\u0010\u0012\f\u0012\n \u0011*\u0004\u0018\u00010\u00100\u00100\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\tR\u001f\u0010\u0013\u001a\u0010\u0012\f\u0012\n \u0011*\u0004\u0018\u00010\u00070\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\tR\u001f\u0010\u0015\u001a\u0010\u0012\f\u0012\n \u0011*\u0004\u0018\u00010\u00160\u00160\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\tR\u001f\u0010\u0017\u001a\u0010\u0012\f\u0012\n \u0011*\u0004\u0018\u00010\u00160\u00160\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\tR\u0019\u0010\u0018\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00190\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\tR\u001b\u0010\u001b\u001a\u00020\u001c8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u001f\u0010 \u001a\u0004\b\u001d\u0010\u001eR\u001f\u0010!\u001a\u0010\u0012\f\u0012\n \u0011*\u0004\u0018\u00010\u00100\u00100\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\tR\u001f\u0010#\u001a\u0010\u0012\f\u0012\n \u0011*\u0004\u0018\u00010$0$0\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010\tR\u0017\u0010&\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010\u000e\u00a8\u00060"}, d2 = {"Lcom/schule/myfitnessTracker/ui/tracking/TrackingViewModel;", "Landroidx/lifecycle/AndroidViewModel;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "currentRunId", "Landroidx/lifecycle/MutableLiveData;", "", "getCurrentRunId", "()Landroidx/lifecycle/MutableLiveData;", "distanceFormatted", "Landroidx/lifecycle/MediatorLiveData;", "", "getDistanceFormatted", "()Landroidx/lifecycle/MediatorLiveData;", "distanceM", "", "kotlin.jvm.PlatformType", "getDistanceM", "elapsedSec", "getElapsedSec", "isPaused", "", "isTracking", "lastLocation", "Landroid/location/Location;", "getLastLocation", "repository", "Lcom/schule/myfitnessTracker/data/db/FitnessRepository;", "getRepository", "()Lcom/schule/myfitnessTracker/data/db/FitnessRepository;", "repository$delegate", "Lkotlin/Lazy;", "speedKmh", "getSpeedKmh", "stepCount", "", "getStepCount", "timerFormatted", "getTimerFormatted", "getRoutePoints", "Landroidx/lifecycle/LiveData;", "", "Lcom/schule/myfitnessTracker/data/model/RoutePoint;", "runId", "pauseTracking", "", "toggleTracking", "app_debug"})
public final class TrackingViewModel extends androidx.lifecycle.AndroidViewModel {
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy repository$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.lang.Boolean> isTracking = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.lang.Long> currentRunId = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.lang.Float> distanceM = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.lang.Float> speedKmh = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.lang.Integer> stepCount = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.lang.Long> elapsedSec = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<android.location.Location> lastLocation = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MutableLiveData<java.lang.Boolean> isPaused = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MediatorLiveData<java.lang.String> distanceFormatted = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.MediatorLiveData<java.lang.String> timerFormatted = null;
    
    public TrackingViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super(null);
    }
    
    private final com.schule.myfitnessTracker.data.db.FitnessRepository getRepository() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.lang.Boolean> isTracking() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.lang.Long> getCurrentRunId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.lang.Float> getDistanceM() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.lang.Float> getSpeedKmh() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.lang.Integer> getStepCount() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.lang.Long> getElapsedSec() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<android.location.Location> getLastLocation() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MutableLiveData<java.lang.Boolean> isPaused() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MediatorLiveData<java.lang.String> getDistanceFormatted() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.MediatorLiveData<java.lang.String> getTimerFormatted() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.schule.myfitnessTracker.data.model.RoutePoint>> getRoutePoints(long runId) {
        return null;
    }
    
    public final void toggleTracking() {
    }
    
    public final void pauseTracking() {
    }
}