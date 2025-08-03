package com.swanky.sympai.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HealthTip(
    val id: Int,
    val title: String,
    val description: String,
    val category: HealthTipCategory,
    val iconRes: Int? = null
) : Parcelable

enum class HealthTipCategory {
    DAILY_TIPS,
    COMMON_DISEASES,
    FIRST_AID,
    HEALTHY_LIVING
}