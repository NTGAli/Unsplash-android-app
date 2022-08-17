package com.example.pic.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter(value = ["bind:imageViewUrl"], requireAll = false)
fun loadImage(view: ImageView, url: String){
    Glide.with(view).load(url).into(view)
}