package com.example.pic.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.pic.MyApplication
import com.example.pic.data.remote.*
import com.example.pic.util.Constants.BASE_URL
import com.example.pic.util.Constants.CACHE_SIZE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @RequiresApi(Build.VERSION_CODES.M)
    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
//            .readTimeout(15, TimeUnit.SECONDS)
//            .connectTimeout(15, TimeUnit.SECONDS)
            .cache(cache())
            .addInterceptor(LoggingInterceptor().httpLoggingInterceptor())
            .addInterceptor(HeaderInterceptor())
            .addNetworkInterceptor(NetworkInterceptor())
            .addInterceptor(OfflineInterceptor())
            .build()
    }

    private fun cache(): Cache{
        return Cache(File(MyApplication.instance?.cacheDir,"someImages"), CACHE_SIZE.toLong())
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun getRetrofitInstance(retrofit: Retrofit): UnsplashApi {
        return retrofit.create(UnsplashApi::class.java)
    }



}