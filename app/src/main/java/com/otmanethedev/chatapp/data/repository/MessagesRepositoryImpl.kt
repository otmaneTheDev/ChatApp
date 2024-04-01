package com.otmanethedev.chatapp.data.repository

import com.otmanethedev.chatapp.data.local.entities.MessageEntity
import com.otmanethedev.chatapp.data.local.daos.MessagesDao
import com.otmanethedev.chatapp.data.local.entities.toDomain
import com.otmanethedev.chatapp.domain.models.Message
import com.otmanethedev.chatapp.domain.repository.MessagesRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MessagesRepositoryImpl @Inject constructor(
    private val messagesDao: MessagesDao
) : MessagesRepository {

    override fun getMessages(): Flow<List<Message>> {
        return messagesDao.getMessages().map { it.map { it.toDomain() } }
    }

    override suspend fun sendMessage(message: MessageEntity) {
        return messagesDao.insert(message)
    }
}