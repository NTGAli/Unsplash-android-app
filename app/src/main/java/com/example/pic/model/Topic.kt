package com.example.pic.model

data class Topic(
    val id: String,
    val slug: String,
    val title: String,
    val description: String,
    val cover_photo: Url
)

data class Url(
    val urls: Link
)
