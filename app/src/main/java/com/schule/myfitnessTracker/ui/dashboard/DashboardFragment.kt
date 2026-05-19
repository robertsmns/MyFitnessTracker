package com.schule.myfitnessTracker.ui.dashboard

import android.app.Application
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
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
import com.schule.myfitnessTracker.util.SecurityUtils
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

    private val modeTrigger = MutableLiveData<Pair<Long, Boolean>>()

    val todayDistance: LiveData<Float>  = modeTrigger.switchMap { (uid, mock) -> repository.getTodayDistance(uid, mock) }
    val todaySteps: LiveData<Int>       = modeTrigger.switchMap { (uid, mock) -> repository.getTodaySteps(uid, mock) }
    val todayCalories: LiveData<Int>    = modeTrigger.switchMap { (uid, mock) -> repository.getTodayCalories(uid, mock) }
    val lastRun: LiveData<Run?>         = modeTrigger.switchMap { (uid, mock) -> repository.getLastRun(uid, mock) }
    val avgSpeed: LiveData<Float>       = modeTrigger.switchMap { (uid, mock) -> repository.getAvgSpeed(uid, mock) }

    val userName   = MutableLiveData(profileManager.name)
    val userWeight = MutableLiveData(profileManager.weight)
    val userEmail  = MutableLiveData("")
    val userRole   = MutableLiveData("USER")
    val userProfilePic = MutableLiveData<String?>(null)

    private val _weeklyStats = MutableLiveData<List<DailyStats>>()
    val weeklyStats: LiveData<List<DailyStats>> = _weeklyStats

    init {
        loadUser()
    }

    private fun loadUser() {
        viewModelScope.launch {
            val user = repository.getUserById(profileManager.currentUserId)
            if (user != null) {
                userName.postValue(user.username)
                userWeight.postValue(user.weight)
                userEmail.postValue(user.email)
                userRole.postValue(user.role)
                userProfilePic.postValue(user.profilePicturePath)
                
                profileManager.name = user.username
                profileManager.weight = user.weight
                profileManager.targetDistanceKm = user.targetDistanceKm
            } else {
                // User existiert nicht mehr in DB (Migration/Wipe)
                logout()
            }
        }
    }

    fun refreshMode() {
        modeTrigger.value = profileManager.currentUserId to profileManager.isSimulationMode
        loadWeeklyStats()
        loadUser()
    }

    fun loadWeeklyStats() {
        viewModelScope.launch {
            val stats = repository.getWeeklyStats(profileManager.currentUserId, profileManager.isSimulationMode)
            _weeklyStats.postValue(stats)
        }
    }

    fun deleteRun(run: Run) {
        viewModelScope.launch {
            repository.deleteRun(run)
        }
    }

    fun updateProfile(name: String, weight: Float, target: Float, newPassword: String? = null, profilePic: String? = null) {
        viewModelScope.launch {
            val user = repository.getUserById(profileManager.currentUserId) ?: return@launch
            var updatedUser = user.copy(
                username = name,
                weight = weight,
                targetDistanceKm = target
            )
            
            if (!newPassword.isNullOrBlank()) {
                updatedUser = updatedUser.copy(passwordHash = SecurityUtils.hashPassword(newPassword))
            }
            
            if (profilePic != null) {
                updatedUser = updatedUser.copy(profilePicturePath = profilePic)
            }

            repository.updateUser(updatedUser)
            loadUser()
        }
    }

    fun logout() {
        profileManager.currentUserId = -1L
    }

    fun loadMockData() {
        viewModelScope.launch {
            mockDataManager.insertSimulationData(profileManager.currentUserId)
            loadWeeklyStats()
        }
    }

    suspend fun getRoutePoints(runId: Long) = repository.getRouteForRun(runId)
}

// ─────────────────────────────────────────────────────────────────────────────
// Fragment
// ─────────────────────────────────────────────────────────────────────────────

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DashboardViewModel by viewModels()

    private var selectedImageUri: Uri? = null
    private var currentDialogBinding: com.schule.myfitnessTracker.databinding.DialogEditProfileBinding? = null

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            selectedImageUri = it
            currentDialogBinding?.let { db ->
                db.ivProfilePicture.setImageURI(it)
                db.ivProfilePicture.setPadding(0, 0, 0, 0)
                db.ivProfilePicture.clearColorFilter()
                db.ivProfilePicture.imageTintList = null
            }
        }
    }

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
        viewModel.refreshMode()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupProfile() {
        binding.cardProfile.setOnClickListener {
            showEditProfileDialog()
        }

        binding.cardActiveMode.setOnClickListener {
            findNavController().navigate(R.id.mapFragment)
        }
    }


    private fun showEditProfileDialog() {
        val profileManager = com.schule.myfitnessTracker.util.ProfileManager(requireContext())
        val dialogBinding = com.schule.myfitnessTracker.databinding.DialogEditProfileBinding.inflate(layoutInflater)
        currentDialogBinding = dialogBinding
        selectedImageUri = null

        // Bestehende Werte setzen
        dialogBinding.etName.setText(viewModel.userName.value)
        dialogBinding.etWeight.setText(viewModel.userWeight.value.toString())
        dialogBinding.etTarget.setText(profileManager.targetDistanceKm.toString())
        dialogBinding.etEmail.setText(viewModel.userEmail.value)
        
        viewModel.userProfilePic.value?.let { path ->
            setProfilePicSafe(dialogBinding.ivProfilePicture, path)
        }

        dialogBinding.btnChangePicture.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        val dialog = com.google.android.material.dialog.MaterialAlertDialogBuilder(requireContext())
            .setView(dialogBinding.root)
            .setPositiveButton("Speichern") { _, _ ->
                val name = dialogBinding.etName.text.toString()
                val weight = dialogBinding.etWeight.text.toString().toFloatOrNull() ?: 75f
                val target = dialogBinding.etTarget.text.toString().toFloatOrNull() ?: 5f
                val newPass = dialogBinding.etNewPassword.text.toString()

                if (newPass.isNotBlank() && !SecurityUtils.isValidPassword(newPass)) {
                    Toast.makeText(requireContext(), "Neues Passwort ist zu unsicher!", Toast.LENGTH_LONG).show()
                    return@setPositiveButton
                }

                val savedPath = selectedImageUri?.let { profileManager.saveProfilePicture(it) }

                viewModel.updateProfile(
                    name = name,
                    weight = weight,
                    target = target,
                    newPassword = if (newPass.isBlank()) null else newPass,
                    profilePic = savedPath ?: viewModel.userProfilePic.value
                )
            }
            .setNegativeButton("Abbrechen", null)
            .setOnDismissListener {
                currentDialogBinding = null
            }
            .create()

        dialogBinding.btnLogout.setOnClickListener {
            dialog.dismiss()
            viewModel.logout()
            findNavController().navigate(R.id.loginFragment)
        }

        if (viewModel.userRole.value == "ADMIN" && profileManager.isSimulationMode) {
            dialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL, "Demo-Daten laden") { _, _ ->
                viewModel.loadMockData()
            }
        }
        
        dialog.show()
    }

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
            color = ContextCompat.getColor(requireContext(), R.color.primary)
            valueTextColor = valueColor
            valueTextSize = 10f
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

    private fun observeViewModel() {
        viewModel.todayDistance.observe(viewLifecycleOwner) { distM ->
            val meters = distM ?: 0f
            if (meters < 1000f) {
                binding.tvTodayDistance.text = "%.0f".format(meters)
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

        viewModel.lastRun.observe(viewLifecycleOwner) { run ->
            if (run != null) {
                binding.lastRunLayout.root.visibility = View.VISIBLE
                binding.tvNoRuns.visibility = View.GONE
                
                val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN)
                val timeFormat = SimpleDateFormat("HH:mm", Locale.GERMAN)
                
                binding.lastRunLayout.tvDate.text = dateFormat.format(Date(run.startTime))
                binding.lastRunLayout.tvTimeRange.text = "${timeFormat.format(Date(run.startTime))} - ${if (run.endTime > 0) timeFormat.format(Date(run.endTime)) else ""}"
                binding.lastRunLayout.tvDistance.text = run.distanceFormatted
                binding.lastRunLayout.tvDuration.text = run.durationFormatted
                binding.lastRunLayout.tvSpeed.text = "⌀ %.1f km/h".format(run.avgSpeedKmh)
                binding.lastRunLayout.tvSteps.text = "%,d Schritte".format(run.steps)
                binding.lastRunLayout.tvCalories.text = "${run.calories} kcal"
                binding.lastRunLayout.btnDelete.setOnClickListener { showDeleteConfirmDialog(run) }
                binding.lastRunLayout.root.setOnClickListener {
                    val detailsDialog = RunDetailsDialogFragment(run)
                    detailsDialog.show(childFragmentManager, "run_details")
                }
            } else {
                binding.lastRunLayout.root.visibility = View.GONE
                binding.tvNoRuns.visibility = View.VISIBLE
            }
        }

        viewModel.weeklyStats.observe(viewLifecycleOwner) { stats ->
            updateChart(stats)
        }

        viewModel.userName.observe(viewLifecycleOwner) { name ->
            val displayName = if (name.isNullOrEmpty()) getString(R.string.default_user_name) else name
            binding.tvProfileName.text = getString(R.string.hello_athlete, displayName)
        }

        viewModel.userWeight.observe(viewLifecycleOwner) { weight ->
            binding.tvProfileWeight.text = getString(R.string.weight_label, weight?.toString() ?: "75")
        }
        
        viewModel.userProfilePic.observe(viewLifecycleOwner) { picUri ->
            setProfilePicSafe(binding.ivProfileDashboard, picUri, true)
        }
    }

    private fun pm() = com.schule.myfitnessTracker.util.ProfileManager(requireContext())

    private fun hasLocationPermissions(): Boolean {
        return ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == android.content.pm.PackageManager.PERMISSION_GRANTED
    }

    /**
     * Setzt das Profilbild sicher und fängt SecurityExceptions ab.
     */
    private fun setProfilePicSafe(imageView: com.google.android.material.imageview.ShapeableImageView, path: String?, isDashboard: Boolean = false) {
        if (path.isNullOrEmpty()) {
            setDefaultProfilePic(imageView, isDashboard)
            return
        }

        try {
            if (path.startsWith("content://")) {
                val uri = Uri.parse(path)
                requireContext().contentResolver.openInputStream(uri)?.close()
                imageView.setImageURI(uri)
            } else {
                val file = java.io.File(path)
                if (file.exists()) {
                    val bitmap = android.graphics.BitmapFactory.decodeFile(path)
                    imageView.setImageBitmap(bitmap)
                } else {
                    setDefaultProfilePic(imageView, isDashboard)
                    return
                }
            }
            
            imageView.setPadding(0, 0, 0, 0)
            imageView.clearColorFilter()
            imageView.imageTintList = null 
        } catch (e: Exception) {
            e.printStackTrace()
            setDefaultProfilePic(imageView, isDashboard)
        }
    }

    private fun setDefaultProfilePic(imageView: com.google.android.material.imageview.ShapeableImageView, isDashboard: Boolean) {
        imageView.setImageResource(R.drawable.ic_profile_placeholder)
        if (isDashboard) {
            imageView.setPadding(0, 0, 0, 0)
        } else {
            imageView.setPadding(0, 0, 0, 0)
        }
        // Tint entfernen, damit das schwarz-weiße Placeholder Bild original bleibt
        imageView.clearColorFilter()
        imageView.imageTintList = null
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
}
