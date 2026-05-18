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
import com.schule.myfitnessTracker.ui.history.RunDetailsDialogFragment
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

// ─────────────────────────────────────────────────────────────────────────────
// ViewModel
// ─────────────────────────────────────────────────────────────────────────────

class DashboardViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = FitnessRepository(FitnessDatabase.getInstance(application))
    private val profileManager = com.schule.myfitnessTracker.util.ProfileManager(application)
    private val mockDataManager = com.schule.myfitnessTracker.util.MockDataManager(repository)

    val todayDistance: LiveData<Float>  = repository.todayDistance
    val todaySteps: LiveData<Int>       = repository.todaySteps
    val todayCalories: LiveData<Int>    = repository.todayCalories
    val lastRun: LiveData<Run?>         = repository.lastRun
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

    fun loadMockData() {
        viewModelScope.launch {
            mockDataManager.insertSimulationData()
            loadWeeklyStats()
        }
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

        setupChart()
        setupProfile()
        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        // Aktualisiert die Statistiken und das Diagramm jedes Mal, wenn das Dashboard sichtbar wird
        viewModel.loadWeeklyStats()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // RecyclerView entfernt, da wir nur noch das letzte Training zeigen

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
            .setNeutralButton("Demo-Daten laden") { _, _ ->
                viewModel.loadMockData()
            }
            .setNegativeButton("Abbrechen", null)
            .show()
    }

    // Die Funktion showElevationProfile kann komplett gelöscht werden, da wir die Höhenmeter nicht mehr wollen

    // ── Diagramm ──────────────────────────────────────────────────────────────

    private fun setupChart() {
        val isDarkMode = (resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK) == 
                         android.content.res.Configuration.UI_MODE_NIGHT_YES
        val textColor = if (isDarkMode) Color.WHITE else Color.parseColor("#666666")
        val gridColor = if (isDarkMode) Color.parseColor("#333333") else Color.parseColor("#EEEEEE")

        binding.barChart.apply {
            description.isEnabled = false
            setDrawGridBackground(false)
            setFitBars(true)
            animateY(1000)
            extraBottomOffset = 10f

            legend.apply {
                isEnabled = true
                verticalAlignment = com.github.mikephil.charting.components.Legend.LegendVerticalAlignment.TOP
                horizontalAlignment = com.github.mikephil.charting.components.Legend.LegendHorizontalAlignment.RIGHT
                orientation = com.github.mikephil.charting.components.Legend.LegendOrientation.HORIZONTAL
                setDrawInside(false)
                this.textColor = textColor
            }

            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                setDrawGridLines(false)
                granularity = 1f
                this.textColor = textColor
                setDrawAxisLine(true)
            }
            axisLeft.apply {
                setDrawGridLines(true)
                axisMinimum = 0f
                this.textColor = textColor
                this.gridColor = gridColor
                // Dynamische Einheit an der Achse
                valueFormatter = object : com.github.mikephil.charting.formatter.ValueFormatter() {
                    override fun getFormattedValue(value: Float): String {
                        return if (value >= 1f) "%.1f km".format(value) else "%.0f m".format(value * 1000)
                    }
                }
            }
            axisRight.isEnabled = false
        }
    }

    private fun updateChart(stats: List<DailyStats>) {
        if (stats.isEmpty()) {
            binding.barChart.clear()
            return
        }

        val isDarkMode = (resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK) == 
                         android.content.res.Configuration.UI_MODE_NIGHT_YES
        val valueColor = if (isDarkMode) Color.WHITE else Color.parseColor("#333333")

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

        val dataSet = BarDataSet(entries, "Distanz pro Tag").apply {
            color = Color.parseColor("#2196F3")
            valueTextColor = valueColor
            valueTextSize = 10f
            // Werte über den Balken formatieren (m oder km)
            valueFormatter = object : com.github.mikephil.charting.formatter.ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return if (value <= 0f) "" 
                           else if (value < 1f) "%.0f m".format(value * 1000)
                           else "%.1f km".format(value)
                }
            }
        }

        binding.barChart.data = BarData(dataSet)
        binding.barChart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        binding.barChart.invalidate()
    }

    // ── Beobachter ────────────────────────────────────────────────────────────

    private fun observeViewModel() {
        // Tages-Stats
        viewModel.todayDistance.observe(viewLifecycleOwner) { distM ->
            val meters = distM ?: 0f
            if (meters < 1000f) {
                binding.tvTodayDistance.text = "%.0f".format(meters)
                // Wir müssen auch das Label "km" unter der Zahl anpassen, 
                // falls wir eine TextView dafür haben.
                // In deinem Layout ist das TextView unter tv_today_distance:
                val parent = binding.tvTodayDistance.parent as? android.widget.LinearLayout
                (parent?.getChildAt(2) as? android.widget.TextView)?.text = "m"
            } else {
                val km = meters / 1000f
                binding.tvTodayDistance.text = "%.2f".format(km)
                val parent = binding.tvTodayDistance.parent as? android.widget.LinearLayout
                (parent?.getChildAt(2) as? android.widget.TextView)?.text = "km"
            }
        }

        viewModel.todaySteps.observe(viewLifecycleOwner) { steps ->
            val s = steps ?: 0
            binding.tvTodaySteps.text = "%,d".format(s)

            // Fortschrittsbalken (Ziel: 10.000 Schritte)
            val progress = (s.toFloat() / 10_000f * 100).toInt().coerceIn(0, 100)
            binding.progressSteps.progress = progress
            binding.tvStepsGoal.text = "$s / 10.000"
        }

        viewModel.avgSpeed.observe(viewLifecycleOwner) { speed ->
            binding.tvAvgSpeed.text = "%.1f".format(speed ?: 0f)
        }

        viewModel.todayCalories.observe(viewLifecycleOwner) { calories ->
            binding.tvTodayCalories.text = (calories ?: 0).toString()
        }

        // Letztes Training anzeigen
        viewModel.lastRun.observe(viewLifecycleOwner) { run ->
            if (run != null) {
                binding.lastRunLayout.root.visibility = View.VISIBLE
                binding.tvNoRuns.visibility = View.GONE
                
                // Manuelles Binden der Daten an das included Layout
                val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN)
                val timeFormat = SimpleDateFormat("HH:mm", Locale.GERMAN)
                
                binding.lastRunLayout.tvDate.text = dateFormat.format(Date(run.startTime))
                binding.lastRunLayout.tvTimeRange.text = "${timeFormat.format(Date(run.startTime))} - ${if (run.endTime > 0) timeFormat.format(Date(run.endTime)) else ""}"
                binding.lastRunLayout.tvDistance.text = run.distanceFormatted
                binding.lastRunLayout.tvDuration.text = run.durationFormatted
                binding.lastRunLayout.tvSpeed.text = "⌀ %.1f km/h".format(run.avgSpeedKmh)
                binding.lastRunLayout.tvSteps.text = "%,d Schritte".format(run.steps)
                binding.lastRunLayout.tvCalories.text = "${run.calories} kcal"
                binding.lastRunLayout.btnDelete.setOnClickListener { viewModel.deleteRun(run) }
                binding.lastRunLayout.root.setOnClickListener {
                    val detailsDialog = RunDetailsDialogFragment(run)
                    detailsDialog.show(childFragmentManager, "run_details")
                }
            } else {
                binding.lastRunLayout.root.visibility = View.GONE
                binding.tvNoRuns.visibility = View.VISIBLE
            }
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
