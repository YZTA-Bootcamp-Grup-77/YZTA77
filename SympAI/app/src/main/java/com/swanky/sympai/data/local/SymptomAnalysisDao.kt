package com.swanky.sympai.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.swanky.sympai.data.model.SymptomAnalysis

@Dao
interface SymptomAnalysisDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnalysis(analysis: SymptomAnalysis): Long

    @Query("SELECT * FROM symptom_analyses ORDER BY timestamp DESC")
    fun getAllAnalyses(): LiveData<List<SymptomAnalysis>>

    @Query("SELECT * FROM symptom_analyses WHERE id = :id")
    suspend fun getAnalysisById(id: Long): SymptomAnalysis?

    @Query("DELETE FROM symptom_analyses WHERE id = :id")
    suspend fun deleteAnalysis(id: Long)
}