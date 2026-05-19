package com.schule.myfitnessTracker.util

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

/**
 * Hilfsklasse zum Speichern und Abrufen von Benutzerprofildaten.
 */
class ProfileManager(private val context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("user_profile", Context.MODE_PRIVATE)

    var currentUserId: Long
        get() = prefs.getLong("current_user_id", -1L)
        set(value) = prefs.edit().putLong("current_user_id", value).apply()

    var name: String
        get() = prefs.getString("name", "") ?: ""
        set(value) = prefs.edit().putString("name", value).apply()

    var weight: Float
        get() = prefs.getFloat("weight", 75f)
        set(value) = prefs.edit().putFloat("weight", value).apply()

    var targetDistanceKm: Float
        get() = prefs.getFloat("target_distance", 5f)
        set(value) = prefs.edit().putFloat("target_distance", value).apply()

    var isSimulationMode: Boolean
        get() = prefs.getBoolean("is_simulation_mode", false)
        set(value) = prefs.edit().putBoolean("is_simulation_mode", value).apply()

    var isDarkMode: Boolean
        get() = prefs.getBoolean("is_dark_mode", false)
        set(value) = prefs.edit().putBoolean("is_dark_mode", value).apply()

    /**
     * Kopiert ein Bild vom gegebenen URI in den internen App-Speicher.
     * Verhindert SecurityExceptions bei Neustart der App.
     */
    fun saveProfilePicture(uri: Uri): String? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val fileName = "profile_${currentUserId}_${System.currentTimeMillis()}.jpg"
            val file = File(context.filesDir, fileName)
            val outputStream = FileOutputStream(file)
            
            inputStream?.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }
            file.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun calculateCalories(distanceMeters: Float): Int {
        val km = distanceMeters / 1000f
        return (weight * km * 0.9f).toInt()
    }
}
