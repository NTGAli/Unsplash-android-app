package com.example.pic.viewModel

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.example.pic.data.paging.PhotoPagingSource
import com.example.pic.data.paging.SearchPagingSource
import com.example.pic.data.repository.SearchRepository
import com.example.pic.model.Feed
import com.example.pic.model.ResultImage
import com.example.pic.model.ResultUser
import com.example.pic.model.UnsplashUser
import com.example.pic.network.UnsplashApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: SearchRepository, private val apiService: UnsplashApi): ViewModel(){

    private var imagesSearch: MutableLiveData<List<Feed>?> = MutableLiveData()
    private var usersSearch: MutableLiveData<List<UnsplashUser>?> = MutableLiveData()
    private var query: MutableLiveData<String> = MutableLiveData()
    private val searchedUser: MutableLiveData<ResultUser?> = MutableLiveData()

    fun getSomeImages() =
        Pager(
            config = PagingConfig(
                pageSize = 10,
            ),
            pagingSourceFactory = { PhotoPagingSource(apiService) }
        ).liveData.cachedIn(viewModelScope)

    fun searchInImages(userQuery: String){
        query.postValue(userQuery)
//        return repository.searchInImages(query)
    }

    fun searchInUsers(query: String): MutableLiveData<ResultUser?>{
        viewModelScope.launch(Dispatchers.IO){
            searchedUser.postValue(repository.searchInUsers(query))
        }
        return searchedUser
    }

    fun setImageList(feeds: List<Feed>?){
        imagesSearch.postValue(feeds)
    }

    fun getQuery(): MutableLiveData<String>{
        return query
    }

    fun getListOfImagesSearched() =
        Pager(
            config = PagingConfig(
                pageSize = 10,
            ),
            pagingSourceFactory = { SearchPagingSource(apiService, query.value.toString()) }
        ).liveData.cachedIn(viewModelScope)


    fun setUsersList(users: List<UnsplashUser>?){

        usersSearch.postValue(users)
    }

    fun getListOfUsersSearched(): MutableLiveData<List<UnsplashUser>?>{
        return usersSearch
    }
}