package com.example.pic.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pic.data.repository.TopicRepository
import com.example.pic.data.repository.UnsplashImageRepository
import com.example.pic.model.Feed
import com.example.pic.model.Preview
import com.example.pic.model.Topic
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopicViewModel @Inject constructor(private val repository: TopicRepository): ViewModel() {

    private val liveDataTopics: MutableLiveData<List<Topic>?> = MutableLiveData()
    private val liveDataPreview: MutableLiveData<Preview?> = MutableLiveData()

    fun getTopics(): MutableLiveData<List<Topic>?>{
        viewModelScope.launch(Dispatchers.IO) {
            liveDataTopics.postValue(repository.getTopics())
        }

        return liveDataTopics
    }

    fun getTopicById(topicID: String): MutableLiveData<Preview?>{
        viewModelScope.launch(Dispatchers.IO){
            liveDataPreview.postValue(repository.getTopicById(topicID))
        }
        return liveDataPreview
    }
}