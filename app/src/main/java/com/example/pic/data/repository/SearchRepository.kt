package com.example.pic.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.pic.model.Feed
import com.example.pic.model.ResultImage
import com.example.pic.model.ResultUser
import com.example.pic.network.UnsplashApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class SearchRepository @Inject constructor(private val unsplashApi: UnsplashApi) {
    private var feeds: List<Feed>? = null
    private var searchUsers: ResultUser? = null

    suspend fun getSomeImages(): List<Feed>?{
        feeds = unsplashApi.getAllImagesByOrder(1,10,"popular")
        return feeds
    }


    suspend fun searchInUsers(query: String): ResultUser?{

        searchUsers = unsplashApi.searchInUsers(query)
        return searchUsers
    }

}