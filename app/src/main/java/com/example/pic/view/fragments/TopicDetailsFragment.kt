package com.example.pic.view.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pic.R
import com.example.pic.view.adapter.FeedListAdapter
import com.example.pic.databinding.FragmentTopicDetailsBinding
import com.example.pic.view.adapter.FeedPagerDataAdapter
import com.example.pic.viewModel.TopicViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TopicDetailsFragment : Fragment() {

    private lateinit var binding: FragmentTopicDetailsBinding
    private val viewModel: TopicViewModel by viewModels()
    private lateinit var topicDetailsAdapter: FeedPagerDataAdapter
    private val bundle = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            FragmentTopicDetailsBinding.inflate(LayoutInflater.from(context), container, false)

//        viewModel.getPhotosTopic(TopicFragment.topic.id).observe(viewLifecycleOwner) {
//            topicDetailsAdapter.submitData(it)
//        }

        viewModel.getPhotosTopic(TopicFragment.topic.id).observe(viewLifecycleOwner){
            lifecycleScope.launch(Dispatchers.IO){
                topicDetailsAdapter.submitData(it)
            }
        }


        init()


        binding.toolbarTopicDetail.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        return binding.root
    }

    private fun init() {
        binding.detail = TopicFragment.topic
        loadImage(binding.topicImgMain, TopicFragment.topic.cover_photo.urls.regular)

        setUpList()
    }

    private fun setUpList() {
        topicDetailsAdapter = FeedPagerDataAdapter() { feed, onLong ->
            if (onLong) {
                imgPreview(feed?.urls?.regular)
            } else {
                bundle.putString("imageID", feed?.id)
                findNavController().navigate(R.id.detailsFeedFragment, bundle)
            }

        }

        val gridLayoutManager = GridLayoutManager(requireContext(), 2)

        binding.topicImgRcv.apply {
            layoutManager = gridLayoutManager
            adapter = topicDetailsAdapter
        }
    }

    @BindingAdapter("ImageUrl")
    fun loadImage(view: ImageView, link: String?) {
        Picasso.get().load(link).into(view)
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


}