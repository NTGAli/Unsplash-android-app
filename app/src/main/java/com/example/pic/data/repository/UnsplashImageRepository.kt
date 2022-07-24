package com.example.pic.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.pic.model.Feed
import com.example.pic.network.UnsplashApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class UnsplashImageRepository @Inject constructor(private val unsplashApi: UnsplashApi){
    var liveDataList: MutableLiveData<List<Feed>?> = MutableLiveData()

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


}