package com.example.pic.view.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import com.example.pic.R
import com.example.pic.data.remote.NetworkResult
import com.example.pic.databinding.FragmentDetailsFeedBinding
import com.example.pic.util.loadImage
import com.example.pic.util.showSnackBar
import com.example.pic.viewModel.FeedViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit


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
    ): View {
        binding = FragmentDetailsFeedBinding.inflate(LayoutInflater.from(context), container, false)

        viewModel.getSpecificImage(requireArguments().getString("imageID")!!)
            ?.observe(viewLifecycleOwner) {


                when (it) {
                    is NetworkResult.Loading -> {
                        // Loading state
                    }

                    is NetworkResult.Success -> {
//                        it!!.data?.created_at = getDate(it.data.created_at)

                        loadImage(binding.regularImgDetails, it.data?.urls?.regular!!)
                        loadImage(binding.profileImage, it.data.user.profile_image.large)
                        bundle.putString("username", it.data.user.username)
                        binding.detail = it.data
                        it.data.created_at = counterTime(it.data.created_at.split("T")[0])

                    }

                    is NetworkResult.Error -> {
                        binding.root.showSnackBar("Error",it.message.toString())
                    }
                }
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

    private fun counterTime(imageDate: String): String {


        val from = LocalDate.parse(imageDate)
        val to = LocalDate.now()
        val period: Period = Period.between(from, to)

        Log.d("My Time", period.years.toString())

        return if (period.years == 0){
            "${period.days} days ago"
        }else{
            "${period.years} years ago"
        }

    }


    private fun loadUser(userBundle: Bundle) {
        findNavController().navigate(R.id.photographerDetailsFragment, userBundle)
    }


}