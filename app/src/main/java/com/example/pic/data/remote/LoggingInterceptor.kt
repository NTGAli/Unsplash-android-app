package com.example.pic.data.remote

import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber

class LoggingInterceptor {
    fun httpLoggingInterceptor(): HttpLoggingInterceptor{
        return HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
            Timber.d( "HttpLog: log: http log: $it")
        }).setLevel(HttpLoggingInterceptor.Level.BODY)
    }
}