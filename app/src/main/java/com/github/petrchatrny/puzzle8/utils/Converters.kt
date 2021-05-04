package com.github.petrchatrny.puzzle8.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
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
    fun toList(value: String?): List<IntArray>? {
        if (value == null || value == "" || value == "null") {
            return null
        }

        val listType: Type = object : TypeToken<IntArray>() {}.type
        val list = JSONArray(value)
        val result = mutableListOf<IntArray>()
        for (i in 0 until list.length()) {
            result.add(Gson().fromJson(list[i].toString(), listType))
        }
        return result
    }

    @TypeConverter
    fun fromList(list: List<IntArray>?): String? {
        return Gson().toJson(list)
    }
}