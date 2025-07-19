package com.swanky.sympai.utils

import com.swanky.sympai.BuildConfig

object Constants {
    // Gemini API key from BuildConfig (loaded from local.properties)
    const val GEMINI_API_KEY = BuildConfig.GEMINI_API_KEY
    
    // Speech recognition request code
    const val SPEECH_REQUEST_CODE = 100
    
    // Navigation constants
    const val ARG_ANALYSIS_RESULT = "analysis_result"
}