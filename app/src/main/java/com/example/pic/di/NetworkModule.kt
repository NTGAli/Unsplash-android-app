package com.example.pic.di

import com.example.pic.data.remote.HeaderInterceptor
import com.example.pic.data.remote.UnsplashApi
import com.example.pic.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
//            .readTimeout(15, TimeUnit.SECONDS)
//            .connectTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(HeaderInterceptor())
            .build()
    }

//    @Provides
//    @Singleton
//    fun provideOkHttpClient(): OkHttpClient {
//        val logging = HeaderInterceptor()
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
//
//        return OkHttpClient.Builder()
//            .addInterceptor(logging)
//            .addInterceptor(HeaderInterceptor())
//            .build()
//    }

    @OptIn(ExperimentalSerializationApi::class)
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