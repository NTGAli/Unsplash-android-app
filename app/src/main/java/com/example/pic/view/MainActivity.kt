package com.example.pic.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.pic.R
import com.example.pic.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        private lateinit var binding: ActivityMainBinding
    }
    private lateinit var navController: NavController
//    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()



        binding.bottomNavMain.setupWithNavController(navController)

        binding.bottomNavMain.setOnItemReselectedListener {
            when (it.itemId){
                R.id.feed_nav -> {
                    navController.popBackStack()
                    navController.navigate(R.id.feed_nav)
                }

                R.id.topic_nav -> {
                    navController.popBackStack()
                    navController.navigate(R.id.topic_nav)
                }

                R.id.search_nav -> {
                    navController.popBackStack()
                    navController.navigate(R.id.search_nav)
                }

                R.id.profile_nav -> {
                    navController.popBackStack()
                    navController.navigate(R.id.profile_nav)
                }

            }
        }

    }
}