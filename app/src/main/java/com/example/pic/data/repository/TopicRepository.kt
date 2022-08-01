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

    var topics: List<Topic>? = listOf()
    var preview: Preview? = null

    suspend fun getTopics():List<Topic>?{
        topics = unsplashApi.getTopics()
        return topics
    }


    suspend fun getTopicById(topicID: String): Preview?{

        preview = unsplashApi.getTopicById(topicID)
        return preview
    }


}