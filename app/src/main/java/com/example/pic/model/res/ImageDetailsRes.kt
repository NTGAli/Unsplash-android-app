package com.example.pic.model.res

data class ImageDetailsRes(
    val id: String,
    var created_at: String,
    val downloads: String,
    val likes: Int,
    val exif: CameraModelRes,
    val user: UnsplashUser,
    val urls: LinkRes
)
