package com.example.pic.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UnsplashUser(
    val username: String,
    val name: String,
    val profile_image: ProfileImg,
    val bio: String
    )

data class ResultUser(
    val results: List<UnsplashUser>
)



