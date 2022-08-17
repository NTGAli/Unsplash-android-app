package com.example.pic.di

import android.app.Application
import androidx.room.Room
import com.example.pic.data.local.UserDB
import com.example.pic.data.local.dao.UserLoginDao
import com.example.pic.util.Constants.LOGIN_DB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {



    @Singleton
    @Provides
    fun getAppDB(context: Application): UserDB {
        return Room.databaseBuilder(
            context,
            UserDB::class.java,
            LOGIN_DB
        )
            .build()
    }

    @Singleton
    @Provides
    fun getDao(appDB: UserDB): UserLoginDao{
        return appDB.userDao()
    }



}