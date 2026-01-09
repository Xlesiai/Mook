package com.example.mook.data.local.database.entities


import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.mook.data.local.database.Converters

@Entity(tableName = "books")
@TypeConverters(Converters::class)
data class BookEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val author: String = "Unknown",
    val year: Int? = null,
    val folderPath: String,
    val coverImagePath: String? = null,
    val description: String? = null,
    val fileFormat: FileFormat = FileFormat.M4B,
    val createdAt: Long = System.currentTimeMillis(),
    val lastAccessedAt: Long = System.currentTimeMillis(),
    val totalDuration: Long = 0,
    val isTTSGenerated: Boolean = false
) {
    enum class FileFormat {
        M4B, EPUB, PDF, TXT, FOLDER
    }
}