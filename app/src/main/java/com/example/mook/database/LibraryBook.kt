package com.example.mook.database

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "Library", primaryKeys = ["title", "author"])
data class LibraryBook(
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "author")
    val author: String,
    val description: String?,
    val cover: String?,
    val text: String?,
    val audio: String?
)
