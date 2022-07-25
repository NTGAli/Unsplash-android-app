package com.example.pic.view.fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.pic.databinding.FragmentDetailsFeedBinding
import com.example.pic.model.ImageDetails
import com.example.pic.viewModel.FeedViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class DetailsFeedFragment : Fragment() {

    private lateinit var binding: FragmentDetailsFeedBinding
    private val viewModel: FeedViewModel by viewModels()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsFeedBinding.inflate(LayoutInflater.from(context), container, false)

        viewModel.getSpecificImage(FeedFragment.imageID).observe(viewLifecycleOwner){
            it!!.created_at = getDate(it.created_at)
            loadImage(binding.regularImgDetails, it.urls.regular)
            loadImage(binding.profileImage, it.user.profile_image.large)
            println("11111111111111111 ${it.user.profile_image.large}")
            binding.detail = it
        }


        return binding.root
    }

    @BindingAdapter("imageUrl")
    fun loadImage(view: ImageView, url: String) {
        Picasso.get().load(url).into(view)
    }

    @SuppressLint("SimpleDateFormat")
    private fun getDate(imgDate: String): String{
        val today = Date()
        val date = SimpleDateFormat("dd-MM-yyyy").parse(imgDate)
        val diff: Long = today.time - date!!.time
        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24

        return if (days > 365){
            SimpleDateFormat("dd/M/yyyy").format(date)
        }else{
            "$days days ago"
        }
    }


}