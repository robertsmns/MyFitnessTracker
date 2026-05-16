package com.schule.myfitnessTracker.util;

/**
 * Hilfsklasse zum Exportieren eines Runs als GPX-Datei.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0002J$\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b\u00a8\u0006\u000e"}, d2 = {"Lcom/schule/myfitnessTracker/util/GpxExporter;", "", "()V", "buildGpxString", "", "run", "Lcom/schule/myfitnessTracker/data/model/Run;", "points", "", "Lcom/schule/myfitnessTracker/data/model/RoutePoint;", "shareGpx", "", "context", "Landroid/content/Context;", "app_debug"})
public final class GpxExporter {
    @org.jetbrains.annotations.NotNull()
    public static final com.schule.myfitnessTracker.util.GpxExporter INSTANCE = null;
    
    private GpxExporter() {
        super();
    }
    
    public final void shareGpx(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.schule.myfitnessTracker.data.model.Run run, @org.jetbrains.annotations.NotNull()
    java.util.List<com.schule.myfitnessTracker.data.model.RoutePoint> points) {
    }
    
    private final java.lang.String buildGpxString(com.schule.myfitnessTracker.data.model.Run run, java.util.List<com.schule.myfitnessTracker.data.model.RoutePoint> points) {
        return null;
    }
}