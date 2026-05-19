package com.schule.myfitnessTracker.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.schule.myfitnessTracker.R
import com.schule.myfitnessTracker.data.db.FitnessDatabase
import com.schule.myfitnessTracker.databinding.ActivityMainBinding
import com.schule.myfitnessTracker.util.ProfileManager
import kotlinx.coroutines.launch

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
        Manifest.permission.ACCESS_COARSE_LOCATION
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
                "GPS wird für das Tracking benötigt!",
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
        val profileManager = ProfileManager(this)
        if (profileManager.isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()
        requestPermissionsIfNeeded()

        // Überprüfen, ob der gespeicherte User noch in der Datenbank existiert (nach Migration/Wipe)
        lifecycleScope.launch {
            val userExists = profileManager.currentUserId != -1L && 
                             FitnessDatabase.getInstance(this@MainActivity).userDao().getUserById(profileManager.currentUserId) != null
            
            if (!userExists) {
                profileManager.currentUserId = -1L
                // Falls wir nicht im Login/Register sind, dahin navigieren
                val navController = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
                if (navController.currentDestination?.id != R.id.loginFragment && 
                    navController.currentDestination?.id != R.id.registerFragment) {
                    navController.navigate(R.id.loginFragment)
                }
            }
        }
    }


    // ── Navigation ────────────────────────────────────────────────────────────

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Bottom Navigation mit NavController verknüpfen
        binding.bottomNavigation.setupWithNavController(navController)

        // Navbar auf Login/Register ausblenden
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment, R.id.registerFragment -> {
                    binding.bottomNavigation.visibility = android.view.View.GONE
                }
                else -> {
                    binding.bottomNavigation.visibility = android.view.View.VISIBLE
                }
            }
        }
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
