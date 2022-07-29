package com.example.pic.data.local.dao

import android.net.Uri
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
    fun getUser(email: String): User?

    @Query("SELECT * FROM User WHERE id LIKE :id")
    fun getUserById(id: Int): User?

    @Query("UPDATE User SET firstName = :firstName WHERE id=:id")
    fun updateFirstName(firstName: String, id: Int)

    @Query("UPDATE User SET lastName = :lastName WHERE id=:id")
    fun updateLastName(lastName: String, id: Int)

    @Query("UPDATE User SET email = :email WHERE id=:id")
    fun updateEmail(email: String, id: Int)

    @Query("UPDATE User SET password = :password WHERE id=:id")
    fun updatePassword(password: String, id: Int)

    @Query("UPDATE User SET profile = :imageUri WHERE id=:id")
    fun updateProfile(imageUri: String, id: Int)


}