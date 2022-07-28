package com.example.pic.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pic.data.repository.SearchRepository
import com.example.pic.model.Feed
import com.example.pic.model.ResultImage
import com.example.pic.model.ResultUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: SearchRepository): ViewModel(){

    fun getSomeImages(): MutableLiveData<List<Feed>?>{
        return repository.getSomeImages()
    }

    fun searchInImages(query: String): MutableLiveData<ResultImage?>{
        return repository.searchInImages(query)
    }

    fun searchInUsers(query: String): MutableLiveData<ResultUser?>{
        return repository.searchInUsers(query)
    }
}