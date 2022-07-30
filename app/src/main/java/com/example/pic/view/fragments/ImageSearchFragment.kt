package com.example.pic.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pic.R
import com.example.pic.adapter.FeedListAdapter
import com.example.pic.model.Feed
import com.example.pic.viewModel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageSearchFragment : Fragment() {

    private lateinit var imageSearchView: View
    private lateinit var searchImagesAdapter: FeedListAdapter
    lateinit var lists: List<Feed>
    private lateinit var rcv: RecyclerView
    val viewModel: SearchViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        imageSearchView = inflater.inflate(R.layout.fragment_image_search, container, false)

        viewModel.getListOfImagesSearched().observe(viewLifecycleOwner){
            println("aaaaaaaaaaaaaaaa ${it.toString()}")
            searchImagesAdapter.submitList(it)
        }

        init()


        return imageSearchView
    }

    private fun init(){
        rcv = imageSearchView.findViewById(R.id.searchImg_rcv)
        setUpImagesList()
    }

    private fun setUpImagesList(){

        searchImagesAdapter = FeedListAdapter(){ feed, onLing ->

        }

        rcv.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = searchImagesAdapter
        }

    }

    fun getData(list: List<Feed>?){
        viewModel.getListOfImagesSearched().observe(viewLifecycleOwner){
            println("aaaaaaaaaaaaaaaa ${it.toString()}")
            searchImagesAdapter.submitList(it)
        }



    }

}