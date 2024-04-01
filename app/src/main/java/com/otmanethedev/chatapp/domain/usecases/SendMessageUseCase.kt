package com.otmanethedev.chatapp.domain.usecases

import com.otmanethedev.chatapp.data.local.entities.MessageEntity
import com.otmanethedev.chatapp.domain.repository.MessagesRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SendMessageUseCase @Inject constructor(
    private val repository: MessagesRepository
) {

    suspend operator fun invoke(message: MessageEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.sendMessage(message)
        }
    }
}