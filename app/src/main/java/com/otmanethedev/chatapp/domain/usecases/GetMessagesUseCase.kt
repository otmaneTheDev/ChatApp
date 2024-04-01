package com.otmanethedev.chatapp.domain.usecases

import com.otmanethedev.chatapp.data.Constants
import com.otmanethedev.chatapp.domain.repository.MessagesRepository
import com.otmanethedev.chatapp.ui.chat_activity.models.UiMessage
import com.otmanethedev.chatapp.utils.toCalendar
import com.otmanethedev.chatapp.utils.toLong
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

@Singleton
class GetMessagesUseCase @Inject constructor(
    private val repository: MessagesRepository
) {

    operator fun invoke(): Flow<List<UiMessage>> = repository.getMessages()
        .map {
            val groupedMessages = it.groupBy { it.date.toLong() }
            val messagesDomain = mutableListOf<UiMessage>()
            groupedMessages.map {
                messagesDomain.add(UiMessage.DateSection(it.key.toCalendar()))
                it.value.forEach {
                    if (it.sender == Constants.USER1) {
                        messagesDomain.add(UiMessage.OutputUiMessage(it.date, it.text))
                    } else {
                        messagesDomain.add(UiMessage.InputUiMessage(it.date, it.text))
                    }
                }
            }
            messagesDomain
        }
        .flowOn(Dispatchers.IO)
}
