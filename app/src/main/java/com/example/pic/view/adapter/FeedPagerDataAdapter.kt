package com.example.pic.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pic.databinding.FeedItemBinding
import com.example.pic.model.res.Feed
import com.squareup.picasso.Picasso
import loadImage


class FeedPagerDataAdapter(private val onClick: (Feed?, Boolean) -> Unit): PagingDataAdapter<Feed, FeedPagerDataAdapter.FeedViewHolder>(FeedDiffUtil()) {

    class FeedViewHolder(private val binding: FeedItemBinding): RecyclerView.ViewHolder(binding.root){
        var cardItem: CardView = binding.feedItemCard
        fun bindData(feed: Feed){
            loadImage(binding.imgFeedItem, feed.urls.regular)

        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        return FeedViewHolder(FeedItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.bindData(getItem(position)!!)
        holder.cardItem.setOnClickListener {
            onClick.invoke(getItem(position), false)
        }

        holder.cardItem.setOnLongClickListener {
            onClick.invoke(getItem(position), true)
            return@setOnLongClickListener true
        }
    }

    class FeedDiffUtil: DiffUtil.ItemCallback<Feed>(){
        override fun areItemsTheSame(oldItem: Feed, newItem: Feed): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Feed, newItem: Feed): Boolean {
            return areItemsTheSame(oldItem, newItem)
        }
    }
}