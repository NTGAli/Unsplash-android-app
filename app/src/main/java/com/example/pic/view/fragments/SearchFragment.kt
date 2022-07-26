package com.example.pic.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pic.R
import com.example.pic.adapter.FeedListAdapter
import com.example.pic.databinding.FragmentSearchBinding
import com.example.pic.viewModel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {


    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchAdapter: FeedListAdapter
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(LayoutInflater.from(context), container, false)

        init()

        viewModel.getSomeImages().observe(viewLifecycleOwner){
            searchAdapter.submitList(it)
        }

        return binding.root
    }

    private fun init(){
        setUpList()
    }

    private fun setUpList(){
        searchAdapter = FeedListAdapter(){ feed, onLing ->

        }


        binding.searchRcv.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = searchAdapter
        }
    }


}