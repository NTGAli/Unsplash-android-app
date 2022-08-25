package com.example.pic.data.remote

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.pic.MyApplication
import com.example.pic.util.Constants
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import timber.log.Timber
import java.util.concurrent.TimeUnit

class OfflineInterceptor: Interceptor {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        Timber.d( "offline interceptor: called.")
        var request: Request = chain.request()

        if (!MyApplication().isNetworkConnected()) {
            val cacheControl: CacheControl = CacheControl.Builder()
                .maxStale(7, TimeUnit.DAYS)
                .build()
            request = request.newBuilder()
                .removeHeader(Constants.HEADER_PRAGMA)
                .removeHeader(Constants.HEADER_CACHE_CONTROL)
                .cacheControl(cacheControl)
                .build()
        }
        chain.proceed(request)
    }
}