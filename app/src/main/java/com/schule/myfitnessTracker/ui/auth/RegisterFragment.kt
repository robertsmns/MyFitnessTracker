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
import com.schule.myfitnessTracker.databinding.FragmentRegisterBinding
import com.schule.myfitnessTracker.util.SecurityUtils
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var repository: FitnessRepository

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repository = FitnessRepository(FitnessDatabase.getInstance(requireContext()))

        binding.btnRegister.setOnClickListener {
            performRegistration()
        }

        binding.tvLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    private fun performRegistration() {
        val username = binding.etUsername.text.toString()
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val passwordRepeat = binding.etPasswordRepeat.text.toString()

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(requireContext(), "Bitte alle Felder ausfüllen", Toast.LENGTH_SHORT).show()
            return
        }

        if (password != passwordRepeat) {
            Toast.makeText(requireContext(), "Passwörter stimmen nicht überein", Toast.LENGTH_SHORT).show()
            return
        }

        if (!SecurityUtils.isValidPassword(password)) {
            Toast.makeText(requireContext(), "Passwort erfüllt nicht die Regeln (8 Zeichen, Zahl, Sonderzeichen)", Toast.LENGTH_LONG).show()
            return
        }

        lifecycleScope.launch {
            if (repository.getUserByEmail(email) != null) {
                Toast.makeText(requireContext(), "Email wird bereits verwendet", Toast.LENGTH_SHORT).show()
                return@launch
            }
            if (repository.getUserByUsername(username) != null) {
                Toast.makeText(requireContext(), "Username wird bereits verwendet", Toast.LENGTH_SHORT).show()
                return@launch
            }

            val newUser = User(
                username = username,
                email = email,
                passwordHash = SecurityUtils.hashPassword(password)
            )
            repository.insertUser(newUser)
            Toast.makeText(requireContext(), "Konto erstellt! Bitte einloggen", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
