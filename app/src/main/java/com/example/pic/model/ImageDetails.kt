package com.example.pic.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ImageDetails(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    var created_at: String,
    val downloads: String,
    val likes: Int,
    val exif: CameraModel,
    val user: UnsplashUser,
    val urls: Link
)
