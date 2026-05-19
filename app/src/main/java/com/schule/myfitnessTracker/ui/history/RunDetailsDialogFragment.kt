package com.schule.myfitnessTracker.ui.history

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.PolylineOptions
import com.schule.myfitnessTracker.R
import com.schule.myfitnessTracker.data.model.Run
import com.schule.myfitnessTracker.databinding.DialogRunDetailsBinding
import com.schule.myfitnessTracker.util.GpxExporter
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class RunDetailsDialogFragment(
    private val run: Run
) : DialogFragment() {

    private var _binding: DialogRunDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HistoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Nutzt das System-Theme (DayNight) statt festem Light Mode
        setStyle(STYLE_NORMAL, android.R.style.Theme_Material_Dialog_MinWidth)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DialogRunDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Karte initial ausblenden gegen Dark-Mode-Blitzen
        binding.detailMap.alpha = 0f

        val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.GERMAN)
        
        val modeLabel = if (run.trackingMode == "PASSIVE") "Alltag: " else "Sport: "
        val typeLabel = when (run.activityType) {
            "WALKING" -> "Gehen"
            "RUNNING" -> "Laufen"
            "BICYCLE" -> "Radfahren"
            "VEHICLE" -> "Fahrt"
            "STILL"   -> "Stillstand"
            else      -> "Aktivität"
        }
        
        binding.tvTitle.text = "$modeLabel$typeLabel vom ${dateFormat.format(Date(run.startTime))}"

        binding.btnClose.setOnClickListener { dismiss() }

        // Karte laden
        val mapFragment = childFragmentManager.findFragmentById(R.id.detailMap) as? SupportMapFragment
            ?: SupportMapFragment.newInstance().also {
                childFragmentManager.beginTransaction().replace(R.id.detailMap, it).commit()
            }

        lifecycleScope.launch {
            val points = viewModel.getRoutePoints(run.id)
            
            binding.btnShareGpx.setOnClickListener {
                GpxExporter.shareGpx(requireContext(), run, points)
            }

            mapFragment.getMapAsync { googleMap ->
                // Dark Mode für Google Maps in Details
                val isDarkMode = (resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK) == 
                                 android.content.res.Configuration.UI_MODE_NIGHT_YES
                if (isDarkMode) {
                    googleMap.setMapStyle(com.google.android.gms.maps.model.MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.map_style_dark))
                }

                // Einblenden nach Styling
                binding.detailMap.animate().alpha(1f).setDuration(400).start()

                if (points.isNotEmpty()) {
                    val latLngs = points.map { LatLng(it.latitude, it.longitude) }
                    
                    googleMap.addPolyline(PolylineOptions()
                        .addAll(latLngs)
                        .color(Color.parseColor("#2196F3"))
                        .width(10f))

                    val boundsBuilder = LatLngBounds.Builder()
                    latLngs.forEach { boundsBuilder.include(it) }
                    val bounds = boundsBuilder.build()
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100))
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
