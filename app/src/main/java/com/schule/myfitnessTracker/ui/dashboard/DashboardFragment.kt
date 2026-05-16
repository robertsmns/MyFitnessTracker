package com.schule.myfitnessTracker.ui.dashboard

import android.app.Application
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
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

    val todayDistance: LiveData<Float>  = repository.todayDistance
    val todaySteps: LiveData<Int>       = repository.todaySteps
    val allRuns: LiveData<List<Run>>    = repository.allRuns
    val avgSpeed: LiveData<Float>       = repository.avgSpeed

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
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // ── RecyclerView ──────────────────────────────────────────────────────────

    private fun setupRecyclerView() {
        runAdapter = RunHistoryAdapter { run -> viewModel.deleteRun(run) }
        binding.rvRunHistory.adapter = runAdapter
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
    }
}
