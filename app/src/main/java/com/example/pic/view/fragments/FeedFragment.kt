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
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pic.R
import com.example.pic.view.adapter.FeedPagerDataAdapter
import com.example.pic.databinding.FragmentHomeBinding
import com.example.pic.model.res.Feed
import com.example.pic.viewModel.FeedViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*


@AndroidEntryPoint
@OptIn(ExperimentalPagingApi::class)
class FeedFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private val viewModel: FeedViewModel by viewModels()
    private lateinit var feedAdapter: FeedPagerDataAdapter
    var page = 1
    var users: ArrayList<Feed>? = arrayListOf()
    val bundle = Bundle()

    companion object{
//        lateinit var imageID: String
//        lateinit var username: String
    }
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        init()

//        getPage()



//        viewModel.getImages().observe(viewLifecycleOwner){
//            feedAdapter.submitData(it)
//        }

        viewModel.getImages().observe(viewLifecycleOwner){
            lifecycleScope.launch(Dispatchers.IO){
                feedAdapter.submitData(it)
            }
        }


//        binding.idNestedSV.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
//
//            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight && page < 200) {
//                page++
//                getPage()
//            }
//        })

        return binding.root
    }

    private fun init(){
        setUpList()
    }

    private fun setUpList(){
        feedAdapter = FeedPagerDataAdapter(){ feed, onLong ->
            if (onLong){
                imgPreview(feed?.urls?.regular)
            }else {
//                imageID = feed.id
                bundle.putString("imageID", feed?.id)
                findNavController().navigate(R.id.detailsFeedFragment, bundle)
            }
        }
        val gridLayoutManager = GridLayoutManager(requireContext(),2)

        binding.feedRecv.apply {
            layoutManager = gridLayoutManager
            adapter = feedAdapter
        }
    }



    private fun imgPreview(imgLink: String?) {
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
    fun loadImage(view: ImageView, url: String?) {
        Picasso.get().load(url).into(view)
    }


}