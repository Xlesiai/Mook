package com.example.mook.data.local.database.entities


import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.mook.data.local.database.Converters

@Entity(
    tableName = "audio_versions",
    foreignKeys = [
        ForeignKey(
            entity = BookEntity::class,
            parentColumns = ["id"],
            childColumns = ["bookId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("bookId", "isDefault")]
)
@TypeConverters(Converters::class)
data class AudioVersionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val bookId: Long,
    val title: String,
    val audioFilePath: String,
    val sourceType: SourceType = SourceType.PRE_RECORDED,
    val voiceModelId: Long? = null,
    val duration: Long = 0,
    val fileSize: Long = 0,
    val isDefault: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
) {
    enum class SourceType {
        PRE_RECORDED, TTS_GENERATED, USER_UPLOADED
    }
}