package com.otmanethedev.chatapp.ui

import android.icu.util.Calendar
import androidx.lifecycle.ViewModel
import com.otmanethedev.chatapp.ui.models.Message
import com.otmanethedev.chatapp.utils.isSameDay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

enum class ChatUser {
    USER1, // OutputMessage
    USER2 // InputMessage
}

class MainViewModel : ViewModel() {

    private val _messages: MutableStateFlow<List<Message>> = MutableStateFlow(listOf())
    val messages: StateFlow<List<Message>> get() = _messages

    private var selectedUser = ChatUser.USER1

    sealed class ChatAction {
        data object SwitchUser : ChatAction()
        class SendMessage(val message: String) : ChatAction()
    }

    init {
        getCachedMessagesToDomain()
    }

    fun handleAction(action: ChatAction) {
        when (action) {
            is ChatAction.SendMessage -> handleActionSendMessage(action.message)
            ChatAction.SwitchUser -> handleActionSwitchUser()
        }
    }

    private fun handleActionSendMessage(text: String) {
        val currentTime = Calendar.getInstance()

        val currentMessages = _messages.value.toMutableList()
        val lastMessageDate = currentMessages.last().date

        if (currentMessages.isEmpty() || !lastMessageDate.isSameDay(currentTime)) {
            currentMessages.add(Message.DateSection(currentTime))
        }

        if (selectedUser == ChatUser.USER1) {
            currentMessages.add(Message.OutputMessage(currentTime, text))
        } else {
            currentMessages.add(Message.InputMessage(currentTime, text))
        }
        _messages.value = currentMessages
    }

    private fun handleActionSwitchUser() {
        selectedUser = if (selectedUser == ChatUser.USER1) ChatUser.USER2 else ChatUser.USER1
    }

    private fun getCachedMessagesToDomain() {
        val messagesDto = fetchCachedMessagesDto()
        val groupedMessages = messagesDto.groupBy { it.date }
        val messagesDomain = mutableListOf<Message>()
        groupedMessages.map {
            messagesDomain.add(Message.DateSection(it.key))
            it.value.forEach {
                if (it.senderId == ChatUser.USER1.name) {
                    messagesDomain.add(Message.OutputMessage(it.date, it.text))
                } else {
                    messagesDomain.add(Message.InputMessage(it.date, it.text))
                }
            }
        }
        _messages.value = messagesDomain
    }

    private fun fetchCachedMessagesDto(): List<MessageDto> {
        val randomDayCalendar = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_MONTH, -10)
        }

        val yesterdayCalendar = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_MONTH, -1)
        }

        return listOf(
            MessageDto(ChatUser.USER1.name, randomDayCalendar, "Hey, how's it going?"),
            MessageDto(ChatUser.USER2.name, randomDayCalendar, "Not too bad, just chilling. You?"),
            MessageDto(ChatUser.USER1.name, randomDayCalendar, "Same here, just got back from work."),
            MessageDto(ChatUser.USER2.name, randomDayCalendar, "Nice, any plans for the evening?"),
            MessageDto(ChatUser.USER1.name, randomDayCalendar, "Not really, probably just gonna watch some Netflix. You?"),
            MessageDto(ChatUser.USER2.name, randomDayCalendar, "Thinking of hitting the gym, need to burn off some steam."),
            MessageDto(ChatUser.USER1.name, randomDayCalendar, "Sounds like a plan. I've been meaning to get back into working out myself."),
            MessageDto(ChatUser.USER2.name, randomDayCalendar, "Yeah, it's tough to stay consistent sometimes."),
            MessageDto(ChatUser.USER1.name, randomDayCalendar, "Tell me about it. Life gets in the way."),
            MessageDto(ChatUser.USER2.name, randomDayCalendar, "Totally. So, any exciting news lately?"),
            MessageDto(ChatUser.USER1.name, yesterdayCalendar, "Not much, just the usual grind. How about you?"),
            MessageDto(ChatUser.USER2.name, yesterdayCalendar, "Actually, I got a promotion at work last week!"),
            MessageDto(ChatUser.USER1.name, yesterdayCalendar, "Congrats! That's awesome news."),
            MessageDto(ChatUser.USER2.name, yesterdayCalendar, "Thanks! Pretty stoked about it."),
            MessageDto(ChatUser.USER1.name, yesterdayCalendar, "You deserve it, man. Hard work pays off."),
            MessageDto(ChatUser.USER2.name, yesterdayCalendar, "Appreciate it. So, anything new on your end?"),
            MessageDto(ChatUser.USER1.name, yesterdayCalendar, "Not really, just trying to stay sane amidst the chaos."),
            MessageDto(ChatUser.USER2.name, yesterdayCalendar, "I feel you. Hang in there, buddy."),
            MessageDto(ChatUser.USER1.name, yesterdayCalendar, "Thanks, man. Always good catching up with you."),
            MessageDto(ChatUser.USER2.name, yesterdayCalendar, "Likewise. Take care, talk soon!")
        )
    }

    class MessageDto(val senderId: String, val date: Calendar, val text: String)
}