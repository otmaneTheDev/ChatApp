package com.otmanethedev.chatapp.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.otmanethedev.chatapp.ui.MainViewModel.ChatAction
import com.otmanethedev.chatapp.ui.adapters.MessageRvAdapter
import com.otmanethedev.chatapp.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

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

        binding.userSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.handleAction(ChatAction.SwitchUser)
        }
    }

    private fun setUpObservers() {
        lifecycleScope.launch {
            viewModel.messages.collect {
                messageRvAdapter.updateList(it)
                binding.rvMessages.scrollToPosition(it.size - 1)
            }
        }
    }
}