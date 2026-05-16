package com.schule.myfitnessTracker.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.schule.myfitnessTracker.data.model.Run
import com.schule.myfitnessTracker.databinding.ItemRunBinding
import java.text.SimpleDateFormat
import java.util.*

/**
 * Adapter für die Trainings-Historie in der RecyclerView.
 *
 * Zeigt für jeden Run:
 *  - Datum & Uhrzeit
 *  - Distanz und Dauer
 *  - Durchschnittsgeschwindigkeit
 *  - Schritte
 */
class RunHistoryAdapter(
    private val onDeleteClick: (Run) -> Unit,
    private val onItemClick: (Run) -> Unit
) : ListAdapter<Run, RunHistoryAdapter.RunViewHolder>(RunDiffCallback()) {

    private val dateFormat = SimpleDateFormat("dd.MM.yyyy  HH:mm", Locale.GERMAN)

    inner class RunViewHolder(private val binding: ItemRunBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(run: Run) {
            binding.apply {
                tvDate.text     = dateFormat.format(Date(run.startTime))
                tvDistance.text = run.distanceKm
                tvDuration.text = run.durationFormatted
                tvSpeed.text    = "⌀ %.1f km/h".format(run.avgSpeedKmh)
                tvSteps.text    = "%,d Schritte".format(run.steps)
                tvCalories.text = "${run.calories} kcal"

                btnDelete.setOnClickListener { onDeleteClick(run) }
                root.setOnClickListener { onItemClick(run) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RunViewHolder {
        val binding = ItemRunBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return RunViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RunViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class RunDiffCallback : DiffUtil.ItemCallback<Run>() {
    override fun areItemsTheSame(oldItem: Run, newItem: Run) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Run, newItem: Run) = oldItem == newItem
}
