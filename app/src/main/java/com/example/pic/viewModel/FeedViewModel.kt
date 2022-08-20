package com.example.pic.viewModel

import androidx.lifecycle.*
import androidx.paging.*
import com.example.pic.data.paging.NetworkPagingSource
import com.example.pic.data.remote.*
import com.example.pic.model.res.ImageDetailsRes
import com.example.pic.model.res.UnsplashUser
import com.example.pic.view.custom.safeApiCall
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class FeedViewModel @Inject constructor(
    private val apiService: UnsplashApi
) : ViewModel() {


    private var imageDetailsLiveData: MutableLiveData<NetworkResult<ImageDetailsRes>>? = MutableLiveData()
    private var userResponse: MutableLiveData<NetworkResult<UnsplashUser>> = MutableLiveData()


    fun getSpecificImage(id: String): LiveData<NetworkResult<ImageDetailsRes>>? {
        viewModelScope.launch {
            imageDetailsLiveData = safeApiCall(Dispatchers.IO){
                apiService.getSpecificImage(id)
            } as MutableLiveData<NetworkResult<ImageDetailsRes>>
        }

        return imageDetailsLiveData
    }



    fun getUserByUsername(username: String): MutableLiveData<NetworkResult<UnsplashUser>> {

        viewModelScope.launch {
            userResponse = safeApiCall(Dispatchers.IO){
                apiService.getUserByUsername(username)
            } as MutableLiveData<NetworkResult<UnsplashUser>>
        }
        return userResponse
    }


    fun getImages() =
        Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            pagingSourceFactory = {
                NetworkPagingSource{
                    apiService.getAllImages(it,10)
                }
            }
        ).liveData.cachedIn(viewModelScope)


    fun getUsersPhotos(username: String) =
        Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            pagingSourceFactory = {
                NetworkPagingSource{
                    apiService.getUserPhotosByUsername(username,it,10)
                }
            }
        ).liveData.cachedIn(viewModelScope)




}