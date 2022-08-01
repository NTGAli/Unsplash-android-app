package com.example.pic.view.fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import com.example.pic.R
import com.example.pic.databinding.FragmentDetailsFeedBinding
import com.example.pic.model.ImageDetails
import com.example.pic.viewModel.FeedViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
@OptIn(ExperimentalPagingApi::class)
class DetailsFeedFragment : Fragment() {

    private lateinit var binding: FragmentDetailsFeedBinding
    private val viewModel: FeedViewModel by viewModels()
    private val bundle = Bundle()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsFeedBinding.inflate(LayoutInflater.from(context), container, false)



        viewModel.getSpecificImage(requireArguments().getString("imageID")!!)
            .observe(viewLifecycleOwner) {
                it!!.created_at = getDate(it.created_at)
                loadImage(binding.regularImgDetails, it.urls.regular)
                loadImage(binding.profileImage, it.user.profile_image.large)
                bundle.putString("username", it.user.username)
                binding.detail = it
            }


        binding.txtNameUser.setOnClickListener {
            loadUser(bundle)
        }
        binding.profileImage.setOnClickListener {
            loadUser(bundle)
        }

        binding.feedDetailsTooBar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }


        return binding.root
    }

    @BindingAdapter("imageUrl")
    fun loadImage(view: ImageView, url: String) {
        Picasso.get().load(url).into(view)
    }

    @SuppressLint("SimpleDateFormat")
    private fun getDate(imgDate: String): String {
//        var today = Date()
//        var date = SimpleDateFormat("dd-MM-yyyy").parse(imgDate)
//        var diff: Long = today.time - date!!.time
//        var seconds = diff / 1000
//        var minutes = seconds / 60
//        var hours = minutes / 60
//        var days = hours / 24
//
//        return if (days > 365){
//            SimpleDateFormat("dd/M/yyyy").format(date)
//        }else{
//            "$days days ago"
//        }

        return "8 days ago"
    }

    private fun loadUser(userBundle: Bundle) {
        findNavController().navigate(R.id.photographerDetailsFragment, userBundle)
    }


}