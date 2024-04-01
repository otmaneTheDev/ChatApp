package com.otmanethedev.chatapp.ui.chat_activity.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.otmanethedev.chatapp.databinding.ItemDateSectionBinding
import com.otmanethedev.chatapp.databinding.ItemInputMessageBinding
import com.otmanethedev.chatapp.databinding.ItemOutputMessageBinding
import com.otmanethedev.chatapp.R
import com.otmanethedev.chatapp.domain.models.Message
import com.otmanethedev.chatapp.ui.chat_activity.models.UiMessage
import com.otmanethedev.chatapp.utils.dayAndHour
import com.otmanethedev.chatapp.utils.isToday
import com.otmanethedev.chatapp.utils.viewBinding

class MessageRvAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<UiMessage> = emptyList()

    fun updateList(items: List<UiMessage>) {
        val diffCallback = MessageDiffCallback(this.items, items)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(this)

        this.items = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_date_section -> DateSectionViewHolder(parent.viewBinding(ItemDateSectionBinding::inflate))
            R.layout.item_input_message -> InputMessageViewHolder(parent.viewBinding(ItemInputMessageBinding::inflate))
            R.layout.item_output_message -> OutputMessageViewHolder(parent.viewBinding(ItemOutputMessageBinding::inflate))
            else -> throw IllegalStateException("Unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is UiMessage.DateSection -> (holder as DateSectionViewHolder).bind(item)
            is UiMessage.InputUiMessage -> (holder as InputMessageViewHolder).bind(item)
            is UiMessage.OutputUiMessage -> (holder as OutputMessageViewHolder).bind(item)
        }
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is UiMessage.DateSection -> R.layout.item_date_section
            is UiMessage.InputUiMessage -> R.layout.item_input_message
            is UiMessage.OutputUiMessage -> R.layout.item_output_message
        }
    }

    inner class DateSectionViewHolder(private val binding: ItemDateSectionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UiMessage.DateSection) {
            if (item.date.isToday()) {
                binding.txtDate.text = "Today"
            } else {
                binding.txtDate.text = item.date.dayAndHour()
            }
        }
    }

    inner class InputMessageViewHolder(private val binding: ItemInputMessageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UiMessage.InputUiMessage) {
            binding.txtMessage.text = item.text
        }
    }

    inner class OutputMessageViewHolder(private val binding: ItemOutputMessageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UiMessage.OutputUiMessage) {
            binding.txtMessage.text = item.text
        }
    }

    class MessageDiffCallback(private val oldList: List<UiMessage>, private val newList: List<UiMessage>) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].date == newList[newItemPosition].date
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}
