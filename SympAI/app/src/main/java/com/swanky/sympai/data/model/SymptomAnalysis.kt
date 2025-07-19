package com.swanky.sympai.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
@Entity(tableName = "symptom_analyses")
data class SymptomAnalysis(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userInput: String,
    val possibleConditions: List<PossibleCondition>,
    val specialistRecommendation: String,
    val generalAdvice: String,
    val timestamp: Date = Date()
) : Parcelable

@Parcelize
data class PossibleCondition(
    val name: String,
    val description: String,
    val probability: String, // "HIGH", "MEDIUM", "LOW"
    val urgency: String // "EMERGENCY", "URGENT", "ROUTINE", "SELF_CARE"
) : Parcelable