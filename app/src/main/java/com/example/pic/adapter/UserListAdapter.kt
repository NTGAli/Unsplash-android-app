package com.example.pic.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pic.R
import com.example.pic.model.UnsplashUser
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView


class UserListAdapter: ListAdapter<UnsplashUser, UserListAdapter.UserViewHolder>(UserDiffUtil()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserListAdapter.UserViewHolder {
        return UserViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false))
    }

    override fun onBindViewHolder(holder: UserListAdapter.UserViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }

    class UserViewHolder(private val view:View): RecyclerView.ViewHolder(view){
        private val profileImg: CircleImageView = view.findViewById(R.id.img_user_profile_item_search)
        private val txtName: TextView = view.findViewById(R.id.txt_username_item)

        fun bindData(unsplashUser: UnsplashUser){
            val anim: Animation = AnimationUtils.loadAnimation(view.context, R.anim.left_to_right)
            txtName.text = unsplashUser.name
//            txtName.animation =anim
            txtName.isSelected = true
            loadImage(profileImg, unsplashUser.profile_image.large)
        }

        @BindingAdapter("ImageUrl")
        fun loadImage(view: ImageView, link: String){
            Picasso.get().load(link).into(view)
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