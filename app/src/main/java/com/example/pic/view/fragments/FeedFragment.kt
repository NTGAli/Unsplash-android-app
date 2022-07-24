package com.example.pic.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pic.adapter.FeedListAdapter
import com.example.pic.databinding.FragmentHomeBinding
import com.example.pic.viewModel.FeedViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FeedFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private val viewModel: FeedViewModel by viewModels()
    private lateinit var feedAdapter: FeedListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        init()

        viewModel.getUserListObserve(1).observe(viewLifecycleOwner){
            Log.d("llllllllllll",it.toString())
            feedAdapter.submitList(it)
        }
        return binding.root
    }

    private fun init(){
        feedAdapter = FeedListAdapter(){

        }
        val gridLayoutManager = GridLayoutManager(requireContext(),2)

        binding.feedRecv.apply {
            layoutManager = gridLayoutManager
            adapter = feedAdapter
        }

    }


}