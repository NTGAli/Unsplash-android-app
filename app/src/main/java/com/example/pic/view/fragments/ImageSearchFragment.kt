package com.example.pic.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pic.R
import com.example.pic.view.adapter.FeedPagerDataAdapter
import com.example.pic.view.custom.gone
import com.example.pic.view.custom.imgPreview
import com.example.pic.view.custom.visible
import com.example.pic.viewModel.SearchViewModel
import com.facebook.shimmer.ShimmerFrameLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ImageSearchFragment : Fragment() {

    private lateinit var imageSearchView: View
    private lateinit var searchImagesAdapter: FeedPagerDataAdapter
    private val bundle= Bundle()
    private lateinit var rcv: RecyclerView
    private lateinit var shimmer: ShimmerFrameLayout

    val viewModel: SearchViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        imageSearchView = inflater.inflate(R.layout.fragment_image_search, container, false)




        viewModel.getSomeImages().observe(viewLifecycleOwner){
            lifecycleScope.launch(Dispatchers.IO) {
                searchImagesAdapter.submitData(it)
            }
        }



        viewModel.getQuery().observe(viewLifecycleOwner){
            viewModel.getListOfImagesSearched().observe(viewLifecycleOwner){
                lifecycleScope.launch {
                    searchImagesAdapter.submitData(it)
                }
            }
        }



        init()

        lifecycleScope.launch(viewLifecycleOwner.lifecycleScope.coroutineContext) {
            searchImagesAdapter.loadStateFlow.collectLatest { loadStates ->
                if (loadStates.refresh == LoadState.Loading){
                    shimmer.startShimmerAnimation()
                    shimmer.visible()
                }else{
                    shimmer.stopShimmerAnimation()
                    shimmer.gone()
                }
            }
        }





        return imageSearchView
    }

    private fun init(){
        rcv = imageSearchView.findViewById(R.id.searchImg_rcv)
        shimmer = imageSearchView.findViewById(R.id.shimmer_search_user)
        setUpImagesList()
    }

    private fun setUpImagesList(){

        searchImagesAdapter = FeedPagerDataAdapter{ feed, onLong ->
            if (onLong){
                imageSearchView.imgPreview(feed?.urls?.regular)
            }else{
                bundle.putString("imageID", feed?.id)
                findNavController().navigate(R.id.detailsFeedFragment, bundle)
            }
        }

        rcv.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = searchImagesAdapter
        }

    }
}