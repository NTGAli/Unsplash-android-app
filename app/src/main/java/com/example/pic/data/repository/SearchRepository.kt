package com.example.pic.data.repository

import com.example.pic.model.res.Feed
import com.example.pic.model.res.ResultUser
import com.example.pic.data.remote.UnsplashApi
import retrofit2.Response
import javax.inject.Inject

class SearchRepository @Inject constructor(private val unsplashApi: UnsplashApi) {

    suspend fun getSomeImages(): Response<List<Feed>>{
        return unsplashApi.getAllImagesByOrder(1,10,"popular")
    }


    suspend fun searchInUsers(query: String): ResultUser?{
        return unsplashApi.searchInUsers(query)
    }

}