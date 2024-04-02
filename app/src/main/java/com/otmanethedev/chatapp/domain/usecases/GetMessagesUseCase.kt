package com.otmanethedev.chatapp.domain.usecases

import com.otmanethedev.chatapp.data.Constants
import com.otmanethedev.chatapp.data.Constants.ELAPSED_TIME_BETWEEN_MESSAGES
import com.otmanethedev.chatapp.data.Constants.ONE
import com.otmanethedev.chatapp.domain.repository.MessagesRepository
import com.otmanethedev.chatapp.domain.utils.toCalendar
import com.otmanethedev.chatapp.domain.utils.toLong
import com.otmanethedev.chatapp.ui.chat_activity.models.UiMessage
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
            groupedMessages.forEach {
                messagesDomain.add(UiMessage.DateSection(it.key.toCalendar()))
                it.value.forEachIndexed { index, currentMessage ->
                    val previousMessage = it.value.getOrNull(index - ONE)

                    val hasTail = when {
                        previousMessage?.sender != currentMessage.sender -> true
                        previousMessage.date.timeInMillis < currentMessage.date.timeInMillis - ELAPSED_TIME_BETWEEN_MESSAGES -> true
                        index == it.value.size - ONE -> true
                        else -> false
                    }

                    if (currentMessage.sender == Constants.USER1) {
                        messagesDomain.add(UiMessage.OutputUiMessage(currentMessage.date, currentMessage.text, hasTail))
                    } else {
                        messagesDomain.add(UiMessage.InputUiMessage(currentMessage.date, currentMessage.text, hasTail))
                    }
                }
            }
            messagesDomain
        }.flowOn(Dispatchers.IO)


}
