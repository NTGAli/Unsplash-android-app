package com.example.pic.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pic.data.repository.SearchRepository
import com.example.pic.model.Feed
import com.example.pic.model.ResultImage
import com.example.pic.model.ResultUser
import com.example.pic.model.UnsplashUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: SearchRepository): ViewModel(){

    private var imagesSearch: MutableLiveData<List<Feed>?> = MutableLiveData()
    private var usersSearch: MutableLiveData<List<UnsplashUser>?> = MutableLiveData()

    fun getSomeImages(): MutableLiveData<List<Feed>?>{
        return repository.getSomeImages()
    }

    fun searchInImages(query: String): MutableLiveData<ResultImage?>{
        return repository.searchInImages(query)
    }

    fun searchInUsers(query: String): MutableLiveData<ResultUser?>{
        return repository.searchInUsers(query)
    }

    fun setImageList(feeds: List<Feed>?){
        imagesSearch.postValue(feeds)
    }

    fun getListOfImagesSearched(): MutableLiveData<List<Feed>?>{
     return imagesSearch
    }

    fun setUsersList(users: List<UnsplashUser>?){
        usersSearch.postValue(users)
    }

    fun getListOfUsersSearched(): MutableLiveData<List<UnsplashUser>?>{
        return usersSearch
    }
}