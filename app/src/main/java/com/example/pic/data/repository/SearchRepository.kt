package com.example.pic.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.pic.model.Feed
import com.example.pic.network.UnsplashApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class SearchRepository @Inject constructor(private val unsplashApi: UnsplashApi) {
    private var liveData: MutableLiveData<List<Feed>?> = MutableLiveData()

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

}