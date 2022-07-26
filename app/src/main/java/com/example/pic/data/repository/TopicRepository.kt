package com.example.pic.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.pic.model.Feed
import com.example.pic.model.Preview
import com.example.pic.model.Topic
import com.example.pic.network.UnsplashApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class TopicRepository @Inject constructor(private val unsplashApi: UnsplashApi) {

    val liveData: MutableLiveData<List<Topic>?> = MutableLiveData()
    val feedLiveData: MutableLiveData<Preview?> = MutableLiveData()

    fun getTopics(): MutableLiveData<List<Topic>?>{
        val call: Call<List<Topic>> = unsplashApi.getTopics()
        call.enqueue(object: Callback<List<Topic>>{
            override fun onResponse(call: Call<List<Topic>>, response: Response<List<Topic>>) {
                liveData.postValue(response.body())
            }

            override fun onFailure(call: Call<List<Topic>>, t: Throwable) {
                liveData.postValue(null)
            }

        })

        return liveData
    }


    fun getTopicById(topicID: String): MutableLiveData<Preview?>{
        val call: Call<Preview> = unsplashApi.getTopicById(topicID)
        call.enqueue(object: Callback<Preview>{
            override fun onResponse(call: Call<Preview>, response: Response<Preview>) {
                feedLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<Preview>, t: Throwable) {
                feedLiveData.postValue(null)
            }

        })

        return feedLiveData
    }
}