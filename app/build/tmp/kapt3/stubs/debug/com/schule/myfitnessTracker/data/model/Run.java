package com.schule.myfitnessTracker.data.model;

/**
 * Repräsentiert eine einzelne Trainingseinheit (Lauf/Spaziergang).
 *
 * Wird in der Room-Datenbank in der Tabelle "runs" gespeichert.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u001d\b\u0087\b\u0018\u00002\u00020\u0001B_\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u0012\b\b\u0002\u0010\u000b\u001a\u00020\n\u0012\b\b\u0002\u0010\f\u001a\u00020\u0007\u0012\b\b\u0002\u0010\r\u001a\u00020\u000e\u00a2\u0006\u0002\u0010\u000fJ\t\u0010$\u001a\u00020\u0003H\u00c6\u0003J\t\u0010%\u001a\u00020\u0003H\u00c6\u0003J\t\u0010&\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\'\u001a\u00020\u0007H\u00c6\u0003J\t\u0010(\u001a\u00020\u0007H\u00c6\u0003J\t\u0010)\u001a\u00020\nH\u00c6\u0003J\t\u0010*\u001a\u00020\nH\u00c6\u0003J\t\u0010+\u001a\u00020\u0007H\u00c6\u0003J\t\u0010,\u001a\u00020\u000eH\u00c6\u0003Jc\u0010-\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\n2\b\b\u0002\u0010\f\u001a\u00020\u00072\b\b\u0002\u0010\r\u001a\u00020\u000eH\u00c6\u0001J\u0013\u0010.\u001a\u00020\u000e2\b\u0010/\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00100\u001a\u00020\nH\u00d6\u0001J\t\u00101\u001a\u00020\u0015H\u00d6\u0001R\u0011\u0010\b\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u000b\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0014\u001a\u00020\u00158F\u00a2\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0011R\u0011\u0010\u0019\u001a\u00020\u00158F\u00a2\u0006\u0006\u001a\u0004\b\u001a\u0010\u0017R\u0011\u0010\u001b\u001a\u00020\u00038F\u00a2\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\f\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0011R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u001dR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u001dR\u0011\u0010\r\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010!R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u001dR\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0013\u00a8\u00062"}, d2 = {"Lcom/schule/myfitnessTracker/data/model/Run;", "", "id", "", "startTime", "endTime", "distanceMeters", "", "avgSpeedKmh", "steps", "", "calories", "elevationGain", "isActive", "", "(JJJFFIIFZ)V", "getAvgSpeedKmh", "()F", "getCalories", "()I", "distanceFormatted", "", "getDistanceFormatted", "()Ljava/lang/String;", "getDistanceMeters", "durationFormatted", "getDurationFormatted", "durationSeconds", "getDurationSeconds", "()J", "getElevationGain", "getEndTime", "getId", "()Z", "getStartTime", "getSteps", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "app_debug"})
@androidx.room.Entity(tableName = "runs")
public final class Run {
    @androidx.room.PrimaryKey(autoGenerate = true)
    private final long id = 0L;
    
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
    
    public Run(long id, long startTime, long endTime, float distanceMeters, float avgSpeedKmh, int steps, int calories, float elevationGain, boolean isActive) {
        super();
    }
    
    public final long getId() {
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
    
    public Run() {
        super();
    }
    
    public final long component1() {
        return 0L;
    }
    
    public final long component2() {
        return 0L;
    }
    
    public final long component3() {
        return 0L;
    }
    
    public final float component4() {
        return 0.0F;
    }
    
    public final float component5() {
        return 0.0F;
    }
    
    public final int component6() {
        return 0;
    }
    
    public final int component7() {
        return 0;
    }
    
    public final float component8() {
        return 0.0F;
    }
    
    public final boolean component9() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.schule.myfitnessTracker.data.model.Run copy(long id, long startTime, long endTime, float distanceMeters, float avgSpeedKmh, int steps, int calories, float elevationGain, boolean isActive) {
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