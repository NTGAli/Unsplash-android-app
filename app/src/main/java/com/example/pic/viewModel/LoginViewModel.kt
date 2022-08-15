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
    var userEmail: MutableLiveData<String>? = MutableLiveData()
    var userEntity: LiveData<UserEntity?> = MutableLiveData()
    private val allRecords: LiveData<List<UserEntity>> = userLoginDao.getAllUser()

    private fun loadRecords(){
        users = allRecords
    }

    fun addUser(user: UserEntity) {
        viewModelScope.launch(Dispatchers.IO) {

            if (userLoginDao.getUser(user.email).value == null){
                userLoginDao.addUser(user)
                loadRecords()
            }
        }
    }


    fun submitUser(email: String){
        userEmail?.postValue(email)
        getUser(email)
    }

    fun getUser(email: String): LiveData<UserEntity?>{
        userEntity = userLoginDao.getUser(email)
        return userEntity
    }

    fun isUserExist(email: String): LiveData<Boolean>{
        return userLoginDao.isUserExist(email)
    }

}