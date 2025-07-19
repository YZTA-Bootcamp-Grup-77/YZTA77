package com.swanky.sympai.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.swanky.sympai.data.model.SymptomAnalysis
import com.swanky.sympai.data.repository.SymptomRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: SymptomRepository) : ViewModel() {

    private val _analysisResult = MutableLiveData<SymptomAnalysis?>()
    val analysisResult: LiveData<SymptomAnalysis?> = _analysisResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun analyzeSymptoms(userInput: String) {
        _isLoading.value = true
        _error.value = null
        
        viewModelScope.launch {
            repository.analyzeSymptoms(userInput)
                .onSuccess { analysis ->
                    // Save to database
                    val id = repository.insertAnalysis(analysis)
                    _analysisResult.postValue(analysis.copy(id = id))
                    _isLoading.postValue(false)
                }
                .onFailure { exception ->
                    _error.postValue(exception.message ?: "An unknown error occurred")
                    _isLoading.postValue(false)
                }
        }
    }

    fun clearResult() {
        _analysisResult.value = null
    }

    class HomeViewModelFactory(private val repository: SymptomRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}