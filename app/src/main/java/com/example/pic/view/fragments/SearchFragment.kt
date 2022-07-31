package com.example.pic.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.pic.view.adapter.SearchPagerAdapter
import com.example.pic.databinding.FragmentSearchBinding
import com.example.pic.viewModel.SearchViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchFragment : Fragment() {


    private lateinit var binding: FragmentSearchBinding
    private val tabsTitle = arrayOf("Photos", "Users")
    private val viewModel: SearchViewModel by activityViewModels()

    companion object{
        lateinit var query: String
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(LayoutInflater.from(context), container, false)




        binding.searchTil.editText?.setOnEditorActionListener { _, keyCode, event ->
            if ((keyCode == EditorInfo.IME_ACTION_DONE)
            ) {
                query = binding.searchTil.editText?.text.toString()

                viewModel.searchInImages(query)


                viewModel.searchInUsers(query).observe(viewLifecycleOwner){
                    viewModel.setUsersList(it?.results)
                }

//                binding.searchTil.editText?.isFocusable = false
//                binding.searchTil.editText?.isFocusableInTouchMode = false
//                binding.searchTil.editText?.isSelected = false


                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        binding.searchTil.setOnClickListener {
            binding.searchTil.editText?.isFocusable = true
            binding.searchTil.editText?.isFocusableInTouchMode = true

//            binding.searchTil.editText?.requestFocus()
        }


        binding.viewPager.adapter = SearchPagerAdapter(requireActivity().supportFragmentManager, lifecycle)
        TabLayoutMediator(binding.tabLayout, binding.viewPager){ tab, position ->
            tab.text = tabsTitle[position]
        }.attach()


        return binding.root
    }






}