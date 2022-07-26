package com.example.pic.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pic.R
import com.example.pic.adapter.TopicListAdapter
import com.example.pic.databinding.FragmentTopicBinding
import com.example.pic.model.Topic
import com.example.pic.viewModel.TopicViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TopicFragment : Fragment() {

    private lateinit var binding: FragmentTopicBinding
    private val viewModel: TopicViewModel by viewModels()
    lateinit var topicAdapter: TopicListAdapter
    companion object{
        lateinit var topic: Topic
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTopicBinding.inflate(LayoutInflater.from(context), container, false)


        init()

        viewModel.getTopics().observe(viewLifecycleOwner){
            topicAdapter.submitList(it)
        }


        return binding.root
    }


    private fun init(){
        topicAdapter = TopicListAdapter(){
            topic = it
            println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaa $topic")
            findNavController().navigate(R.id.topicDetailsFragment)
        }

        setUpList()
    }

    private fun setUpList(){
        val gridLayoutManager= GridLayoutManager(requireContext(), 2)
        binding.topicRcv.apply {
            layoutManager= gridLayoutManager
            adapter = topicAdapter
        }
    }


}