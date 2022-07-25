package com.example.pic.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pic.data.repository.UnsplashImageRepository
import com.example.pic.model.Feed
import com.example.pic.model.ImageDetails
import com.example.pic.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(private val repository: UnsplashImageRepository): ViewModel() {

    fun getPage(page: Int): MutableLiveData<List<Feed>?> {
        return repository.getImages(page)
    }

    fun getSpecificImage(id: String): MutableLiveData<ImageDetails?>{
        return repository.getSpecificImage(id)
    }

}