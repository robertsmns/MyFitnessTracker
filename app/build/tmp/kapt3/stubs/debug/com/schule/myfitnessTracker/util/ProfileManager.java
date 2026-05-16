package com.schule.myfitnessTracker.util;

/**
 * Hilfsklasse zum Speichern und Abrufen von Benutzerprofildaten.
 * Wird für die Kalorienberechnung und Personalisierung genutzt.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u000eR$\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u00068F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R$\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0005\u001a\u00020\u000e8F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R$\u0010\u0014\u001a\u00020\u000e2\u0006\u0010\u0005\u001a\u00020\u000e8F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b\u0015\u0010\u0011\"\u0004\b\u0016\u0010\u0013\u00a8\u0006\u001a"}, d2 = {"Lcom/schule/myfitnessTracker/util/ProfileManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "value", "", "name", "getName", "()Ljava/lang/String;", "setName", "(Ljava/lang/String;)V", "prefs", "Landroid/content/SharedPreferences;", "", "targetDistanceKm", "getTargetDistanceKm", "()F", "setTargetDistanceKm", "(F)V", "weight", "getWeight", "setWeight", "calculateCalories", "", "distanceMeters", "app_debug"})
public final class ProfileManager {
    @org.jetbrains.annotations.NotNull()
    private final android.content.SharedPreferences prefs = null;
    
    public ProfileManager(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
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
    
    /**
     * Berechnet die verbrannten Kalorien basierend auf Distanz und Gewicht.
     * Formel: kcal = Strecke (km) * Gewicht (kg) * Aktivitätsfaktor (ca. 0.9 für Laufen)
     */
    public final int calculateCalories(float distanceMeters) {
        return 0;
    }
}