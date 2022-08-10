package com.example.pic.model.res

import androidx.room.Entity
import androidx.room.PrimaryKey

data class ImageDetailsRes(
    val id: String,
    var created_at: String,
    val downloads: String,
    val likes: Int,
    val exif: CameraModelRes,
    val user: UnsplashUser,
    val urls: LinkRes
)
