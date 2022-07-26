package com.example.pic.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pic.data.repository.TopicRepository
import com.example.pic.data.repository.UnsplashImageRepository
import com.example.pic.model.Feed
import com.example.pic.model.Preview
import com.example.pic.model.Topic
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TopicViewModel @Inject constructor(private val repository: TopicRepository): ViewModel() {

    fun getTopics(): MutableLiveData<List<Topic>?>{
        return repository.getTopics()
    }

    fun getTopicById(topicID: String): MutableLiveData<Preview?>{
        return repository.getTopicById(topicID)
    }
}