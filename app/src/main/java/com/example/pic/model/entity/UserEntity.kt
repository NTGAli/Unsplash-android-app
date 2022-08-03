package com.example.pic.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val email: String,
    val password: String,
    val firstName: String?,
    val lastName: String?,
    val profile: String?
)
