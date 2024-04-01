package com.otmanethedev.chatapp.data.local.utils

import android.icu.util.Calendar
import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromCalendar(calendar: Calendar): Long {
        return calendar.timeInMillis
    }

    @TypeConverter
    fun toCalendar(timeInMillis: Long): Calendar {
        return Calendar.getInstance().apply { this.timeInMillis = timeInMillis }
    }
}