package com.example.pic.view

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
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
    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        init()


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()


//
//        appBarConfiguration = AppBarConfiguration(
//            setOf(R.id.homeFragment, R.id.topicFragment, R.id.searchFragment, R.id.profileFragment)
//        )

//        setupActionBarWithNavController(navController, appBarConfiguration)



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

    private fun init() {
//        setToolBar("Feed")
    }

//    @Override
//    fun onNavigationItemSelected(item: MenuItem): Boolean {
//        // Handle navigation view item clicks here.
//        when (item.getItemId()) {
//            R.id.topic_nav -> {
//                println("TOPIC TOPIC TOPIC TOPIC TOPIC")
//            }
//        }
//        return true
//    }




//    @SuppressLint("SetTextI18n")
//    fun setToolBar(toolBarName: String) {
//        setSupportActionBar(binding.toolbar)
//        binding.toolbarTitle.text = toolBarName
//        supportActionBar!!.setDisplayShowTitleEnabled(false)
//    }


}