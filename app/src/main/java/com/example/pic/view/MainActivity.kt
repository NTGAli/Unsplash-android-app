package com.example.pic.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
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

//        appBarConfiguration = AppBarConfiguration(
//            setOf(R.id.homeFragment, R.id.topicFragment, R.id.searchFragment, R.id.profileFragment)
//        )
//
//        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.bottomNavMain.setupWithNavController(navController)

    }

    private fun init() {
        setToolBar("Feed")
    }

    @SuppressLint("SetTextI18n")
    fun setToolBar(toolBarName: String) {
        setSupportActionBar(binding.toolbar)
        binding.toolbarTitle.text = toolBarName
        supportActionBar!!.setDisplayShowTitleEnabled(false)
    }


}