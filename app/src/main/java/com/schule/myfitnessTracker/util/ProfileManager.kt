package com.schule.myfitnessTracker.util

import android.content.Context
import android.content.SharedPreferences

/**
 * Hilfsklasse zum Speichern und Abrufen von Benutzerprofildaten.
 * Wird für die Kalorienberechnung und Personalisierung genutzt.
 */
class ProfileManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("user_profile", Context.MODE_PRIVATE)

    var name: String
        get() = prefs.getString("name", "") ?: ""
        set(value) = prefs.edit().putString("name", value).apply()

    var weight: Float
        get() = prefs.getFloat("weight", 75f) // Standard: 75kg
        set(value) = prefs.edit().putFloat("weight", value).apply()

    var targetDistanceKm: Float
        get() = prefs.getFloat("target_distance", 5f) // Standard: 5km
        set(value) = prefs.edit().putFloat("target_distance", value).apply()

    /**
     * Berechnet die verbrannten Kalorien basierend auf Distanz und Gewicht.
     * Formel: kcal = Strecke (km) * Gewicht (kg) * Aktivitätsfaktor (ca. 0.9 für Laufen)
     */
    fun calculateCalories(distanceMeters: Float): Int {
        val km = distanceMeters / 1000f
        return (weight * km * 0.9f).toInt()
    }
}
