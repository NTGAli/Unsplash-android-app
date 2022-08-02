package com.example.pic.viewModel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pic.data.local.dao.UserLoginDao
import com.example.pic.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val userLoginDao: UserLoginDao): ViewModel() {

    private var liveData: MutableLiveData<User?> = MutableLiveData()


    fun getUser(email: String): MutableLiveData<User?>{
        liveData.postValue(userLoginDao.getUser(email))
        return liveData
    }

    fun updateFirstName(fistName: String, id: Int){
        viewModelScope.launch(Dispatchers.IO){
            userLoginDao.updateFirstName(fistName, id)
            updateData(id)
        }

    }

    fun updateLastName(lastName: String, id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            userLoginDao.updateLastName(lastName, id)
            updateData(id)
        }

    }

    fun updatePassword(password: String, id: Int){
        viewModelScope.launch(Dispatchers.IO){
            userLoginDao.updatePassword(password, id)
            updateData(id)
        }
    }

    fun updateEmail(email: String, id: Int){
        viewModelScope.launch(Dispatchers.IO){
            userLoginDao.updateEmail(email, id)
            updateData(id)
        }
    }

    fun updateProfile(imageUri: String?, id: Int?){
        viewModelScope.launch(Dispatchers.IO){
            userLoginDao.updateProfile(imageUri!!, id!!)
        }
    }

    private fun updateData(id: Int){
        liveData.postValue(userLoginDao.getUserById(id))
    }



}