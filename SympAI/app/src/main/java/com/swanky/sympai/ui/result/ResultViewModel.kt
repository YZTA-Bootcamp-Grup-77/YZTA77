package com.swanky.sympai.ui.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.swanky.sympai.data.model.SymptomAnalysis
import com.swanky.sympai.data.repository.SymptomRepository
import kotlinx.coroutines.launch

class ResultViewModel(private val repository: SymptomRepository) : ViewModel() {

    private val _analysis = MutableLiveData<SymptomAnalysis?>()
    val analysis: LiveData<SymptomAnalysis?> = _analysis

    fun loadAnalysis(id: Long) {
        viewModelScope.launch {
            val result = repository.getAnalysisById(id)
            _analysis.postValue(result)
        }
    }

    fun setAnalysis(analysis: SymptomAnalysis) {
        _analysis.value = analysis
    }

    class ResultViewModelFactory(private val repository: SymptomRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ResultViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ResultViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}