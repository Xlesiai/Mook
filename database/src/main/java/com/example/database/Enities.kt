package com.example.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books", primaryKeys = ["title", "author"])
data class Book(
    val title: String,
    val author: String,
    val filePath: String = "",
    val narrator: String = "",
    val collection: String = "",
    val genre: String = "",
    val coverImage: String = "",
    val duration: Long = 0,
    val lastPlayed: Long = 0,
    val progress: Float = 0f,
    val rating: Float = 0f
)
