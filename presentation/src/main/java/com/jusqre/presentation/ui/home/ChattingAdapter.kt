package com.jusqre.presentation.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jusqre.domain.model.ChattingItem
import com.jusqre.presentation.databinding.ItemChattingBinding

class ChattingAdapter(
    private val onClick: (ChattingItem) -> Unit,
    private val onLongClick: () -> Unit,
    private val editModeStatus: () -> Boolean,
    private val animation: Animation
) : ListAdapter<ChattingItem, ChattingAdapter.ViewHolder>(diffUtil) {
    class ViewHolder(
        private val binding: ItemChattingBinding,
        private val onClick: (ChattingItem) -> Unit,
        private val onLongClick: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ChattingItem) {
            binding.chattingItem = item
            binding.root.setOnClickListener {
                onClick(item)
            }
            binding.root.setOnLongClickListener {
                onLongClick()
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemChattingBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding, onClick, onLongClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (editModeStatus()) {
            holder.itemView.startAnimation(animation)
        } else {
            holder.itemView.clearAnimation()
        }
        holder.bind(getItem(position))
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<ChattingItem>() {
            override fun areItemsTheSame(oldItem: ChattingItem, newItem: ChattingItem): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: ChattingItem, newItem: ChattingItem): Boolean {
                return oldItem.chatId == newItem.chatId && oldItem.lastChat == newItem.lastChat
            }
        }
    }
}