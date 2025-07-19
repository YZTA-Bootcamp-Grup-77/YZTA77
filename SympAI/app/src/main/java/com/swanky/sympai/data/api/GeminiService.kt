package com.swanky.sympai.data.api

import android.content.Context
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.GenerateContentResponse
import com.google.gson.Gson
import com.swanky.sympai.data.model.PossibleCondition
import com.swanky.sympai.data.model.SymptomAnalysis
import com.swanky.sympai.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GeminiService(private val context: Context) {

    private val generativeModel by lazy {
        GenerativeModel(
            modelName = "gemini-1.5-pro",
            apiKey = Constants.GEMINI_API_KEY
        )
    }

    suspend fun analyzeSymptoms(userInput: String): Result<SymptomAnalysis> = withContext(Dispatchers.IO) {
        try {
            val prompt = buildPrompt(userInput)
            val response = generativeModel.generateContent(prompt)
            
            val jsonResponse = extractJsonFromResponse(response)
            val analysis = parseJsonToAnalysis(jsonResponse, userInput)
            
            Result.success(analysis)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun buildPrompt(userInput: String): String {
        return """
            You are a medical symptom analyzer. Analyze the following symptoms and provide a structured response in JSON format.
            
            User symptoms: $userInput
            
            Respond ONLY with a valid JSON object that follows this exact schema:
            {
              "possibleConditions": [
                {
                  "name": "string",
                  "description": "string",
                  "probability": "HIGH|MEDIUM|LOW",
                  "urgency": "EMERGENCY|URGENT|ROUTINE|SELF_CARE"
                }
              ],
              "specialistRecommendation": "string",
              "generalAdvice": "string"
            }
            
            Important guidelines:
            1. Include 2-4 possible conditions that match the symptoms
            2. For each condition, provide a brief description
            3. Assign a probability level (HIGH, MEDIUM, LOW)
            4. Assign an urgency level (EMERGENCY, URGENT, ROUTINE, SELF_CARE)
            5. Recommend appropriate medical specialists
            6. Provide general advice for managing symptoms
            7. ONLY return the JSON object, no other text
            8. Make sure the JSON is valid and properly formatted
            
            Remember: This is not medical advice. Always recommend seeking professional medical help for serious symptoms.
        """.trimIndent()
    }

    private fun extractJsonFromResponse(response: GenerateContentResponse): String {
        val text = response.text?.trim() ?: throw IllegalStateException("Empty response from Gemini")
        
        // Extract JSON if it's wrapped in code blocks
        return if (text.startsWith("```json") && text.endsWith("```")) {
            text.removePrefix("```json").removeSuffix("```").trim()
        } else if (text.startsWith("{") && text.endsWith("}")) {
            text
        } else {
            throw IllegalStateException("Response is not in expected JSON format")
        }
    }

    private fun parseJsonToAnalysis(jsonString: String, userInput: String): SymptomAnalysis {
        val gson = Gson()
        val response = gson.fromJson(jsonString, GeminiResponse::class.java)
        
        return SymptomAnalysis(
            userInput = userInput,
            possibleConditions = response.possibleConditions,
            specialistRecommendation = response.specialistRecommendation,
            generalAdvice = response.generalAdvice
        )
    }

    data class GeminiResponse(
        val possibleConditions: List<PossibleCondition>,
        val specialistRecommendation: String,
        val generalAdvice: String
    )
}