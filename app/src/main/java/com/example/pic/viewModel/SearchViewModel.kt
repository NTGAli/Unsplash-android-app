package com.example.pic.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.example.pic.data.paging.PhotoPagingSource
import com.example.pic.data.repository.SearchRepository
import com.example.pic.model.Feed
import com.example.pic.model.ResultImage
import com.example.pic.model.ResultUser
import com.example.pic.model.UnsplashUser
import com.example.pic.network.UnsplashApi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: SearchRepository, private val apiService: UnsplashApi): ViewModel(){

    private var imagesSearch: MutableLiveData<List<Feed>?> = MutableLiveData()
    private var usersSearch: MutableLiveData<List<UnsplashUser>?> = MutableLiveData()

    fun getSomeImages() =
        Pager(
            config = PagingConfig(
                pageSize = 10,
            ),
            pagingSourceFactory = { PhotoPagingSource(apiService) }
        ).liveData.cachedIn(viewModelScope)

    fun searchInImages(query: String): MutableLiveData<ResultImage?>{
        return repository.searchInImages(query)
    }

    fun searchInUsers(query: String): MutableLiveData<ResultUser?>{
        return repository.searchInUsers(query)
    }

    fun setImageList(feeds: List<Feed>?){
        imagesSearch.postValue(feeds)
    }

    fun getListOfImagesSearched() =
        Pager(
            config = PagingConfig(
                pageSize = 10,
            ),
            pagingSourceFactory = { PhotoPagingSource(apiService) }
        ).liveData.cachedIn(viewModelScope)

    fun setUsersList(users: List<UnsplashUser>?){
        usersSearch.postValue(users)
    }

    fun getListOfUsersSearched(): MutableLiveData<List<UnsplashUser>?>{
        return usersSearch
    }
}