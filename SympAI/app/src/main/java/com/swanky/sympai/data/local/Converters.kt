package com.swanky.sympai.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.swanky.sympai.data.model.PossibleCondition
import java.util.Date

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromPossibleConditionsList(value: List<PossibleCondition>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toPossibleConditionsList(value: String): List<PossibleCondition> {
        val listType = object : TypeToken<List<PossibleCondition>>() {}.type
        return gson.fromJson(value, listType)
    }
}