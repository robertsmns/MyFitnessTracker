package com.schule.myfitnessTracker.ui.tracking

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.schule.myfitnessTracker.R
import com.schule.myfitnessTracker.data.model.RoutePoint
import com.schule.myfitnessTracker.databinding.FragmentMapBinding
import com.schule.myfitnessTracker.util.ProfileManager

/**
 * Karten-Fragment – zeigt die live gezeichnete Route auf Google Maps.
 *
 * Features:
 *  - Karte zoomt automatisch auf aktuellen Standort
 *  - Route wird als blaue Polylinie gezeichnet
 *  - Start-Marker (grün) und aktuelle Position (blau)
 *  - Start/Stop-Button zum Steuern des Trackings
 */
class MapFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TrackingViewModel by viewModels()
    private lateinit var profileManager: ProfileManager

    private var map: GoogleMap? = null
    private var polyline: Polyline? = null
    private var startMarker: Marker? = null
    private var currentMarker: Marker? = null

    private val routePoints = mutableListOf<RoutePoint>()
    private var isFirstLocationUpdate = true

    // ── Lifecycle ─────────────────────────────────────────────────────────────

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileManager = ProfileManager(requireContext())

        // Google Maps initialisieren
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        setupButtons()
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // ── Karte ─────────────────────────────────────────────────────────────────

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        // Karten-Stil
        map?.apply {
            mapType = GoogleMap.MAP_TYPE_NORMAL
            uiSettings.isZoomControlsEnabled = true
            uiSettings.isCompassEnabled      = true
            uiSettings.isMyLocationButtonEnabled = false

            // Versuche "Mein Standort"-Layer (benötigt Location-Permission)
            try {
                isMyLocationEnabled = true
            } catch (e: SecurityException) {
                e.printStackTrace()
            }
        }

        // Polylinie vorbereiten
        polyline = map?.addPolyline(
            PolylineOptions()
                .color(Color.parseColor("#2196F3"))  // Material Blue
                .width(12f)
                .geodesic(true)
        )

        // Bereits geladene Punkte zeichnen (falls Fragment neu erstellt)
        if (routePoints.isNotEmpty()) redrawRoute()
    }

    // ── UI & Buttons ──────────────────────────────────────────────────────────

    private fun setupButtons() {
        binding.btnStartStop.setOnClickListener {
            viewModel.toggleTracking()
        }
        binding.btnPause.setOnClickListener {
            viewModel.pauseTracking()
        }
        binding.btnCenter.setOnClickListener {
            centerOnCurrentLocation()
        }
    }

    private fun observeViewModel() {
        // Tracking-Status → Button-Beschriftung
        viewModel.isTracking.observe(viewLifecycleOwner) { tracking ->
            binding.btnStartStop.text = if (tracking) "STOP" else "START"
            binding.btnStartStop.setBackgroundColor(
                if (tracking) Color.parseColor("#F44336")   // Rot
                else Color.parseColor("#4CAF50")            // Grün
            )
            binding.btnPause.visibility = if (tracking) View.VISIBLE else View.GONE
            if (!tracking) {
                isFirstLocationUpdate = true
                routePoints.clear()
                redrawRoute()
            }
        }

        // Aktive Run-ID → Route aus DB laden
        viewModel.currentRunId.observe(viewLifecycleOwner) { runId ->
            runId ?: return@observe
            viewModel.getRoutePoints(runId).observe(viewLifecycleOwner) { points ->
                routePoints.clear()
                routePoints.addAll(points)
                redrawRoute()

                // Automatisch auf letzte Position zoomen
                if (points.isNotEmpty() && isFirstLocationUpdate) {
                    val last = points.last()
                    zoomToPosition(last.latitude, last.longitude)
                    isFirstLocationUpdate = false
                }
            }
        }

        // Live-Statistiken in Overlay anzeigen
        viewModel.distanceM.observe(viewLifecycleOwner) { meters ->
            val target = profileManager.targetDistanceKm * 1000f
            if (meters >= target && target > 0) {
                // Ziel erreicht! (Könnte man optisch hervorheben)
                binding.tvDistance.setTextColor(Color.parseColor("#4CAF50")) // Grün
            } else {
                binding.tvDistance.setTextColor(Color.WHITE)
            }
        }

        viewModel.distanceFormatted.observe(viewLifecycleOwner) { dist ->
            binding.tvDistance.text = dist
        }
        viewModel.timerFormatted.observe(viewLifecycleOwner) { time ->
            binding.tvTimer.text = time
        }
        viewModel.speedKmh.observe(viewLifecycleOwner) { speed ->
            binding.tvSpeed.text = "%.1f km/h".format(speed)
        }

        // Standort-Updates für initiale Zentrierung
        viewModel.lastLocation.observe(viewLifecycleOwner) { location ->
            if (location != null && isFirstLocationUpdate) {
                zoomToPosition(location.latitude, location.longitude)
                isFirstLocationUpdate = false
            }
        }
    }

    // ── Karten-Zeichnen ───────────────────────────────────────────────────────

    private fun redrawRoute() {
        if (routePoints.isEmpty()) {
            polyline?.points = emptyList()
            startMarker?.remove()
            currentMarker?.remove()
            return
        }

        // Polylinie
        val latLngs = routePoints.map { LatLng(it.latitude, it.longitude) }
        polyline?.points = latLngs

        // Start-Marker
        if (startMarker == null) {
            startMarker = map?.addMarker(
                MarkerOptions()
                    .position(latLngs.first())
                    .title("Start")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
            )
        }

        // Aktueller Positions-Marker
        currentMarker?.remove()
        currentMarker = map?.addMarker(
            MarkerOptions()
                .position(latLngs.last())
                .title("Aktuell")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
        )
    }

    private fun zoomToPosition(lat: Double, lng: Double) {
        map?.animateCamera(
            CameraUpdateFactory.newLatLngZoom(LatLng(lat, lng), 17f)
        )
    }

    private fun centerOnCurrentLocation() {
        // Erst versuchen wir es über die gezeichnete Route
        val lastPoint = routePoints.lastOrNull()
        if (lastPoint != null) {
            zoomToPosition(lastPoint.latitude, lastPoint.longitude)
            return
        }

        // Falls noch keine Route da ist, nehmen wir den letzten bekannten GPS-Standort
        viewModel.lastLocation.value?.let { location ->
            zoomToPosition(location.latitude, location.longitude)
        }
    }
}
