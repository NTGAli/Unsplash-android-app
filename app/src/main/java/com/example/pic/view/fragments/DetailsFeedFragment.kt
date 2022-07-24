package com.example.pic.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pic.databinding.FragmentDetailsFeedBinding

class DetailsFeedFragment : Fragment() {

    private lateinit var binding: FragmentDetailsFeedBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsFeedBinding.inflate(LayoutInflater.from(context), container, false)



        return binding.root
    }


}