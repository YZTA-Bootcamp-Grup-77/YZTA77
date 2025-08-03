package com.swanky.sympai.ui.healthtips

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.swanky.sympai.data.model.HealthTip
import com.swanky.sympai.data.model.HealthTipCategory
import com.swanky.sympai.data.repository.HealthTipsRepository

class HealthTipsViewModel(private val repository: HealthTipsRepository) : ViewModel() {
    
    private val _healthTips = MutableLiveData<List<HealthTip>>()
    val healthTips: LiveData<List<HealthTip>> = _healthTips
    
    private val _selectedCategory = MutableLiveData<HealthTipCategory>()
    val selectedCategory: LiveData<HealthTipCategory> = _selectedCategory
    
    init {
        // Başlangıçta günlük ipuçlarını göster
        selectCategory(HealthTipCategory.DAILY_TIPS)
    }
    
    fun selectCategory(category: HealthTipCategory) {
        _selectedCategory.value = category
        _healthTips.value = repository.getHealthTipsByCategory(category)
    }
    
    fun getAllHealthTips() {
        _healthTips.value = repository.getAllHealthTips()
    }
    
    class HealthTipsViewModelFactory(
        private val repository: HealthTipsRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HealthTipsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return HealthTipsViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}