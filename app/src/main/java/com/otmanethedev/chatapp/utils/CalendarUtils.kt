package com.otmanethedev.chatapp.utils

import java.text.SimpleDateFormat
import android.icu.util.Calendar
import java.util.Locale

fun Calendar.dayAndHour() = SimpleDateFormat("EEEE HH:mm", Locale.getDefault()).format(this.time)

fun Calendar.isToday(): Boolean {
    val today = Calendar.getInstance()
    return this.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
            this.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
            this.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)
}

fun Calendar.toLong(): Long {
    val year = get(Calendar.YEAR)
    val month = get(Calendar.MONTH) + 1
    val day = get(Calendar.DAY_OF_MONTH)
    return year * 10000L + month * 100 + day
}

fun Long.toCalendar(): Calendar {
    val year = (this / 10000).toInt()
    val month = (this % 10000 / 100).toInt() - 1
    val day = (this % 100).toInt()
    return Calendar.getInstance().apply {
        set(year, month, day)
    }
}