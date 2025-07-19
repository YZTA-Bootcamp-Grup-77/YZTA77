package com.swanky.sympai.ui.result.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.swanky.sympai.R
import com.swanky.sympai.data.model.PossibleCondition
import com.swanky.sympai.databinding.ItemConditionBinding

class ConditionAdapter : ListAdapter<PossibleCondition, ConditionAdapter.ConditionViewHolder>(ConditionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConditionViewHolder {
        val binding = ItemConditionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ConditionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ConditionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ConditionViewHolder(private val binding: ItemConditionBinding) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(condition: PossibleCondition) {
            binding.conditionName.text = condition.name
            binding.conditionDescription.text = condition.description
            
            // Set probability chip
            val probabilityText = when (condition.probability) {
                "HIGH" -> binding.root.context.getString(R.string.probability_high)
                "MEDIUM" -> binding.root.context.getString(R.string.probability_medium)
                else -> binding.root.context.getString(R.string.probability_low)
            }
            binding.probabilityChip.text = probabilityText
            
            val probabilityColor = when (condition.probability) {
                "HIGH" -> R.color.probability_high
                "MEDIUM" -> R.color.probability_medium
                else -> R.color.probability_low
            }
            binding.probabilityChip.chipBackgroundColor = ContextCompat.getColorStateList(
                binding.root.context,
                probabilityColor
            )
            
            // Set probability chip content description for accessibility
            val probabilityDesc = when (condition.probability) {
                "HIGH" -> binding.root.context.getString(R.string.probability_high_desc)
                "MEDIUM" -> binding.root.context.getString(R.string.probability_medium_desc)
                else -> binding.root.context.getString(R.string.probability_low_desc)
            }
            binding.probabilityChip.contentDescription = probabilityDesc
            
            // Set urgency chip
            val urgencyText = when (condition.urgency) {
                "EMERGENCY" -> binding.root.context.getString(R.string.urgency_emergency)
                "URGENT" -> binding.root.context.getString(R.string.urgency_urgent)
                "ROUTINE" -> binding.root.context.getString(R.string.urgency_routine)
                else -> binding.root.context.getString(R.string.urgency_self_care)
            }
            binding.urgencyChip.text = urgencyText
            
            val urgencyColor = when (condition.urgency) {
                "EMERGENCY" -> R.color.urgency_emergency
                "URGENT" -> R.color.urgency_urgent
                "ROUTINE" -> R.color.urgency_routine
                else -> R.color.urgency_self_care
            }
            binding.urgencyChip.chipBackgroundColor = ContextCompat.getColorStateList(
                binding.root.context,
                urgencyColor
            )
            
            // Set urgency chip content description for accessibility
            val urgencyDesc = when (condition.urgency) {
                "EMERGENCY" -> binding.root.context.getString(R.string.urgency_emergency_desc)
                "URGENT" -> binding.root.context.getString(R.string.urgency_urgent_desc)
                "ROUTINE" -> binding.root.context.getString(R.string.urgency_routine_desc)
                else -> binding.root.context.getString(R.string.urgency_self_care_desc)
            }
            binding.urgencyChip.contentDescription = urgencyDesc
        }
    }

    class ConditionDiffCallback : DiffUtil.ItemCallback<PossibleCondition>() {
        override fun areItemsTheSame(oldItem: PossibleCondition, newItem: PossibleCondition): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: PossibleCondition, newItem: PossibleCondition): Boolean {
            return oldItem == newItem
        }
    }
}