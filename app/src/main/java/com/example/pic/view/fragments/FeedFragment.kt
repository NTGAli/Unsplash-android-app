package com.example.pic.view.fragments

import android.annotation.SuppressLint
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
import com.example.pic.databinding.FragmentHomeBinding
import com.example.pic.util.showSnackBar
import com.example.pic.view.adapter.FeedPagerDataAdapter
import com.example.pic.view.custom.gone
import com.example.pic.view.custom.imgPreview
import com.example.pic.view.custom.visible
import com.example.pic.viewModel.FeedViewModel
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import com.github.pwittchen.reactivenetwork.library.rx2.internet.observing.InternetObservingSettings
import com.github.pwittchen.reactivenetwork.library.rx2.internet.observing.strategy.SocketInternetObservingStrategy
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class FeedFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private val viewModel: FeedViewModel by viewModels()
    private lateinit var feedAdapter: FeedPagerDataAdapter
    val bundle = Bundle()

    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        init()

        loadData()

        lifecycleScope.launch(viewLifecycleOwner.lifecycleScope.coroutineContext) {
            feedAdapter.loadStateFlow.collectLatest { loadStates ->
                if (loadStates.refresh is LoadState.Loading) {
                    binding.shimmerViewContainer.startShimmerAnimation()
                    binding.shimmerViewContainer.visible()
                } else {
                    binding.shimmerViewContainer.stopShimmerAnimation()
                    binding.shimmerViewContainer.gone()
                }

                if (loadStates.refresh is LoadState.Error) {
                    binding.root.showSnackBar(
                        "Error",
                        (loadStates.refresh as LoadState.Error).error.message.toString()
                    )
                    binding.refreshFeed.isRefreshing = false
                }
            }
        }


        binding.refreshFeed.setOnRefreshListener {
            loadData()

        }

        ReactiveNetwork
            .observeInternetConnectivity()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { isConnectedToInternet: Boolean? ->

                try {
                    if (isConnectedToInternet!!) {
                        loadData()
                    }
                } catch (e: Exception) {

                }
            }

        val settings = InternetObservingSettings.builder()
            .host("https://unsplash.com/")
            .strategy(SocketInternetObservingStrategy())
            .build()

        ReactiveNetwork
            .observeInternetConnectivity(settings)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { isConnectedToHost: Boolean? ->
                if (isConnectedToHost!!) {
                    loadData()
                }

            }

        return binding.root
    }

    private fun loadData() {
        viewModel.getImages().observe(viewLifecycleOwner) {
            lifecycleScope.launch(Dispatchers.IO) {
                feedAdapter.submitData(it)
                binding.refreshFeed.isRefreshing = false

            }

        }
    }

    private fun init() {
        setUpList()
    }

    private fun setUpList() {
        feedAdapter = FeedPagerDataAdapter { feed, onLong ->
            if (onLong) {
                binding.root.imgPreview(feed?.urls?.regular)
            } else {
//                imageID = feed.id
                bundle.putString("imageID", feed?.id)
                findNavController().navigate(R.id.detailsFeedFragment, bundle)
            }
        }
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)

        binding.feedRecv.apply {
            layoutManager = gridLayoutManager
            adapter = feedAdapter
        }

    }

    override fun onResume() {
        super.onResume()
        binding.shimmerViewContainer.startShimmerAnimation()
    }

    override fun onPause() {
        super.onPause()
        binding.shimmerViewContainer.stopShimmerAnimation()
        super.onPause()
    }


}