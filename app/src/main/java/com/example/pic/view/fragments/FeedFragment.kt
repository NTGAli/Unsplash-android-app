package com.example.pic.view.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import androidx.core.widget.NestedScrollView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pic.R
import com.example.pic.adapter.FeedListAdapter
import com.example.pic.databinding.FragmentHomeBinding
import com.example.pic.model.Feed
import com.example.pic.viewModel.FeedViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FeedFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private val viewModel: FeedViewModel by viewModels()
    private lateinit var feedAdapter: FeedListAdapter
    var page = 1
    var users: ArrayList<Feed>? = arrayListOf()

    companion object{
        lateinit var imageID: String
        lateinit var username: String
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        init()

        getPage()


        binding.idNestedSV.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->

            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight && page < 200) {
                page++
                getPage()
            }
        })

        return binding.root
    }

    private fun init(){
        activity?.title = "HH"
        setUpList()
    }

    private fun setUpList(){
        feedAdapter = FeedListAdapter(){feed, onLong ->
            if (onLong){
                imgPreview(feed.urls.regular)
            }else {
                imageID = feed.id
                findNavController().navigate(R.id.detailsFeedFragment)
            }
        }
        val gridLayoutManager = GridLayoutManager(requireContext(),2)

        binding.feedRecv.apply {
            layoutManager = gridLayoutManager
            adapter = feedAdapter
        }
    }
    private fun getPage(){
        viewModel.getPage(page).observe(viewLifecycleOwner) {
            users = (users?.plus(it as ArrayList<Feed>)) as ArrayList<Feed>
            feedAdapter.submitList(users!!.distinct())
        }
    }

    private fun imgPreview(imgLink: String) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_image_preview)
        var imgPreview: ImageView = dialog.findViewById(R.id.img_preview_feed)
        loadImage(imgPreview, imgLink)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.show()
    }

    @BindingAdapter("imageUrl")
    fun loadImage(view: ImageView, url: String) {
        Picasso.get().load(url).into(view)
    }


}