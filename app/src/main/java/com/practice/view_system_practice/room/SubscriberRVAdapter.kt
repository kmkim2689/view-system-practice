package com.practice.view_system_practice.room

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.practice.view_system_practice.R
import com.practice.view_system_practice.databinding.ItemSubscriberBinding
import com.practice.view_system_practice.room.entity.Subscriber

class SubscriberRVAdapter(
    private val list: List<Subscriber>,
    private val onItemClick: (Subscriber) -> Unit
)
    : RecyclerView.Adapter<SubscriberRVAdapter.SubscriberViewHolder>() {
    inner class SubscriberViewHolder(val binding: ItemSubscriberBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Subscriber) {
            binding.tvName.text = item.name
            binding.tvEmail.text = item.email
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubscriberViewHolder {
        val binding = DataBindingUtil.inflate<ItemSubscriberBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_subscriber,
            parent,
            false
        )

        return SubscriberViewHolder(binding).also { viewHolder ->
            binding.llItem.setOnClickListener {
                onItemClick(list[viewHolder.adapterPosition])
            }
        }

    }

    override fun onBindViewHolder(holder: SubscriberViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}