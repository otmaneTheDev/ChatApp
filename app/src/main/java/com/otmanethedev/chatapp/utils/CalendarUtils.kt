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

fun Calendar.isSameDay(otherDate: Calendar): Boolean {
    return this.get(Calendar.YEAR) == otherDate.get(Calendar.YEAR) &&
            this.get(Calendar.DAY_OF_YEAR) == otherDate.get(Calendar.DAY_OF_YEAR)
}