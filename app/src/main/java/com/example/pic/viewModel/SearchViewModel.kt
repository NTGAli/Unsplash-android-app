package com.example.pic.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pic.data.repository.SearchRepository
import com.example.pic.model.Feed
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: SearchRepository): ViewModel(){

    fun getSomeImages(): MutableLiveData<List<Feed>?>{
        return repository.getSomeImages()
    }
}