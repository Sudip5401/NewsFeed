package com.example.newsfeed.presentation.ui.common

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.newsfeed.R
import com.example.newsfeed.data.dataSource.dto.Data
import com.example.newsfeed.databinding.FeedItemBinding


class NewsFeedAdapter(
    private val context: Context?,
    private val list: MutableList<Data>,
    private val onItemClick: (data: Data) -> Unit
) :
    RecyclerView.Adapter<NewsFeedAdapter.NewsFeedViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsFeedViewHolder {
        return NewsFeedViewHolder(
            FeedItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsFeedViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class NewsFeedViewHolder(private val binding: FeedItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Data) {
            binding.apply {
                titleTv.text = data.title
                descTv.text = data.description
                context?.let {
                    Glide.with(it)
                        .load(data.image)
                        .apply(
                            RequestOptions()
                                .placeholder(R.drawable.news_placeholder)
                                .error(R.drawable.news_placeholder)
                        )
                        .into(binding.logoIv)
                }
                itemView.setOnClickListener {
                    onItemClick.invoke(data)
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(updatedList: MutableList<Data>) {
        this.list.addAll(updatedList)
        notifyDataSetChanged()
    }

}