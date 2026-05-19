package com.schule.myfitnessTracker.ui.options

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.schule.myfitnessTracker.data.db.FitnessDatabase
import com.schule.myfitnessTracker.data.db.FitnessRepository
import com.schule.myfitnessTracker.databinding.FragmentOptionsBinding
import com.schule.myfitnessTracker.util.ProfileManager
import kotlinx.coroutines.launch

class OptionsFragment : Fragment() {

    private var _binding: FragmentOptionsBinding? = null
    private val binding get() = _binding!!
    private lateinit var profileManager: ProfileManager
    private lateinit var repository: FitnessRepository

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentOptionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileManager = ProfileManager(requireContext())
        repository = FitnessRepository(FitnessDatabase.getInstance(requireContext()))

        // Simulation Mode Check
        lifecycleScope.launch {
            val user = repository.getUserById(profileManager.currentUserId)
            val isAdmin = user?.role == "ADMIN"
            
            binding.switchSimulation.isEnabled = isAdmin
            if (!isAdmin) {
                binding.switchSimulation.isChecked = false
                profileManager.isSimulationMode = false
                binding.tvSimulationHint.visibility = View.VISIBLE
            } else {
                binding.tvSimulationHint.visibility = View.GONE
            }
        }

        // Switches initialisieren
        binding.switchSimulation.isChecked = profileManager.isSimulationMode
        binding.switchDarkMode.isChecked = profileManager.isDarkMode

        binding.switchSimulation.setOnCheckedChangeListener { _, isChecked ->
            profileManager.isSimulationMode = isChecked
        }

        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            profileManager.isDarkMode = isChecked
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
