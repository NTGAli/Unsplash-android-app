package com.example.pic.viewModel

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
class LoginViewModel @Inject constructor(private val userLoginDao: UserLoginDao): ViewModel() {

    var users: MutableLiveData<List<User>> = MutableLiveData()
    private val allRecords: List<User> = userLoginDao.getAllUser()

    private fun loadRecords(){
        val list = allRecords
        users.postValue(list)
    }

    fun addUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            userLoginDao.addUser(user)
            loadRecords()
        }
    }

    fun isUserExist(email: String): Boolean{
        val user = userLoginDao.isUserExist(email)
        return !user.email.isEmpty()
    }

}