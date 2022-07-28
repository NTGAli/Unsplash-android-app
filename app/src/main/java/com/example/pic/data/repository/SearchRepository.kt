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
    private var liveData: MutableLiveData<List<Feed>?> = MutableLiveData()
    private var searchImageLiveData: MutableLiveData<ResultImage?> = MutableLiveData()
    private var searchUsersLiveData: MutableLiveData<ResultUser?> = MutableLiveData()

    fun getSomeImages(): MutableLiveData<List<Feed>?>{
        val call: Call<List<Feed>> = unsplashApi.getAllImagesByOrder(1,10,"popular")
        call.enqueue(object : Callback<List<Feed>>{
            override fun onResponse(call: Call<List<Feed>>, response: Response<List<Feed>>) {
                liveData.postValue(response.body())
            }

            override fun onFailure(call: Call<List<Feed>>, t: Throwable) {
                liveData.postValue(null)
            }

        })
        return liveData
    }


    fun searchInImages(query: String): MutableLiveData<ResultImage?>{
        val call: Call<ResultImage> = unsplashApi.searchInImages(query)
        call.enqueue(object: Callback<ResultImage>{
            override fun onResponse(call: Call<ResultImage>, response: Response<ResultImage>) {
                searchImageLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<ResultImage>, t: Throwable) {
                searchImageLiveData.postValue(null)
            }

        })

        return searchImageLiveData
    }


    fun searchInUsers(query: String): MutableLiveData<ResultUser?>{
        val call: Call<ResultUser> = unsplashApi.searchInUsers(query)
        call.enqueue(object : Callback<ResultUser>{
            override fun onResponse(call: Call<ResultUser>, response: Response<ResultUser>) {
                searchUsersLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<ResultUser>, t: Throwable) {
                searchUsersLiveData.postValue(null)
            }

        })

        return searchUsersLiveData
    }

}