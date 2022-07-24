package com.example.pic.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Feed (
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val urls: Link,
    )