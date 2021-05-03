package com.github.petrchatrny.puzzle8.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

class Converters {

    @TypeConverter
    fun toDate(timestamp: Long): Date {
        return Date(timestamp)
    }

    @TypeConverter
    fun toTimestamp(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun toList(value: String?): List<List<Int>?> {
        val listType: Type = object : TypeToken<List<List<Int>>?>() {}.type
        if (value == null || value == "" || value == "null") {
            return listOf()
        }
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<List<Int>>?): String? {
        return Gson().toJson(list)
    }
}