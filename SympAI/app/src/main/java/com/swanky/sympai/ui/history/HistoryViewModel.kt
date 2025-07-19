package com.swanky.sympai.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.swanky.sympai.data.model.SymptomAnalysis
import com.swanky.sympai.data.repository.SymptomRepository
import kotlinx.coroutines.launch

class HistoryViewModel(private val repository: SymptomRepository) : ViewModel() {

    val allAnalyses: LiveData<List<SymptomAnalysis>> = repository.allAnalyses

    fun deleteAnalysis(id: Long) {
        viewModelScope.launch {
            repository.deleteAnalysis(id)
        }
    }

    class HistoryViewModelFactory(private val repository: SymptomRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return HistoryViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}