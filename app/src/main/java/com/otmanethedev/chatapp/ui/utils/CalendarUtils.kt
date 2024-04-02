package com.otmanethedev.chatapp.ui.utils

import android.icu.util.Calendar
import java.text.SimpleDateFormat
import java.util.Locale

fun Calendar.dayAndHour() = SimpleDateFormat("EEEE HH:mm", Locale.getDefault()).format(this.time)

fun Calendar.isToday(): Boolean {
    val today = Calendar.getInstance()
    return this.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
            this.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
            this.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)
}