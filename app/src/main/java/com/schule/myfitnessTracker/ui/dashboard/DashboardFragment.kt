package com.schule.myfitnessTracker.ui.dashboard

import android.app.Application
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.schule.myfitnessTracker.R
import com.schule.myfitnessTracker.data.db.DailyStats
import com.schule.myfitnessTracker.data.db.FitnessDatabase
import com.schule.myfitnessTracker.data.db.FitnessRepository
import com.schule.myfitnessTracker.data.model.Run
import com.schule.myfitnessTracker.databinding.FragmentDashboardBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

// ─────────────────────────────────────────────────────────────────────────────
// ViewModel
// ─────────────────────────────────────────────────────────────────────────────

class DashboardViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = FitnessRepository(FitnessDatabase.getInstance(application))
    private val profileManager = com.schule.myfitnessTracker.util.ProfileManager(application)

    val todayDistance: LiveData<Float>  = repository.todayDistance
    val todaySteps: LiveData<Int>       = repository.todaySteps
    val allRuns: LiveData<List<Run>>    = repository.allRuns
    val avgSpeed: LiveData<Float>       = repository.avgSpeed

    val userName   = androidx.lifecycle.MutableLiveData(profileManager.name)
    val userWeight = androidx.lifecycle.MutableLiveData(profileManager.weight)

    // Wöchentliche Statistiken (für Balkendiagramm)
    private val _weeklyStats = androidx.lifecycle.MutableLiveData<List<DailyStats>>()
    val weeklyStats: LiveData<List<DailyStats>> = _weeklyStats

    init {
        loadWeeklyStats()
    }

    fun loadWeeklyStats() {
        viewModelScope.launch {
            val stats = repository.getWeeklyStats()
            _weeklyStats.postValue(stats)
        }
    }

    fun deleteRun(run: Run) {
        viewModelScope.launch {
            repository.deleteRun(run)
        }
    }

    fun updateProfile(name: String, weight: Float) {
        profileManager.name = name
        profileManager.weight = weight
        userName.value = name
        userWeight.value = weight
    }

    suspend fun getRoutePoints(runId: Long) = repository.getRouteForRun(runId)
}

// ─────────────────────────────────────────────────────────────────────────────
// Fragment
// ─────────────────────────────────────────────────────────────────────────────

/**
 * Dashboard – Tages- und Wochenübersicht.
 *
 * Zeigt:
 *  - Heutige Distanz, Schritte, Kalorien
 *  - Wöchentliches Balkendiagramm (MPAndroidChart)
 *  - Liste der letzten Trainingseinheiten
 */
class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DashboardViewModel by viewModels()

    private lateinit var runAdapter: RunHistoryAdapter

    // ── Lifecycle ─────────────────────────────────────────────────────────────

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupChart()
        setupProfile()
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // ── RecyclerView ──────────────────────────────────────────────────────────

    private fun setupRecyclerView() {
        runAdapter = RunHistoryAdapter(
            onDeleteClick = { run -> viewModel.deleteRun(run) },
            onItemClick = { run -> showElevationProfile(run) }
        )
        binding.rvRunHistory.adapter = runAdapter
    }

    private fun setupProfile() {
        binding.cardProfile.setOnClickListener {
            showEditProfileDialog()
        }
    }

    private fun showEditProfileDialog() {
        val profileManager = com.schule.myfitnessTracker.util.ProfileManager(requireContext())
        val layout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(60, 20, 60, 20)
        }

        val nameInput = EditText(context).apply {
            hint = "Name"
            setText(viewModel.userName.value)
        }
        val weightInput = EditText(context).apply {
            hint = "Gewicht (kg)"
            inputType = android.text.InputType.TYPE_CLASS_NUMBER or android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL
            setText(viewModel.userWeight.value.toString())
        }
        val targetInput = EditText(context).apply {
            hint = "Ziel-Distanz (km)"
            inputType = android.text.InputType.TYPE_CLASS_NUMBER or android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL
            setText(profileManager.targetDistanceKm.toString())
        }

        layout.addView(nameInput)
        layout.addView(weightInput)
        layout.addView(targetInput)

        AlertDialog.Builder(requireContext())
            .setTitle("Profil & Ziele")
            .setView(layout)
            .setPositiveButton("Speichern") { _, _ ->
                val name = nameInput.text.toString()
                val weight = weightInput.text.toString().toFloatOrNull() ?: 75f
                val target = targetInput.text.toString().toFloatOrNull() ?: 5f
                
                viewModel.updateProfile(name, weight)
                profileManager.targetDistanceKm = target
            }
            .setNegativeButton("Abbrechen", null)
            .show()
    }

    private fun showElevationProfile(run: Run) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_elevation_profile, null)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        val chart = dialogView.findViewById<com.github.mikephil.charting.charts.LineChart>(R.id.elevationChart)
        val infoText = dialogView.findViewById<android.widget.TextView>(R.id.tvElevationInfo)
        val btnClose = dialogView.findViewById<View>(R.id.btnClose)

        infoText.text = "Gesamtanstieg: %.1f m".format(run.elevationGain)
        btnClose.setOnClickListener { dialog.dismiss() }

        // Punkte laden und Diagramm füllen
        lifecycleScope.launch {
            val points = viewModel.getRoutePoints(run.id)
            if (points.isNotEmpty()) {
                val entries = points.mapIndexed { i, p -> Entry(i.toFloat(), p.altitude.toFloat()) }
                val dataSet = LineDataSet(entries, "Höhe (m)").apply {
                    color = Color.parseColor("#FF9800")
                    setDrawCircles(false)
                    setDrawValues(false)
                    setDrawFilled(true)
                    fillColor = Color.parseColor("#FF9800")
                    fillAlpha = 50
                    mode = LineDataSet.Mode.CUBIC_BEZIER
                }
                chart.data = LineData(dataSet)
                chart.description.isEnabled = false
                chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
                chart.axisRight.isEnabled = false
                chart.invalidate()
            }
        }

        dialog.show()
    }

    // ── Diagramm ──────────────────────────────────────────────────────────────

    private fun setupChart() {
        binding.barChart.apply {
            description.isEnabled = false
            legend.isEnabled = false
            setDrawGridBackground(false)
            setFitBars(true)
            animateY(800)

            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                setDrawGridLines(false)
                granularity = 1f
                textColor = Color.parseColor("#666666")
            }
            axisLeft.apply {
                setDrawGridLines(true)
                axisMinimum = 0f
                textColor = Color.parseColor("#666666")
            }
            axisRight.isEnabled = false
        }
    }

    private fun updateChart(stats: List<DailyStats>) {
        if (stats.isEmpty()) return

        // Letzte 7 Tage mit Standardwert 0 auffüllen
        val dayFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val labelFormat = SimpleDateFormat("EEE", Locale.GERMAN)
        val calendar = Calendar.getInstance()
        val filledStats = (6 downTo 0).map { daysAgo ->
            val cal = calendar.clone() as Calendar
            cal.add(Calendar.DAY_OF_YEAR, -daysAgo)
            val key = dayFormat.format(cal.time)
            val label = labelFormat.format(cal.time)
            val dist = stats.find { it.day == key }?.distanceKm ?: 0f
            label to dist
        }

        val entries = filledStats.mapIndexed { i, (_, dist) ->
            BarEntry(i.toFloat(), dist)
        }
        val labels = filledStats.map { it.first }

        val dataSet = BarDataSet(entries, "km").apply {
            color = Color.parseColor("#2196F3")
            valueTextColor = Color.parseColor("#333333")
            valueTextSize = 10f
        }

        binding.barChart.data = BarData(dataSet)
        binding.barChart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        binding.barChart.invalidate()
    }

    // ── Beobachter ────────────────────────────────────────────────────────────

    private fun observeViewModel() {
        // Tages-Stats
        viewModel.todayDistance.observe(viewLifecycleOwner) { distM ->
            val km = distM / 1000f
            binding.tvTodayDistance.text = "%.2f".format(km)
        }

        viewModel.todaySteps.observe(viewLifecycleOwner) { steps ->
            binding.tvTodaySteps.text = "%,d".format(steps)

            // Fortschrittsbalken (Ziel: 10.000 Schritte)
            val progress = (steps.toFloat() / 10_000f * 100).toInt().coerceIn(0, 100)
            binding.progressSteps.progress = progress
            binding.tvStepsGoal.text = "$steps / 10.000"
        }

        viewModel.avgSpeed.observe(viewLifecycleOwner) { speed ->
            binding.tvAvgSpeed.text = "%.1f".format(speed)
        }

        // Run-Liste
        viewModel.allRuns.observe(viewLifecycleOwner) { runs ->
            runAdapter.submitList(runs)
            binding.tvNoRuns.visibility = if (runs.isEmpty()) View.VISIBLE else View.GONE

            // Gesamt-Distanz
            val totalKm = runs.sumOf { it.distanceMeters.toDouble() } / 1000.0
            binding.tvTotalKm.text = "%.1f km total".format(totalKm)
        }

        // Wöchentliches Diagramm
        viewModel.weeklyStats.observe(viewLifecycleOwner) { stats ->
            updateChart(stats)
        }

        // Profil
        viewModel.userName.observe(viewLifecycleOwner) { name ->
            binding.tvProfileName.text = if (name.isNullOrEmpty()) "Hallo Sportler!" else "Hallo $name!"
        }
        viewModel.userWeight.observe(viewLifecycleOwner) { weight ->
            binding.tvProfileWeight.text = "$weight kg"
        }
    }
}
