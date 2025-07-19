package com.swanky.sympai.ui.history.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.swanky.sympai.data.model.SymptomAnalysis
import com.swanky.sympai.databinding.ItemHistoryBinding
import java.text.SimpleDateFormat
import java.util.Locale

class HistoryAdapter(
    private val onItemClick: (SymptomAnalysis) -> Unit,
    private val onDeleteClick: (SymptomAnalysis) -> Unit
) : ListAdapter<SymptomAnalysis, HistoryAdapter.HistoryViewHolder>(HistoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemHistoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HistoryViewHolder(binding, onItemClick, onDeleteClick)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class HistoryViewHolder(
        private val binding: ItemHistoryBinding,
        private val onItemClick: (SymptomAnalysis) -> Unit,
        private val onDeleteClick: (SymptomAnalysis) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(analysis: SymptomAnalysis) {
            // Format date
            val dateFormat = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
            val formattedDate = dateFormat.format(analysis.timestamp)
            
            // Set text
            binding.userInputText.text = analysis.userInput
            binding.dateText.text = formattedDate
            binding.specialistText.text = analysis.specialistRecommendation
            
            // Set click listeners
            binding.root.setOnClickListener {
                onItemClick(analysis)
            }
            
            binding.deleteButton.setOnClickListener {
                onDeleteClick(analysis)
            }
        }
    }

    class HistoryDiffCallback : DiffUtil.ItemCallback<SymptomAnalysis>() {
        override fun areItemsTheSame(oldItem: SymptomAnalysis, newItem: SymptomAnalysis): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SymptomAnalysis, newItem: SymptomAnalysis): Boolean {
            return oldItem == newItem
        }
    }
}