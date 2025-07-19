package com.swanky.sympai.data.repository

import androidx.lifecycle.LiveData
import com.swanky.sympai.data.api.GeminiService
import com.swanky.sympai.data.local.SymptomAnalysisDao
import com.swanky.sympai.data.model.SymptomAnalysis

class SymptomRepository(
    private val symptomAnalysisDao: SymptomAnalysisDao,
    private val geminiService: GeminiService
) {
    // Local database operations
    val allAnalyses: LiveData<List<SymptomAnalysis>> = symptomAnalysisDao.getAllAnalyses()
    
    suspend fun insertAnalysis(analysis: SymptomAnalysis): Long {
        return symptomAnalysisDao.insertAnalysis(analysis)
    }
    
    suspend fun getAnalysisById(id: Long): SymptomAnalysis? {
        return symptomAnalysisDao.getAnalysisById(id)
    }
    
    suspend fun deleteAnalysis(id: Long) {
        symptomAnalysisDao.deleteAnalysis(id)
    }
    
    // API operations
    suspend fun analyzeSymptoms(userInput: String): Result<SymptomAnalysis> {
        return geminiService.analyzeSymptoms(userInput)
    }
}