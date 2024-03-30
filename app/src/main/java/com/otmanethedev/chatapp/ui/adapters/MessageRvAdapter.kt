package com.otmanethedev.chatapp.ui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.otmanethedev.chatapp.databinding.ItemDateSectionBinding
import com.otmanethedev.chatapp.databinding.ItemInputMessageBinding
import com.otmanethedev.chatapp.databinding.ItemOutputMessageBinding
import android.icu.util.Calendar
import com.otmanethedev.chatapp.R
import com.otmanethedev.chatapp.ui.models.Message
import com.otmanethedev.chatapp.utils.dayAndHour
import com.otmanethedev.chatapp.utils.isToday
import com.otmanethedev.chatapp.utils.viewBinding

class MessageRvAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<Message> = emptyList()

    fun updateList(items: List<Message>) {
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
            is Message.DateSection -> (holder as DateSectionViewHolder).bind(item)
            is Message.InputMessage -> (holder as InputMessageViewHolder).bind(item)
            is Message.OutputMessage -> (holder as OutputMessageViewHolder).bind(item)
        }
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is Message.DateSection -> R.layout.item_date_section
            is Message.InputMessage -> R.layout.item_input_message
            is Message.OutputMessage -> R.layout.item_output_message
        }
    }

    inner class DateSectionViewHolder(private val binding: ItemDateSectionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Message.DateSection) {
            if (item.date.isToday()) {
                binding.txtDate.text = "Today"
            } else {
                binding.txtDate.text = item.date.dayAndHour()
            }
        }
    }

    inner class InputMessageViewHolder(private val binding: ItemInputMessageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Message.InputMessage) {
            binding.txtMessage.text = item.text
        }
    }

    inner class OutputMessageViewHolder(private val binding: ItemOutputMessageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Message.OutputMessage) {
            binding.txtMessage.text = item.text
        }
    }

    class MessageDiffCallback(private val oldList: List<Message>, private val newList: List<Message>) : DiffUtil.Callback() {

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
