package com.example.pic.data.remote

import com.example.pic.util.Constants
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request()
                .newBuilder()
                .addHeader("Authorization", "Client-ID ${Constants.API_KEY}")
                .build()
        )
    }
}