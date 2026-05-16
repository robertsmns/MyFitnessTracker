package com.schule.myfitnessTracker.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.schule.myfitnessTracker.R
import com.schule.myfitnessTracker.databinding.ActivityMainBinding

/**
 * Haupt-Activity – Container für alle Fragments.
 *
 * Verwaltet:
 *  - Navigation über Bottom Navigation Bar
 *  - Berechtigungsanfragen (GPS, Aktivitätserkennung, Benachrichtigungen)
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // ── Permissions ──────────────────────────────────────────────────────────

    private val requiredPermissions = mutableListOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACTIVITY_RECOGNITION
    ).apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            add(Manifest.permission.POST_NOTIFICATIONS)
        }
    }.toTypedArray()

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.values.all { it }
        if (!allGranted) {
            Toast.makeText(
                this,
                "GPS & Aktivitätserkennung werden für das Tracking benötigt!",
                Toast.LENGTH_LONG
            ).show()
        }
        // Hintergrund-GPS separat anfragen (Android 10+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            requestBackgroundLocation()
        }
    }

    private val backgroundLocationLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { /* wird benötigt für Tracking, wenn App minimiert */ }

    // ── Lifecycle ────────────────────────────────────────────────────────────

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()
        requestPermissionsIfNeeded()
    }

    // ── Navigation ────────────────────────────────────────────────────────────

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Bottom Navigation mit NavController verknüpfen
        binding.bottomNavigation.setupWithNavController(navController)
    }

    // ── Permissions ──────────────────────────────────────────────────────────

    private fun requestPermissionsIfNeeded() {
        val needsPermission = requiredPermissions.any {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }
        if (needsPermission) {
            permissionLauncher.launch(requiredPermissions)
        }
    }

    private fun requestBackgroundLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q &&
            ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            backgroundLocationLauncher.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        }
    }
}
