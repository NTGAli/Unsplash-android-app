package com.example.pic.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.pic.data.local.UserDB
import com.example.pic.model.Feed
import com.example.pic.model.ImageDetails
import com.example.pic.model.UnsplashUser
import com.example.pic.network.UnsplashApi
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class UnsplashImageRepository @Inject constructor(private val unsplashApi: UnsplashApi){
    var liveDataList: MutableLiveData<List<Feed>?> = MutableLiveData()
    var imageDetails: ImageDetails? = null
    var userDetails: UnsplashUser? = null



    suspend fun getSpecificImage(id: String): ImageDetails?{
        imageDetails = unsplashApi.getSpecificImage(id)
        return imageDetails
    }

    suspend fun getUserDetails(username: String): UnsplashUser?{

        userDetails = unsplashApi.getUserByUsername(username)
        return userDetails

    }

}