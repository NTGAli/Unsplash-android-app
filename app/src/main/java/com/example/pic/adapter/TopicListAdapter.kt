package com.example.pic.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pic.R
import com.example.pic.model.Topic

class TopicListAdapter(private val onClick: (Topic) -> Unit): ListAdapter<Topic, TopicListAdapter.TopicViewHolder>(DiffTopic()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TopicListAdapter.TopicViewHolder {
        return TopicViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.topic_item_layout, parent, false))
    }

    override fun onBindViewHolder(holder: TopicListAdapter.TopicViewHolder, position: Int) {
        holder.bindData(getItem(position))
        holder.layout.setOnClickListener {
            onClick.invoke(getItem(position))
        }
    }

    class TopicViewHolder(private val view: View): RecyclerView.ViewHolder(view){
        private var txtTopic: TextView = view.findViewById(R.id.txt_topic_name)
        var layout: RelativeLayout = view.findViewById(R.id.topic_item_layout)
        fun bindData(topic: Topic){
            txtTopic.text = topic.title
        }
    }

    class DiffTopic: DiffUtil.ItemCallback<Topic>(){
        override fun areItemsTheSame(oldItem: Topic, newItem: Topic): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Topic, newItem: Topic): Boolean {
            return areItemsTheSame(oldItem, newItem)
        }

    }
}