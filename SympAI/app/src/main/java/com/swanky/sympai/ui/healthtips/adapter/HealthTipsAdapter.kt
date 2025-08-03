package com.swanky.sympai.ui.healthtips.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.swanky.sympai.R
import com.swanky.sympai.data.model.HealthTip
import com.swanky.sympai.data.model.HealthTipCategory
import com.swanky.sympai.databinding.ItemHealthTipBinding

class HealthTipsAdapter : ListAdapter<HealthTip, HealthTipsAdapter.HealthTipViewHolder>(HealthTipDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HealthTipViewHolder {
        val binding = ItemHealthTipBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HealthTipViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HealthTipViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class HealthTipViewHolder(private val binding: ItemHealthTipBinding) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(healthTip: HealthTip) {
            binding.tipTitle.text = healthTip.title
            binding.tipDescription.text = healthTip.description
            
            // Kategori etiketini ayarla
            val categoryText = when (healthTip.category) {
                HealthTipCategory.DAILY_TIPS -> binding.root.context.getString(R.string.daily_tips)
                HealthTipCategory.COMMON_DISEASES -> binding.root.context.getString(R.string.common_diseases)
                HealthTipCategory.FIRST_AID -> binding.root.context.getString(R.string.first_aid)
                HealthTipCategory.HEALTHY_LIVING -> binding.root.context.getString(R.string.healthy_living)
            }
            binding.categoryChip.text = categoryText
            
            // Kategori rengini ayarla
            val chipBackgroundColor = when (healthTip.category) {
                HealthTipCategory.DAILY_TIPS -> R.color.primary_light
                HealthTipCategory.COMMON_DISEASES -> R.color.secondary
                HealthTipCategory.FIRST_AID -> R.color.urgency_emergency
                HealthTipCategory.HEALTHY_LIVING -> R.color.accent
            }
            
            val chipStrokeColor = when (healthTip.category) {
                HealthTipCategory.DAILY_TIPS -> R.color.primary
                HealthTipCategory.COMMON_DISEASES -> R.color.secondary_dark
                HealthTipCategory.FIRST_AID -> R.color.error
                HealthTipCategory.HEALTHY_LIVING -> R.color.primary
            }
            
            binding.categoryChip.chipBackgroundColor = ContextCompat.getColorStateList(
                binding.root.context,
                chipBackgroundColor
            )
            binding.categoryChip.chipStrokeColor = ContextCompat.getColorStateList(
                binding.root.context,
                chipStrokeColor
            )
            
            // İkon rengini kategori ile eşleştir
            val iconTint = when (healthTip.category) {
                HealthTipCategory.DAILY_TIPS -> R.color.primary
                HealthTipCategory.COMMON_DISEASES -> R.color.secondary_dark
                HealthTipCategory.FIRST_AID -> R.color.error
                HealthTipCategory.HEALTHY_LIVING -> R.color.primary
            }
            
            binding.tipIcon.backgroundTintList = ContextCompat.getColorStateList(
                binding.root.context,
                iconTint
            )
        }
    }

    class HealthTipDiffCallback : DiffUtil.ItemCallback<HealthTip>() {
        override fun areItemsTheSame(oldItem: HealthTip, newItem: HealthTip): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: HealthTip, newItem: HealthTip): Boolean {
            return oldItem == newItem
        }
    }
}