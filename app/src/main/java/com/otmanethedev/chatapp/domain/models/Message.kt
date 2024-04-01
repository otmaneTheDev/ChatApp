package com.otmanethedev.chatapp.domain.models

import android.icu.util.Calendar

class Message(
    val sender: String,
    val receiver: String,
    val text: String,
    val date: Calendar
)
