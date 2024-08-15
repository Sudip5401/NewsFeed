package com.example.newsfeed.presentation.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsfeed.data.dataSource.dto.Data
import com.example.newsfeed.databinding.FeedItemBinding

class NewsFeedAdapter(private val context: Context?, private val list: List<Data>) :
    RecyclerView.Adapter<NewsFeedAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsFeedAdapter.ViewHolder {
        return ViewHolder(
            FeedItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsFeedAdapter.ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(binding: FeedItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Data) {

        }
    }

}