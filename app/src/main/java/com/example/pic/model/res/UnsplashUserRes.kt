package com.example.pic.model.res

import androidx.room.Entity

@Entity
data class UnsplashUser(
    val username: String,
    val name: String,
    val profile_image: ProfileImgRes,
    val bio: String
    )

data class ResultUser(
    val results: List<UnsplashUser>
)



