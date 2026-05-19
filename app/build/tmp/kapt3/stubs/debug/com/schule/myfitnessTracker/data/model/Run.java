package com.schule.myfitnessTracker.data.model;

/**
 * Repräsentiert eine einzelne Trainingseinheit (Lauf/Spaziergang).
 *
 * Wird in der Room-Datenbank in der Tabelle "runs" gespeichert.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b+\b\u0087\b\u0018\u00002\u00020\u0001B\u0085\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\b\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\u000b\u0012\b\b\u0002\u0010\r\u001a\u00020\b\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000f\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u000f\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0012\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0012\u00a2\u0006\u0002\u0010\u0014J\t\u0010+\u001a\u00020\u0003H\u00c6\u0003J\t\u0010,\u001a\u00020\u000fH\u00c6\u0003J\t\u0010-\u001a\u00020\u000fH\u00c6\u0003J\t\u0010.\u001a\u00020\u0012H\u00c6\u0003J\t\u0010/\u001a\u00020\u0012H\u00c6\u0003J\t\u00100\u001a\u00020\u0003H\u00c6\u0003J\t\u00101\u001a\u00020\u0003H\u00c6\u0003J\t\u00102\u001a\u00020\u0003H\u00c6\u0003J\t\u00103\u001a\u00020\bH\u00c6\u0003J\t\u00104\u001a\u00020\bH\u00c6\u0003J\t\u00105\u001a\u00020\u000bH\u00c6\u0003J\t\u00106\u001a\u00020\u000bH\u00c6\u0003J\t\u00107\u001a\u00020\bH\u00c6\u0003J\u008b\u0001\u00108\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\b2\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\u000b2\b\b\u0002\u0010\r\u001a\u00020\b2\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u000f2\b\b\u0002\u0010\u0011\u001a\u00020\u00122\b\b\u0002\u0010\u0013\u001a\u00020\u0012H\u00c6\u0001J\u0013\u00109\u001a\u00020\u000f2\b\u0010:\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010;\u001a\u00020\u000bH\u00d6\u0001J\t\u0010<\u001a\u00020\u0012H\u00d6\u0001R\u0011\u0010\u0013\u001a\u00020\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\t\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\f\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\u001b\u001a\u00020\u00128F\u00a2\u0006\u0006\u001a\u0004\b\u001c\u0010\u0016R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0018R\u0011\u0010\u001e\u001a\u00020\u00128F\u00a2\u0006\u0006\u001a\u0004\b\u001f\u0010\u0016R\u0011\u0010 \u001a\u00020\u00038F\u00a2\u0006\u0006\u001a\u0004\b!\u0010\"R\u0011\u0010\r\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0018R\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010\"R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010\"R\u0011\u0010\u000e\u001a\u00020\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010&R\u0011\u0010\u0010\u001a\u00020\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010&R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010\"R\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010\u001aR\u0011\u0010\u0011\u001a\u00020\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010\u0016R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010\"\u00a8\u0006="}, d2 = {"Lcom/schule/myfitnessTracker/data/model/Run;", "", "id", "", "userId", "startTime", "endTime", "distanceMeters", "", "avgSpeedKmh", "steps", "", "calories", "elevationGain", "isActive", "", "isMock", "trackingMode", "", "activityType", "(JJJJFFIIFZZLjava/lang/String;Ljava/lang/String;)V", "getActivityType", "()Ljava/lang/String;", "getAvgSpeedKmh", "()F", "getCalories", "()I", "distanceFormatted", "getDistanceFormatted", "getDistanceMeters", "durationFormatted", "getDurationFormatted", "durationSeconds", "getDurationSeconds", "()J", "getElevationGain", "getEndTime", "getId", "()Z", "getStartTime", "getSteps", "getTrackingMode", "getUserId", "component1", "component10", "component11", "component12", "component13", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "app_debug"})
@androidx.room.Entity(tableName = "runs", foreignKeys = {@androidx.room.ForeignKey(entity = com.schule.myfitnessTracker.data.model.User.class, parentColumns = {"id"}, childColumns = {"userId"}, onDelete = 5)}, indices = {@androidx.room.Index(value = {"userId"})})
public final class Run {
    @androidx.room.PrimaryKey(autoGenerate = true)
    private final long id = 0L;
    
    /**
     * Zu welchem User gehört dieser Run
     */
    private final long userId = 0L;
    
    /**
     * Startzeit in Millisekunden (Unix-Timestamp)
     */
    private final long startTime = 0L;
    
    /**
     * Endzeit in Millisekunden
     */
    private final long endTime = 0L;
    
    /**
     * Zurückgelegte Distanz in Metern
     */
    private final float distanceMeters = 0.0F;
    
    /**
     * Durchschnittsgeschwindigkeit in km/h
     */
    private final float avgSpeedKmh = 0.0F;
    
    /**
     * Schritte (Step Counter Sensor)
     */
    private final int steps = 0;
    
    /**
     * Verbrannte Kalorien (Schätzung)
     */
    private final int calories = 0;
    
    /**
     * Höhenmeter gesamt (kumuliert)
     */
    private final float elevationGain = 0.0F;
    
    /**
     * Ist die Session noch aktiv?
     */
    private final boolean isActive = false;
    
    /**
     * Ist dies ein simulierter Lauf (Mock-Daten)?
     */
    private final boolean isMock = false;
    
    /**
     * Tracking Modus: "ACTIVE" (Training) oder "PASSIVE" (Hintergrund/Alltag)
     */
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String trackingMode = null;
    
    /**
     * Typ der Aktivität (WALKING, RUNNING, BICYCLE, etc.)
     */
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String activityType = null;
    
    public Run(long id, long userId, long startTime, long endTime, float distanceMeters, float avgSpeedKmh, int steps, int calories, float elevationGain, boolean isActive, boolean isMock, @org.jetbrains.annotations.NotNull()
    java.lang.String trackingMode, @org.jetbrains.annotations.NotNull()
    java.lang.String activityType) {
        super();
    }
    
    public final long getId() {
        return 0L;
    }
    
    /**
     * Zu welchem User gehört dieser Run
     */
    public final long getUserId() {
        return 0L;
    }
    
    /**
     * Startzeit in Millisekunden (Unix-Timestamp)
     */
    public final long getStartTime() {
        return 0L;
    }
    
    /**
     * Endzeit in Millisekunden
     */
    public final long getEndTime() {
        return 0L;
    }
    
    /**
     * Zurückgelegte Distanz in Metern
     */
    public final float getDistanceMeters() {
        return 0.0F;
    }
    
    /**
     * Durchschnittsgeschwindigkeit in km/h
     */
    public final float getAvgSpeedKmh() {
        return 0.0F;
    }
    
    /**
     * Schritte (Step Counter Sensor)
     */
    public final int getSteps() {
        return 0;
    }
    
    /**
     * Verbrannte Kalorien (Schätzung)
     */
    public final int getCalories() {
        return 0;
    }
    
    /**
     * Höhenmeter gesamt (kumuliert)
     */
    public final float getElevationGain() {
        return 0.0F;
    }
    
    /**
     * Ist die Session noch aktiv?
     */
    public final boolean isActive() {
        return false;
    }
    
    /**
     * Ist dies ein simulierter Lauf (Mock-Daten)?
     */
    public final boolean isMock() {
        return false;
    }
    
    /**
     * Tracking Modus: "ACTIVE" (Training) oder "PASSIVE" (Hintergrund/Alltag)
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTrackingMode() {
        return null;
    }
    
    /**
     * Typ der Aktivität (WALKING, RUNNING, BICYCLE, etc.)
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getActivityType() {
        return null;
    }
    
    public final long getDurationSeconds() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDurationFormatted() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDistanceFormatted() {
        return null;
    }
    
    public final long component1() {
        return 0L;
    }
    
    public final boolean component10() {
        return false;
    }
    
    public final boolean component11() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component12() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component13() {
        return null;
    }
    
    public final long component2() {
        return 0L;
    }
    
    public final long component3() {
        return 0L;
    }
    
    public final long component4() {
        return 0L;
    }
    
    public final float component5() {
        return 0.0F;
    }
    
    public final float component6() {
        return 0.0F;
    }
    
    public final int component7() {
        return 0;
    }
    
    public final int component8() {
        return 0;
    }
    
    public final float component9() {
        return 0.0F;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.schule.myfitnessTracker.data.model.Run copy(long id, long userId, long startTime, long endTime, float distanceMeters, float avgSpeedKmh, int steps, int calories, float elevationGain, boolean isActive, boolean isMock, @org.jetbrains.annotations.NotNull()
    java.lang.String trackingMode, @org.jetbrains.annotations.NotNull()
    java.lang.String activityType) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}