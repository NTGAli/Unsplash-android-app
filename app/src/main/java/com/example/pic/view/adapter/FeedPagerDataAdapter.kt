package com.example.pic.view.adapter

import android.annotation.SuppressLint
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pic.databinding.FeedItemBinding
import com.example.pic.model.res.Feed


class FeedPagerDataAdapter(private val onClick: (Feed?, Boolean) -> Unit): PagingDataAdapter<Feed, FeedPagerDataAdapter.FeedViewHolder>(FeedDiffUtil()) {

    private var isLongClick: Boolean = false

    class FeedViewHolder(private val binding: FeedItemBinding): RecyclerView.ViewHolder(binding.root){
        var cardItem: ConstraintLayout = binding.feedItemCard
        fun bindData(feed: Feed){
            binding.url = feed.urls.regular

        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        return FeedViewHolder(FeedItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.bindData(getItem(position)!!)
        holder.cardItem.setOnClickListener {
            onClick.invoke(getItem(position), false)
//            isLongClick = false
        }

        holder.cardItem.setOnLongClickListener {
//            isLongClick = true
            onClick.invoke(getItem(position), true)
            return@setOnLongClickListener true
        }


        holder.cardItem.setOnTouchListener { view, motionEvent ->

//            if (motionEvent.action == MotionEvent.ACTION_UP){
//                Log.d("Action", "UP")
//                return@setOnTouchListener true
//            }else if (motionEvent.action == MotionEvent.ACTION_DOWN){
//                if (isLongClick){
//                    Log.d("Action", "ON LONG")
//                    onClick.invoke(getItem(position), true, false)
//                }else{
//                    onClick.invoke(getItem(position), false, false)
//                }
//
//            }else if (motionEvent.action == MotionEvent.ACTION_MOVE){
//                Log.d("Action", "MOVE>")
//            }

            if (motionEvent.action == MotionEvent.ACTION_MOVE){
                Log.d("Action1", "MOVE>")
            }

            return@setOnTouchListener false
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