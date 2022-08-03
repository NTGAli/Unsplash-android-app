package com.example.pic.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.pic.model.res.Feed
import com.example.pic.model.res.ImageDetailsRes
import com.example.pic.model.res.UnsplashUser
import com.example.pic.data.remote.UnsplashApi
import javax.inject.Inject

class UnsplashImageRepository @Inject constructor(private val unsplashApi: UnsplashApi){
    var liveDataList: MutableLiveData<List<Feed>?> = MutableLiveData()
    var imageDetails: ImageDetailsRes? = null
    var userDetails: UnsplashUser? = null



    suspend fun getSpecificImage(id: String): ImageDetailsRes?{
        imageDetails = unsplashApi.getSpecificImage(id)
        return imageDetails
    }

    suspend fun getUserDetails(username: String): UnsplashUser?{

        userDetails = unsplashApi.getUserByUsername(username)
        return userDetails

    }

}