package com.schule.myfitnessTracker.ui.history

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import com.schule.myfitnessTracker.R
import com.schule.myfitnessTracker.data.db.FitnessDatabase
import com.schule.myfitnessTracker.data.db.FitnessRepository
import com.schule.myfitnessTracker.data.model.Run
import com.schule.myfitnessTracker.databinding.FragmentHistoryBinding
import com.schule.myfitnessTracker.service.TrackingService
import com.schule.myfitnessTracker.ui.dashboard.RunHistoryAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class HistoryViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = FitnessRepository(FitnessDatabase.getInstance(application))
    private val profileManager = com.schule.myfitnessTracker.util.ProfileManager(application)
    
    private val modeTrigger = MutableLiveData<Pair<Long, Boolean>>()
    val allRuns: LiveData<List<Run>> = modeTrigger.switchMap { (uid, isMock) ->
        repository.getAllRuns(uid, isMock)
    }

    fun refreshMode() {
        modeTrigger.value = profileManager.currentUserId to profileManager.isSimulationMode
    }

    fun deleteRun(run: Run) {
        viewModelScope.launch {
            repository.deleteRun(run)
        }
    }

    suspend fun getRoutePoints(runId: Long) = repository.getRouteForRun(runId)
}

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HistoryViewModel by viewModels()
    private lateinit var runAdapter: RunHistoryAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        runAdapter = RunHistoryAdapter(
            onDeleteClick = { run -> showDeleteConfirmDialog(run) },
            onItemClick = { run -> showRunDetails(run) }
        )
        binding.rvRunHistory.adapter = runAdapter

        viewModel.allRuns.observe(viewLifecycleOwner) { runs ->
            runAdapter.submitList(runs)
            binding.tvNoRuns.visibility = if (runs.isEmpty()) View.VISIBLE else View.GONE
        }

        setupLiveStatus()
    }

    private fun setupLiveStatus() {
        // Puls-Animation für das Icon
        val anim = AlphaAnimation(0.2f, 0.8f).apply {
            duration = 1000
            repeatMode = Animation.REVERSE
            repeatCount = Animation.INFINITE
        }
        binding.viewPulse.startAnimation(anim)

        val timeFormat = SimpleDateFormat("HH:mm", Locale.GERMAN)

        TrackingService.isTracking.observe(viewLifecycleOwner) { tracking ->
            binding.cardLiveStatus.visibility = if (tracking) View.VISIBLE else View.GONE
        }

        TrackingService.activityType.observe(viewLifecycleOwner) { type ->
            val label = when (type) {
                "WALKING" -> "Gehen"
                "RUNNING" -> "Laufen"
                "BICYCLE" -> "Radfahren"
                "VEHICLE" -> "Fahrt"
                "STILL"   -> "Stillstand"
                else      -> "Aktiv"
            }
            binding.tvLiveActivity.text = "Gerade aktiv: $label"
            
            val icon = when (type) {
                "WALKING" -> R.drawable.ic_run
                "BICYCLE" -> R.drawable.ic_run // TODO: ic_bike
                "VEHICLE" -> R.drawable.ic_history // TODO: ic_car
                else -> R.drawable.ic_run
            }
            binding.ivLiveIcon.setImageResource(icon)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            while (true) {
                if (_binding != null) {
                    updateLiveDuration(timeFormat)
                }
                delay(10000L) // Alle 10 Sekunden aktualisieren
            }
        }
    }

    private fun updateLiveDuration(timeFormat: SimpleDateFormat) {
        val binding = _binding ?: return
        val start = TrackingService.currentStartTime.value ?: 0L
        if (start > 0) {
            val now = System.currentTimeMillis()
            val diffMs = now - start
            val diffMin = diffMs / (1000 * 60)
            
            val timeStr = timeFormat.format(Date(start))
            binding.tvLiveSince.text = "Hier seit $timeStr ($diffMin Min.)"
        }
    }

    private fun showDeleteConfirmDialog(run: Run) {
        com.google.android.material.dialog.MaterialAlertDialogBuilder(requireContext())
            .setTitle("Lauf löschen")
            .setMessage("Möchtest du diesen Lauf wirklich unwiderruflich löschen?")
            .setPositiveButton("Löschen") { _, _ ->
                viewModel.deleteRun(run)
            }
            .setNegativeButton("Abbrechen", null)
            .show()
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshMode()
    }

    private fun showRunDetails(run: Run) {
        val detailsDialog = RunDetailsDialogFragment(run)
        detailsDialog.show(childFragmentManager, "run_details")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
