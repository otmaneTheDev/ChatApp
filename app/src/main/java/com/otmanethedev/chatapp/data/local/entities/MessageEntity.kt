package com.otmanethedev.chatapp.data.local.entities

import android.icu.util.Calendar
import androidx.room.Entity
import com.otmanethedev.chatapp.domain.models.Message

@Entity(tableName = "messages", primaryKeys = ["date"])
data class MessageEntity(
    val sender: String,
    val receiver: String,
    val text: String,
    val date: Calendar
)

fun MessageEntity.toDomain() = Message(sender, receiver, text, date)
