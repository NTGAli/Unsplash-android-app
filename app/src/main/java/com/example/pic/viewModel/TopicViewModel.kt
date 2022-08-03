package com.example.pic.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.example.pic.data.paging.TopicsPhotosPaging
import com.example.pic.data.repository.TopicRepository
import com.example.pic.model.res.Topic
import com.example.pic.data.remote.UnsplashApi
import com.example.pic.model.res.PreviewRes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopicViewModel @Inject constructor(private val repository: TopicRepository, private val apiService: UnsplashApi): ViewModel() {

    private val liveDataTopics: MutableLiveData<List<Topic>?> = MutableLiveData()
    private val liveDataPreview: MutableLiveData<PreviewRes?> = MutableLiveData()

    fun getTopics(): MutableLiveData<List<Topic>?>{
        viewModelScope.launch(Dispatchers.IO) {
            liveDataTopics.postValue(repository.getTopics())
        }

        return liveDataTopics
    }

    fun getTopicById(topicID: String): MutableLiveData<PreviewRes?>{
        viewModelScope.launch(Dispatchers.IO){
            liveDataPreview.postValue(repository.getTopicById(topicID))
        }
        return liveDataPreview
    }

    fun getPhotosTopic(topicID: String) =
        Pager(
            config = PagingConfig(
                pageSize = 10,
            ),
            pagingSourceFactory = { TopicsPhotosPaging(apiService, topicID) }
        ).liveData.cachedIn(viewModelScope)
}