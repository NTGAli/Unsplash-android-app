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

//    val getAllPage = repository.getImages()

//    fun getPage(page: Int): MutableLiveData<List<Feed>?> {
//        return repository.getImages(page)
//    }

    fun getSpecificImage(id: String): MutableLiveData<ImageDetails?>{
        return repository.getSpecificImage(id)
    }

    fun getUserByUsername(username: String): MutableLiveData<UnsplashUser?>{
        return repository.getUserDetails(username)
    }

    fun getImages() =
        Pager(
            config = PagingConfig(
                pageSize = 10,
            ),
            pagingSourceFactory = { PhotoPagingSource(apiService) }
        ).liveData.cachedIn(viewModelScope)
}