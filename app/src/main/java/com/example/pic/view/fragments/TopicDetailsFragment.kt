package com.example.pic.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pic.R
import com.example.pic.databinding.FragmentTopicDetailsBinding
import com.example.pic.util.loadImage
import com.example.pic.view.adapter.FeedPagerDataAdapter
import com.example.pic.view.custom.gone
import com.example.pic.view.custom.imgPreview
import com.example.pic.view.custom.visible
import com.example.pic.viewModel.TopicViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
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
    ): View {
        // Inflate the layout for this fragment
        binding =
            FragmentTopicDetailsBinding.inflate(LayoutInflater.from(context), container, false)

        init()

        viewModel.getPhotosTopic(TopicFragment.topic.id).observe(viewLifecycleOwner){
            lifecycleScope.launch(Dispatchers.IO){
                topicDetailsAdapter.submitData(it)
            }
        }

        lifecycleScope.launch(viewLifecycleOwner.lifecycleScope.coroutineContext) {
            topicDetailsAdapter.loadStateFlow.collectLatest { loadStates ->
                if (loadStates.refresh == LoadState.Loading){
                    binding.shimmerDetailsTopic.startShimmerAnimation()
                    binding.shimmerDetailsTopic.visible()
                }else{
                    binding.shimmerDetailsTopic.stopShimmerAnimation()
                    binding.shimmerDetailsTopic.gone()
                }
            }
        }




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
        topicDetailsAdapter = FeedPagerDataAdapter { feed, onLong ->
            if (onLong) {
                binding.root.imgPreview(feed?.urls?.regular)
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


}