package com.otmanethedev.chatapp.ui.chat_activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.otmanethedev.chatapp.ui.chat_activity.MainViewModel.ChatAction
import com.otmanethedev.chatapp.ui.chat_activity.adapters.MessageRvAdapter
import com.otmanethedev.chatapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var binding: ActivityMainBinding

    private val messageRvAdapter by lazy { MessageRvAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpObservers()
        setUpUi()
    }

    private fun setUpUi() {
        binding.rvMessages.adapter = messageRvAdapter

        binding.btnSend.setOnClickListener {
            val message = binding.editText.text.toString().trim()
            if (message.isNotEmpty()) {
                viewModel.handleAction(ChatAction.SendMessage(message))
                binding.editText.text.clear()
            }
        }

        binding.userSwitch.setOnCheckedChangeListener { _, _ ->
            viewModel.handleAction(ChatAction.SwitchUser)
        }
    }

    private fun setUpObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.messages.collect {
                    messageRvAdapter.updateList(it)
                    binding.rvMessages.scrollToPosition(it.size - 1)
                }
            }
        }
    }
}