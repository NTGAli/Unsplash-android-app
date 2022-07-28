package com.example.pic.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pic.R
import com.example.pic.adapter.FeedListAdapter
import com.example.pic.databinding.FragmentPhotographerDetailsBinding
import com.example.pic.viewModel.FeedViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotographerDetailsFragment : Fragment() {

    private lateinit var binding: FragmentPhotographerDetailsBinding
    private val viewModel: FeedViewModel by viewModels()
    private lateinit var itemsAdapter: FeedListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPhotographerDetailsBinding.inflate(LayoutInflater.from(context), container, false)

        init()

        viewModel.getUserByUsername(FeedFragment.username).observe(viewLifecycleOwner){
            binding.details = it
            loadImage(binding.profileImage, it!!.profile_image.large)
            itemsAdapter.submitList(it.photos)
        }


        binding.toolbarPhotographDetail.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        return binding.root
    }

    private fun init(){

        setUpList()
    }

    @BindingAdapter("imageUrl")
    fun loadImage(view: ImageView, url: String) {
        Picasso.get().load(url).into(view)
    }

    private fun setUpList(){
        itemsAdapter = FeedListAdapter(){feed, onLong ->
            if (onLong){

            }else {
                FeedFragment.imageID = feed.id
                findNavController().navigate(R.id.detailsFeedFragment)
            }
        }

        binding.recvUserItem.apply {
            layoutManager = GridLayoutManager(requireContext(),2)
            adapter = itemsAdapter
        }
    }
}