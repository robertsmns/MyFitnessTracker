package com.schule.myfitnessTracker.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.schule.myfitnessTracker.R
import com.schule.myfitnessTracker.data.db.FitnessDatabase
import com.schule.myfitnessTracker.data.db.FitnessRepository
import com.schule.myfitnessTracker.data.model.User
import com.schule.myfitnessTracker.databinding.FragmentLoginBinding
import com.schule.myfitnessTracker.util.ProfileManager
import com.schule.myfitnessTracker.util.SecurityUtils
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var repository: FitnessRepository
    private lateinit var profileManager: ProfileManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val db = FitnessDatabase.getInstance(requireContext())
        repository = FitnessRepository(db)
        profileManager = ProfileManager(requireContext())

        // Falls schon eingeloggt, direkt zum Dashboard
        if (profileManager.currentUserId != -1L) {
            findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
            return
        }

        // Admin-User initial anlegen, falls DB leer
        lifecycleScope.launch {
            if (repository.getUserCount() == 0) {
                val admin = User(
                    username = "admin",
                    email = "admin@fitness.de",
                    passwordHash = SecurityUtils.hashPassword("Admin123!"),
                    role = "ADMIN"
                )
                repository.insertUser(admin)
            }
        }

        binding.btnLogin.setOnClickListener {
            performLogin()
        }

        binding.tvRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun performLogin() {
        val identifier = binding.etIdentifier.text.toString()
        val password = binding.etPassword.text.toString()

        if (identifier.isEmpty() || password.isEmpty()) {
            Toast.makeText(requireContext(), "Bitte alle Felder ausfüllen", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            val user = repository.getUserByIdentifier(identifier)
            if (user != null && user.passwordHash == SecurityUtils.hashPassword(password)) {
                // Login erfolgreich
                profileManager.currentUserId = user.id
                profileManager.name = user.username
                profileManager.weight = user.weight
                profileManager.targetDistanceKm = user.targetDistanceKm
                
                findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
            } else {
                Toast.makeText(requireContext(), "Email oder Passwort falsch", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
