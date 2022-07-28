package com.example.pic.view.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pic.R
import com.example.pic.R.id.tab_2
import com.example.pic.adapter.FeedListAdapter
import com.example.pic.adapter.UserListAdapter
import com.example.pic.databinding.FragmentSearchBinding
import com.example.pic.viewModel.SearchViewModel
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchFragment : Fragment() {


    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchImagesAdapter: FeedListAdapter
    private lateinit var searchUsersAdapter: UserListAdapter
    private val viewModel: SearchViewModel by viewModels()

    companion object{
        lateinit var query: String
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(LayoutInflater.from(context), container, false)

        init()

        viewModel.getSomeImages().observe(viewLifecycleOwner){
            searchImagesAdapter.submitList(it)
        }

        binding.searchTil.editText?.setOnEditorActionListener { _, keyCode, event ->
            if ((keyCode == EditorInfo.IME_ACTION_DONE)
            ) {
                query = binding.searchTil.editText?.text.toString()
                viewModel.searchInImages(query).observe(viewLifecycleOwner){
                    searchImagesAdapter.submitList(it?.results)
                }

                binding.searchTil.editText?.isFocusable = false
                binding.searchTil.editText?.isSelected = false


                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {

                if (tab?.position == 0){
                    viewModel.searchInImages(query).observe(viewLifecycleOwner){
                        searchImagesAdapter.submitList(it?.results)
                    }
                    setUpImagesList()
                }else if (tab?.position == 1){
                    viewModel.searchInUsers(query).observe(viewLifecycleOwner){
                        searchUsersAdapter.submitList(it?.results)
                    }
                    setUpUsersList()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        return binding.root
    }

    private fun init(){
        searchUsersAdapter = UserListAdapter()
        setUpImagesList()
    }

    private fun setUpImagesList(){
        searchImagesAdapter = FeedListAdapter(){ feed, onLing ->

        }


        binding.searchImgRcv.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = searchImagesAdapter
        }
    }


    private fun setUpUsersList(){


        binding.searchImgRcv.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = searchUsersAdapter
        }
    }


}