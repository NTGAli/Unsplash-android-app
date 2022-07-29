package com.example.pic.viewModel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pic.data.local.dao.UserLoginDao
import com.example.pic.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val userLoginDao: UserLoginDao): ViewModel() {

    private var liveData: MutableLiveData<User?> = MutableLiveData()


    fun getUser(email: String): MutableLiveData<User?>{
        liveData.postValue(userLoginDao.getUser(email))
        return liveData
    }

    fun updateFirstName(fistName: String, id: Int){
        userLoginDao.updateFirstName(fistName, id)
        updateData(id)
    }

    fun updateLastName(lastName: String, id: Int){
        userLoginDao.updateLastName(lastName, id)
        updateData(id)
    }

    fun updatePassword(password: String, id: Int){
        userLoginDao.updatePassword(password, id)
        updateData(id)
    }

    fun updateEmail(email: String, id: Int){
        userLoginDao.updateEmail(email, id)
        updateData(id)
    }

    fun updateProfile(imageUri: String?, id: Int?){
        userLoginDao.updateProfile(imageUri!!, id!!)
    }

    private fun updateData(id: Int){
        liveData.postValue(userLoginDao.getUserById(id))
    }



}