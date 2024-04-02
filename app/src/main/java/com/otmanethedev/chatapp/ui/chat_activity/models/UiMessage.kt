package com.otmanethedev.chatapp.ui.chat_activity.models

import android.icu.util.Calendar

sealed class UiMessage(val date: Calendar) {
    class DateSection(date: Calendar) : UiMessage(date)
    class InputUiMessage(date: Calendar, val text: String, val hasTail: Boolean = false) : UiMessage(date)
    class OutputUiMessage(date: Calendar, val text: String, val hasTail: Boolean = false) : UiMessage(date)
}