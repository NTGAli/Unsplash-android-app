package com.example.pic.model

data class UnsplashUser(
    val username: String,
    val profile_image: ProfileImg
)

data class ProfileImg(
    val large: String
)
