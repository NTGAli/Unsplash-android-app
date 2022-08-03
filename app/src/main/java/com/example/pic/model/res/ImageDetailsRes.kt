package com.example.pic.model.res

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ImageDetailsRes(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    var created_at: String,
    val downloads: String,
    val likes: Int,
    val exif: CameraModelRes,
    val user: UnsplashUser,
    val urls: LinkRes
)
