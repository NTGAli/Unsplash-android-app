package com.example.pic.data.repository

import com.example.pic.model.res.Topic
import com.example.pic.data.remote.UnsplashApi
import com.example.pic.model.res.PreviewRes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

class TopicRepository @Inject constructor(private val unsplashApi: UnsplashApi) {

    var topics: List<Topic>? = listOf()
    var preview: PreviewRes? = null

    suspend fun getTopics():List<Topic>?{
        topics = unsplashApi.getTopics()
        return topics
    }


    suspend fun getTopicById(topicID: String): PreviewRes?{

        preview = unsplashApi.getTopicById(topicID)
        return preview
    }

}