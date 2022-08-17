package com.example.pic.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pic.R
import com.example.pic.model.res.UnsplashUser
import com.example.pic.util.loadImage
import de.hdodenhof.circleimageview.CircleImageView


class UserListAdapter (private val onClick: (UnsplashUser) -> Unit): ListAdapter<UnsplashUser, UserListAdapter.UserViewHolder>(UserDiffUtil()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserViewHolder {
        return UserViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bindData(getItem(position))
        holder.layout.setOnClickListener {
            onClick.invoke(getItem(position))
        }
    }

    class UserViewHolder(val view:View): RecyclerView.ViewHolder(view){
        private val profileImg: CircleImageView = view.findViewById(R.id.img_user_profile_item_search)
        private val txtName: TextView = view.findViewById(R.id.txt_username_item)
        val layout: ConstraintLayout = view.findViewById(R.id.user_item_layout)

        fun bindData(unsplashUser: UnsplashUser){
            txtName.text = unsplashUser.name
            txtName.isSelected = true
            loadImage(profileImg, unsplashUser.profile_image.large)
        }

    }

    class UserDiffUtil : DiffUtil.ItemCallback<UnsplashUser>(){
        override fun areItemsTheSame(oldItem: UnsplashUser, newItem: UnsplashUser): Boolean {
            return oldItem.username == newItem.username
        }

        override fun areContentsTheSame(oldItem: UnsplashUser, newItem: UnsplashUser): Boolean {
            return areItemsTheSame(oldItem, newItem)
        }

    }
}