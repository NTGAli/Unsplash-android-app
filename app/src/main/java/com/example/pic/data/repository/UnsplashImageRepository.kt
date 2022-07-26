package com.example.pic.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.pic.model.Feed
import com.example.pic.model.ImageDetails
import com.example.pic.model.UnsplashUser
import com.example.pic.network.UnsplashApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class UnsplashImageRepository @Inject constructor(private val unsplashApi: UnsplashApi){
    var liveDataList: MutableLiveData<List<Feed>?> = MutableLiveData()
    var liveDataImageDetails: MutableLiveData<ImageDetails?> = MutableLiveData()
    var liveDataUserDetails: MutableLiveData<UnsplashUser?> = MutableLiveData()

    fun getImages(page: Int): MutableLiveData<List<Feed>?> {
        val call: Call<List<Feed>> = unsplashApi.getAllImages(page, 10)
        call.enqueue(object: Callback<List<Feed>>{
            override fun onResponse(call: Call<List<Feed>>, response: Response<List<Feed>>) {
                liveDataList.postValue(response.body())
            }

            override fun onFailure(call: Call<List<Feed>>, t: Throwable) {
                liveDataList.postValue(null)
            }

        })

        return liveDataList
    }

    fun getSpecificImage(id: String): MutableLiveData<ImageDetails?>{
        val call: Call<ImageDetails> = unsplashApi.getSpecificImage(id)
        call.enqueue(object : Callback<ImageDetails>{
            override fun onResponse(call: Call<ImageDetails>, response: Response<ImageDetails>) {
                liveDataImageDetails.postValue(response.body())
            }

            override fun onFailure(call: Call<ImageDetails>, t: Throwable) {
                liveDataImageDetails.postValue(null)
            }

        })

        return liveDataImageDetails
    }

    fun getUserDetails(username: String): MutableLiveData<UnsplashUser?>{
        val call: Call<UnsplashUser> = unsplashApi.getUserByUsername(username)
        call.enqueue(object: Callback<UnsplashUser>{
            override fun onResponse(call: Call<UnsplashUser>, response: Response<UnsplashUser>) {

                liveDataUserDetails.postValue(response.body())
            }

            override fun onFailure(call: Call<UnsplashUser>, t: Throwable) {
                liveDataUserDetails.postValue(null)
            }

        })

        return liveDataUserDetails
    }







}