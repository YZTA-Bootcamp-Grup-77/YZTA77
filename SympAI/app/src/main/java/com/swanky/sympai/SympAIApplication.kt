package com.swanky.sympai

import android.app.Application
import com.swanky.sympai.data.api.GeminiService
import com.swanky.sympai.data.local.AppDatabase
import com.swanky.sympai.data.repository.SymptomRepository

class SympAIApplication : Application() {
    
    // Lazy initialization of database
    private val database by lazy { AppDatabase.getDatabase(this) }
    
    // Lazy initialization of DAO
    private val symptomAnalysisDao by lazy { database.symptomAnalysisDao() }
    
    // Lazy initialization of API service
    private val geminiService by lazy { GeminiService(this) }
    
    // Repository instance
    val repository by lazy { SymptomRepository(symptomAnalysisDao, geminiService) }
}