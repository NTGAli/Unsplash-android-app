package com.example.pic.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.pic.view.fragments.ImageSearchFragment
import com.example.pic.view.fragments.UserSearchFragment

class SearchPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        when (position){
            0 -> return ImageSearchFragment()
            1 -> return UserSearchFragment()
            else -> return ImageSearchFragment()
        }
    }
}