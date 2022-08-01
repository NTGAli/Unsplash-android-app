package com.example.pic.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.pic.data.paging.PhotoPagingSource
import com.example.pic.data.repository.UnsplashImageRepository
import com.example.pic.model.Feed
import com.example.pic.model.ImageDetails
import com.example.pic.model.UnsplashUser
import com.example.pic.model.User
import com.example.pic.network.UnsplashApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class FeedViewModel @Inject constructor(
    private val repository: UnsplashImageRepository,
    private val apiService: UnsplashApi
    ): ViewModel() {


    private val imageDetails: MutableLiveData<ImageDetails?> = MutableLiveData()
    private val userViewModel: MutableLiveData<UnsplashUser?> = MutableLiveData()

    fun getSpecificImage(id: String): MutableLiveData<ImageDetails?>{
        viewModelScope.launch(Dispatchers.IO){
            imageDetails.postValue(repository.getSpecificImage(id))
        }
        return imageDetails
    }

    fun getUserByUsername(username: String): MutableLiveData<UnsplashUser?>{
        viewModelScope.launch(Dispatchers.IO) {
            userViewModel.postValue(repository.getUserDetails(username))
        }
        return userViewModel
    }

    fun getImages() =
        Pager(
            config = PagingConfig(
                pageSize = 10,
            ),
            pagingSourceFactory = { PhotoPagingSource(apiService) }
        ).liveData.cachedIn(viewModelScope)
}