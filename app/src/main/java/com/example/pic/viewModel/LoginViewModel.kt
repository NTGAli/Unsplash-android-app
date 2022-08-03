package com.example.pic.viewModel

import androidx.lifecycle.*
import com.example.pic.data.local.dao.UserLoginDao
import com.example.pic.model.entity.UserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userLoginDao: UserLoginDao): ViewModel() {

    var users: LiveData<List<UserEntity>> = MutableLiveData()
    private val allRecords: LiveData<List<UserEntity>> = userLoginDao.getAllUser()

    private fun loadRecords(){
        users = allRecords
    }

    fun addUser(user: UserEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            userLoginDao.addUser(user)
            loadRecords()
        }
    }

    fun getUser(email: String): LiveData<UserEntity?>{
        return userLoginDao.getUser(email)
    }

}