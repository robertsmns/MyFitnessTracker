package com.schule.myfitnessTracker.util;

/**
 * Hilfsklasse zum Speichern und Abrufen von Benutzerprofildaten.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020\u001bJ\u0010\u0010\'\u001a\u0004\u0018\u00010\u00132\u0006\u0010(\u001a\u00020)R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R$\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u00068F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR$\u0010\r\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\f8F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R$\u0010\u0011\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\f8F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b\u0011\u0010\u000e\"\u0004\b\u0012\u0010\u0010R$\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0005\u001a\u00020\u00138F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u0004\u00a2\u0006\u0002\n\u0000R$\u0010\u001c\u001a\u00020\u001b2\u0006\u0010\u0005\u001a\u00020\u001b8F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R$\u0010!\u001a\u00020\u001b2\u0006\u0010\u0005\u001a\u00020\u001b8F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b\"\u0010\u001e\"\u0004\b#\u0010 \u00a8\u0006*"}, d2 = {"Lcom/schule/myfitnessTracker/util/ProfileManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "value", "", "currentUserId", "getCurrentUserId", "()J", "setCurrentUserId", "(J)V", "", "isDarkMode", "()Z", "setDarkMode", "(Z)V", "isSimulationMode", "setSimulationMode", "", "name", "getName", "()Ljava/lang/String;", "setName", "(Ljava/lang/String;)V", "prefs", "Landroid/content/SharedPreferences;", "", "targetDistanceKm", "getTargetDistanceKm", "()F", "setTargetDistanceKm", "(F)V", "weight", "getWeight", "setWeight", "calculateCalories", "", "distanceMeters", "saveProfilePicture", "uri", "Landroid/net/Uri;", "app_debug"})
public final class ProfileManager {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final android.content.SharedPreferences prefs = null;
    
    public ProfileManager(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    public final long getCurrentUserId() {
        return 0L;
    }
    
    public final void setCurrentUserId(long value) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getName() {
        return null;
    }
    
    public final void setName(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    public final float getWeight() {
        return 0.0F;
    }
    
    public final void setWeight(float value) {
    }
    
    public final float getTargetDistanceKm() {
        return 0.0F;
    }
    
    public final void setTargetDistanceKm(float value) {
    }
    
    public final boolean isSimulationMode() {
        return false;
    }
    
    public final void setSimulationMode(boolean value) {
    }
    
    public final boolean isDarkMode() {
        return false;
    }
    
    public final void setDarkMode(boolean value) {
    }
    
    /**
     * Kopiert ein Bild vom gegebenen URI in den internen App-Speicher.
     * Verhindert SecurityExceptions bei Neustart der App.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String saveProfilePicture(@org.jetbrains.annotations.NotNull()
    android.net.Uri uri) {
        return null;
    }
    
    public final int calculateCalories(float distanceMeters) {
        return 0;
    }
}