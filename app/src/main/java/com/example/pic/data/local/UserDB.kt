package com.example.pic.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pic.data.local.dao.UserLoginDao
import com.example.pic.model.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDB: RoomDatabase() {

    abstract fun userDao(): UserLoginDao



}