package com.example.pic.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pic.model.User

@Dao
interface UserLoginDao {

    @Query("SELECT * FROM User")
    fun getAllUser(): List<User>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addUser(user: User)

    @Query("DELETE FROM User")
    fun makeEmptyDB()

    @Query("SELECT * FROM User WHERE email LIKE :email")
    fun isUserExist(email: String): User

}