package com.example.pic.view

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.Transformation
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.pic.R
import com.example.pic.databinding.ActivityMainBinding
import com.example.pic.view.custom.gone
import com.example.pic.view.custom.visible
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import com.github.pwittchen.reactivenetwork.library.rx2.internet.observing.InternetObservingSettings
import com.github.pwittchen.reactivenetwork.library.rx2.internet.observing.strategy.SocketInternetObservingStrategy
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController


    @SuppressLint("CheckResult")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()


        ReactiveNetwork
            .observeInternetConnectivity()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { isConnectedToInternet: Boolean? ->

                try {
                    if (!isConnectedToInternet!!) {
                        binding.banner.visible()
                        expand(binding.banner)
                        binding.txtErrorBanner.text =
                            resources.getText(R.string.error_connection_message)
                    } else {
                        dismissBanner()
                    }
                } catch (e: Exception) {

                }
            }


        val settings = InternetObservingSettings.builder()
            .host("https://unsplash.com/")
            .strategy(SocketInternetObservingStrategy())
            .build()

        ReactiveNetwork
            .observeInternetConnectivity(settings)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { isConnectedToHost: Boolean? ->
                if (!isConnectedToHost!!) {
                    binding.banner.visible()
                    expand(binding.banner)
                    binding.txtErrorBanner.text =
                        resources.getText(R.string.error_connection_message2)
                }

            }

        binding.bottomNavMain.setupWithNavController(navController)

        binding.bottomNavMain.setOnItemReselectedListener {
            when (it.itemId) {
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

        binding.dismissButton.setOnClickListener {
            dismissBanner()
        }

    }

    private fun dismissBanner() {
        val animate: Animation =
            AnimationUtils.loadAnimation(binding.root.context, R.anim.left_to_right)

        binding.banner.startAnimation(animate)
        binding.banner.gone()
    }

    private fun expand(v: View) {
        val a = expandAction(v)
        v.startAnimation(a)
    }

    private fun expandAction(v: View): Animation {
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val targetHeight = v.measuredHeight
        v.layoutParams.height = 0
        v.visibility = View.VISIBLE
        val a: Animation = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                v.layoutParams.height =
                    if (interpolatedTime == 1f) ViewGroup.LayoutParams.WRAP_CONTENT else (targetHeight * interpolatedTime).toInt()
                v.requestLayout()
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }
        a.duration = ((targetHeight / v.context.resources.displayMetrics.density).toLong())
        v.startAnimation(a)
        return a
    }


}