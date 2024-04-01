package com.otmanethedev.chatapp.ui.chat_activity

import android.icu.util.Calendar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otmanethedev.chatapp.data.Constants.USER1
import com.otmanethedev.chatapp.data.Constants.USER2
import com.otmanethedev.chatapp.data.local.entities.MessageEntity
import com.otmanethedev.chatapp.domain.usecases.GetMessagesUseCase
import com.otmanethedev.chatapp.domain.usecases.SendMessageUseCase
import com.otmanethedev.chatapp.ui.chat_activity.models.UiMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getMessagesUseCase: GetMessagesUseCase,
    private val sendMessageUseCase: SendMessageUseCase
) : ViewModel() {

    private val _messages: MutableStateFlow<List<UiMessage>> = MutableStateFlow(listOf())
    val messages: StateFlow<List<UiMessage>> get() = _messages.asStateFlow()

    private var senderUserId: String = USER1
    private var receiverUserId: String = USER2

    sealed class ChatAction {
        data object SwitchUser : ChatAction()
        class SendMessage(val message: String) : ChatAction()
    }

    init {
        fetchMessages()
    }

    fun handleAction(action: ChatAction) {
        when (action) {
            is ChatAction.SendMessage -> handleActionSendMessage(action.message)
            ChatAction.SwitchUser -> handleActionSwitchUsers()
        }
    }

    private fun handleActionSendMessage(text: String) {
        viewModelScope.launch {
            sendMessageUseCase.invoke(MessageEntity(senderUserId, receiverUserId, text, Calendar.getInstance()))
        }
    }

    private fun fetchMessages() {
        viewModelScope.launch {
            getMessagesUseCase().collect { messages ->
                _messages.value = messages
            }
        }
    }

    private fun handleActionSwitchUsers() {
        if (senderUserId == USER1) {
            receiverUserId = USER1
            senderUserId = USER2
        } else {
            receiverUserId = USER2
            senderUserId = USER1
        }
    }
}