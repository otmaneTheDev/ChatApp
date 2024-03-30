package com.otmanethedev.chatapp.ui.models

import android.icu.util.Calendar

sealed class Message(val date: Calendar) {
    class DateSection(date: Calendar) : Message(date)
    class InputMessage(date: Calendar, val text: String) : Message(date)
    class OutputMessage(date: Calendar, val text: String) : Message(date)
}
