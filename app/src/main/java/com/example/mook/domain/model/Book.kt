package com.example.mook.domain.model

data class Book(
    val id: Long = 0,
    val title: String,
    val author: String = "Unknown",
    val coverImagePath: String? = null,
    val description: String? = null,
    val year: Int? = null,
    val duration: Long = 0,
    val progress: Float = 0f,
    val isTTSGenerated: Boolean = false,
    val lastPlayedAt: Long? = null
)