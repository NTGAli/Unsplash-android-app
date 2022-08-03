package com.example.pic.data.repository

import com.example.pic.model.res.Feed
import com.example.pic.model.res.ResultUser
import com.example.pic.data.remote.UnsplashApi
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