package com.example.pic.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.pic.data.local.UserDB
import com.example.pic.data.local.dao.UserLoginDao
import com.example.pic.util.Constants.LOGIN_DB
import com.example.pic.util.Constants.UNSPLASH_NAME_DB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {



    @Singleton
    @Provides
    fun getAppDB(context: Application): UserDB {
        return Room.databaseBuilder<UserDB>(
            context,
            UserDB::class.java,
            LOGIN_DB
        )
            .allowMainThreadQueries()
            .build()
    }

    @Singleton
    @Provides
    fun getDao(appDB: UserDB): UserLoginDao{
        return appDB.userDao()
    }



}