package com.schule.myfitnessTracker.ui.history

import android.app.Application
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.PolylineOptions
import com.schule.myfitnessTracker.R
import com.schule.myfitnessTracker.data.db.FitnessDatabase
import com.schule.myfitnessTracker.data.db.FitnessRepository
import com.schule.myfitnessTracker.data.model.Run
import com.schule.myfitnessTracker.databinding.FragmentHistoryBinding
import com.schule.myfitnessTracker.ui.dashboard.RunHistoryAdapter
import com.schule.myfitnessTracker.util.GpxExporter
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class HistoryViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = FitnessRepository(FitnessDatabase.getInstance(application))
    val allRuns: LiveData<List<Run>> = repository.allRuns

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
            onDeleteClick = { run -> viewModel.deleteRun(run) },
            onItemClick = { run -> showRunDetails(run) }
        )
        binding.rvRunHistory.adapter = runAdapter

        viewModel.allRuns.observe(viewLifecycleOwner) { runs ->
            runAdapter.submitList(runs)
            binding.tvNoRuns.visibility = if (runs.isEmpty()) View.VISIBLE else View.GONE
        }
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
