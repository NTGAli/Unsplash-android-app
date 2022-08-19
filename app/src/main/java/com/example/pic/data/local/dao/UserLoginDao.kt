package com.example.pic.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pic.model.entity.UserEntity

@Dao
interface UserLoginDao {

    @Query("SELECT * FROM UserEntity")
    fun getAllUser(): LiveData<List<UserEntity>>

    @Insert
    suspend fun addUser(user: UserEntity)

    @Query("DELETE FROM UserEntity")
    fun makeEmptyDB()

    @Query("SELECT * FROM UserEntity WHERE email LIKE :email")
    fun getUser(email: String): LiveData<UserEntity?>

    @Query("SELECT EXISTS(SELECT * FROM UserEntity WHERE email =:email)")
    fun isUserExist(email: String): LiveData<Boolean>

    @Query("SELECT * FROM UserEntity WHERE id LIKE :id")
    fun getUserById(id: Int): LiveData<UserEntity?>

    @Query("UPDATE UserEntity SET firstName = :firstName WHERE id=:id")
    suspend fun updateFirstName(firstName: String, id: Int)

    @Query("UPDATE UserEntity SET lastName = :lastName WHERE id=:id")
    suspend fun updateLastName(lastName: String, id: Int)

    @Query("UPDATE UserEntity SET email = :email WHERE id=:id")
    suspend fun updateEmail(email: String, id: Int)

    @Query("UPDATE UserEntity SET password = :password WHERE id=:id")
    suspend fun updatePassword(password: String, id: Int)

    @Query("UPDATE UserEntity SET profile = :imageUri WHERE id=:id")
    suspend fun updateProfile(imageUri: String, id: Int)


}