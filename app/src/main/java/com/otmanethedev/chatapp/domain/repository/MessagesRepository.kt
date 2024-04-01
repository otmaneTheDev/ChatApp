package com.otmanethedev.chatapp.domain.repository

import com.otmanethedev.chatapp.data.local.entities.MessageEntity
import com.otmanethedev.chatapp.domain.models.Message
import kotlinx.coroutines.flow.Flow

interface MessagesRepository {
    fun getMessages(): Flow<List<Message>>
    suspend fun sendMessage(message: MessageEntity)
}