package com.example.pic.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.example.pic.data.paging.NetworkPagingSource
import com.example.pic.data.remote.NetworkResult
import com.example.pic.model.res.Topic
import com.example.pic.data.remote.UnsplashApi
import com.example.pic.model.res.PreviewRes
import com.example.pic.view.custom.safeApiCall
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopicViewModel @Inject constructor(private val apiService: UnsplashApi): ViewModel() {

    private var liveDataTopics: MutableLiveData<NetworkResult<List<Topic>>> = MutableLiveData()
    private var previewResponse: MutableLiveData<NetworkResult<PreviewRes>> = MutableLiveData()

    fun getTopics(): MutableLiveData<NetworkResult<List<Topic>>>{
        viewModelScope.launch {
            liveDataTopics = safeApiCall(Dispatchers.IO){
                apiService.getTopics(25)
            } as MutableLiveData<NetworkResult<List<Topic>>>
        }

        return liveDataTopics
    }

    fun getTopicById(topicID: String): MutableLiveData<NetworkResult<PreviewRes>>{
        viewModelScope.launch{
            previewResponse = safeApiCall(Dispatchers.IO){
                apiService.getTopicById(topicID)
            } as MutableLiveData<NetworkResult<PreviewRes>>
        }
        return previewResponse
    }

    fun getPhotosTopic(topicID: String) =
        Pager(
            config = PagingConfig(
                pageSize = 10,
            ),
            pagingSourceFactory = {
                NetworkPagingSource{
                    apiService.getTopicPhotosById(topicID,it,10)
                }
            }
        ).liveData.cachedIn(viewModelScope)
}