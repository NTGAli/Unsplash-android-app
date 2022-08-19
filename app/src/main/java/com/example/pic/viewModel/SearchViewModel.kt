package com.example.pic.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.example.pic.data.paging.PhotoPagingSource
import com.example.pic.data.paging.SearchPagingSource
import com.example.pic.data.remote.NetworkResult
import com.example.pic.data.remote.UnsplashApi
import com.example.pic.model.res.ResultUser
import com.example.pic.model.res.UnsplashUser
import com.example.pic.view.custom.safeApiCall
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val apiService: UnsplashApi): ViewModel(){

    private var usersSearch: MutableLiveData<List<UnsplashUser>?> = MutableLiveData()
    private var query: MutableLiveData<String> = MutableLiveData()
    private var searchUserResponse: MutableLiveData<NetworkResult<ResultUser>> = MutableLiveData()

    fun getSomeImages() =
        Pager(
            config = PagingConfig(
                pageSize = 10,
            ),
            pagingSourceFactory = { PhotoPagingSource(apiService) }
        ).liveData.cachedIn(viewModelScope)

    fun searchInImages(userQuery: String){
        query.postValue(userQuery)
    }

    fun searchInUsers(query: String): MutableLiveData<NetworkResult<ResultUser>>{
        viewModelScope.launch{

            searchUserResponse = safeApiCall(Dispatchers.IO){
                apiService.searchInUsers(query)
            } as MutableLiveData<NetworkResult<ResultUser>>
        }
        return searchUserResponse
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