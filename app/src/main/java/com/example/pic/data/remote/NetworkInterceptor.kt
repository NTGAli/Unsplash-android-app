package com.example.pic.data.remote

import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.pic.MyApplication
import com.example.pic.util.Constants.HEADER_CACHE_CONTROL
import com.example.pic.util.Constants.HEADER_PRAGMA
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.util.concurrent.TimeUnit


class NetworkInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        Log.d(TAG, "network interceptor: called.")
        val response: Response = chain.proceed(chain.request())
        val cacheControl: CacheControl = CacheControl.Builder()
            .maxAge(5, TimeUnit.SECONDS)
            .build()
        response.newBuilder()
            .removeHeader(HEADER_PRAGMA)
            .removeHeader(HEADER_CACHE_CONTROL)
            .header(HEADER_CACHE_CONTROL, cacheControl.toString())
            .build()
    }


}