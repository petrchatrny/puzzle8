package com.github.petrchatrny.puzzle8.utils

import java.text.SimpleDateFormat
import java.util.*

fun getDate(dateTime: Date): String {
    return SimpleDateFormat("d. MMMM yyyy", Locale.forLanguageTag("cs")).format(dateTime)
}

fun getTime(dateTime: Date): String {
    return SimpleDateFormat("HH:mm", Locale.forLanguageTag("cs")).format(dateTime)
}

fun getDateTime(dateTime: Date): String {
    return getDate(dateTime) + " " + getTime(dateTime)
}