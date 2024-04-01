package com.otmanethedev.chatapp.domain.utils

import android.icu.util.Calendar

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