package com.schule.myfitnessTracker.service;

/**
 * Foreground Service für GPS-Tracking.
 *
 * Läuft im Hintergrund und:
 * - Empfängt GPS-Updates (alle 3 Sekunden)
 * - Zählt Schritte via Step Detector Sensor
 * - Berechnet Distanz, Geschwindigkeit und Kalorien
 * - Speichert jeden GPS-Punkt in der Room-DB
 * - Zeigt eine persistente Benachrichtigung
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0086\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u0000 <2\u00020\u00012\u00020\u0002:\u0001<B\u0005\u00a2\u0006\u0002\u0010\u0003J\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!H\u0002J\b\u0010\"\u001a\u00020#H\u0002J\u0010\u0010$\u001a\u00020#2\u0006\u0010%\u001a\u00020\u000bH\u0002J\u0018\u0010&\u001a\u00020#2\u0006\u0010\'\u001a\u00020(2\u0006\u0010)\u001a\u00020\u0017H\u0016J\b\u0010*\u001a\u00020#H\u0016J\b\u0010+\u001a\u00020#H\u0016J\u0010\u0010,\u001a\u00020#2\u0006\u0010-\u001a\u00020.H\u0016J\"\u0010/\u001a\u00020\u00172\b\u00100\u001a\u0004\u0018\u0001012\u0006\u00102\u001a\u00020\u00172\u0006\u00103\u001a\u00020\u0017H\u0016J\b\u00104\u001a\u00020#H\u0002J\b\u00105\u001a\u00020#H\u0002J\b\u00106\u001a\u00020#H\u0002J\b\u00107\u001a\u00020#H\u0002J\b\u00108\u001a\u00020#H\u0002J\b\u00109\u001a\u00020#H\u0002J\b\u0010:\u001a\u00020#H\u0002J\b\u0010;\u001a\u00020#H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0018\u001a\u0004\u0018\u00010\u0019X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006="}, d2 = {"Lcom/schule/myfitnessTracker/service/TrackingService;", "Landroidx/lifecycle/LifecycleService;", "Landroid/hardware/SensorEventListener;", "()V", "fusedLocationClient", "Lcom/google/android/gms/location/FusedLocationProviderClient;", "isPaused", "", "locationCallback", "Lcom/google/android/gms/location/LocationCallback;", "prevLocation", "Landroid/location/Location;", "profileManager", "Lcom/schule/myfitnessTracker/util/ProfileManager;", "repository", "Lcom/schule/myfitnessTracker/data/db/FitnessRepository;", "sensorManager", "Landroid/hardware/SensorManager;", "serviceScope", "Lkotlinx/coroutines/CoroutineScope;", "startTime", "", "stepsAtStart", "", "timerJob", "Lkotlinx/coroutines/Job;", "totalDistance", "", "totalElevationGain", "totalStepsRaw", "buildNotification", "Landroid/app/Notification;", "text", "", "createNotificationChannel", "", "handleNewLocation", "location", "onAccuracyChanged", "sensor", "Landroid/hardware/Sensor;", "accuracy", "onCreate", "onDestroy", "onSensorChanged", "event", "Landroid/hardware/SensorEvent;", "onStartCommand", "intent", "Landroid/content/Intent;", "flags", "startId", "pauseTracking", "requestLocationUpdates", "setupLocationClient", "setupSensors", "startTimer", "startTracking", "stopTracking", "updateNotification", "Companion", "app_debug"})
public final class TrackingService extends androidx.lifecycle.LifecycleService implements android.hardware.SensorEventListener {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ACTION_START = "ACTION_START_TRACKING";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ACTION_STOP = "ACTION_STOP_TRACKING";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ACTION_PAUSE = "ACTION_PAUSE_TRACKING";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String CHANNEL_ID = "fitness_tracking_channel";
    private static final int NOTIFICATION_ID = 1;
    private static final long LOCATION_INTERVAL_MS = 3000L;
    private static final long LOCATION_FASTEST_MS = 1500L;
    @org.jetbrains.annotations.NotNull()
    private static final androidx.lifecycle.MutableLiveData<java.lang.Boolean> isTracking = null;
    @org.jetbrains.annotations.NotNull()
    private static final androidx.lifecycle.MutableLiveData<java.lang.Long> currentRunId = null;
    @org.jetbrains.annotations.NotNull()
    private static final androidx.lifecycle.MutableLiveData<java.lang.Float> distanceM = null;
    @org.jetbrains.annotations.NotNull()
    private static final androidx.lifecycle.MutableLiveData<java.lang.Float> speedKmh = null;
    @org.jetbrains.annotations.NotNull()
    private static final androidx.lifecycle.MutableLiveData<java.lang.Integer> stepCount = null;
    @org.jetbrains.annotations.NotNull()
    private static final androidx.lifecycle.MutableLiveData<java.lang.Long> elapsedSec = null;
    @org.jetbrains.annotations.NotNull()
    private static final androidx.lifecycle.MutableLiveData<android.location.Location> lastLocation = null;
    @org.jetbrains.annotations.NotNull()
    private static final androidx.lifecycle.MutableLiveData<java.lang.Integer> heartRate = null;
    private com.google.android.gms.location.FusedLocationProviderClient fusedLocationClient;
    private com.google.android.gms.location.LocationCallback locationCallback;
    private android.hardware.SensorManager sensorManager;
    private com.schule.myfitnessTracker.data.db.FitnessRepository repository;
    private com.schule.myfitnessTracker.util.ProfileManager profileManager;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope serviceScope = null;
    @org.jetbrains.annotations.Nullable()
    private android.location.Location prevLocation;
    private float totalDistance = 0.0F;
    private float totalElevationGain = 0.0F;
    private long startTime = 0L;
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.Job timerJob;
    private int stepsAtStart = 0;
    private int totalStepsRaw = 0;
    private boolean isPaused = false;
    @org.jetbrains.annotations.NotNull()
    public static final com.schule.myfitnessTracker.service.TrackingService.Companion Companion = null;
    
    public TrackingService() {
        super();
    }
    
    @java.lang.Override()
    public void onCreate() {
    }
    
    @java.lang.Override()
    public int onStartCommand(@org.jetbrains.annotations.Nullable()
    android.content.Intent intent, int flags, int startId) {
        return 0;
    }
    
    @java.lang.Override()
    public void onDestroy() {
    }
    
    private final void setupLocationClient() {
    }
    
    private final void setupSensors() {
    }
    
    private final void startTracking() {
    }
    
    private final void stopTracking() {
    }
    
    private final void pauseTracking() {
    }
    
    private final void requestLocationUpdates() {
    }
    
    private final void handleNewLocation(android.location.Location location) {
    }
    
    @java.lang.Override()
    public void onSensorChanged(@org.jetbrains.annotations.NotNull()
    android.hardware.SensorEvent event) {
    }
    
    @java.lang.Override()
    public void onAccuracyChanged(@org.jetbrains.annotations.NotNull()
    android.hardware.Sensor sensor, int accuracy) {
    }
    
    private final void startTimer() {
    }
    
    private final android.app.Notification buildNotification(java.lang.String text) {
        return null;
    }
    
    private final void updateNotification() {
    }
    
    private final void createNotificationChannel() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082T\u00a2\u0006\u0002\n\u0000R\u0019\u0010\r\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001f\u0010\u0011\u001a\u0010\u0012\f\u0012\n \u0013*\u0004\u0018\u00010\u00120\u00120\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0010R\u001f\u0010\u0015\u001a\u0010\u0012\f\u0012\n \u0013*\u0004\u0018\u00010\t0\t0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0010R\u001f\u0010\u0017\u001a\u0010\u0012\f\u0012\n \u0013*\u0004\u0018\u00010\f0\f0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0010R\u001f\u0010\u0019\u001a\u0010\u0012\f\u0012\n \u0013*\u0004\u0018\u00010\u001a0\u001a0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0010R\u0019\u0010\u001b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u001c0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0010R\u001f\u0010\u001e\u001a\u0010\u0012\f\u0012\n \u0013*\u0004\u0018\u00010\u00120\u00120\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0010R\u001f\u0010 \u001a\u0010\u0012\f\u0012\n \u0013*\u0004\u0018\u00010\f0\f0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0010\u00a8\u0006\""}, d2 = {"Lcom/schule/myfitnessTracker/service/TrackingService$Companion;", "", "()V", "ACTION_PAUSE", "", "ACTION_START", "ACTION_STOP", "CHANNEL_ID", "LOCATION_FASTEST_MS", "", "LOCATION_INTERVAL_MS", "NOTIFICATION_ID", "", "currentRunId", "Landroidx/lifecycle/MutableLiveData;", "getCurrentRunId", "()Landroidx/lifecycle/MutableLiveData;", "distanceM", "", "kotlin.jvm.PlatformType", "getDistanceM", "elapsedSec", "getElapsedSec", "heartRate", "getHeartRate", "isTracking", "", "lastLocation", "Landroid/location/Location;", "getLastLocation", "speedKmh", "getSpeedKmh", "stepCount", "getStepCount", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
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
        public final androidx.lifecycle.MutableLiveData<java.lang.Integer> getHeartRate() {
            return null;
        }
    }
}