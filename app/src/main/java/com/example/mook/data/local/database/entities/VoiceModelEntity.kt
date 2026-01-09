// com.example.mook.data.local.database.entities.VoiceModelEntity.kt
package com.example.mook.data.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.mook.data.local.database.Converters

@Entity(tableName = "voice_models")
@TypeConverters(Converters::class)
data class VoiceModelEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val displayName: String,
    val engineType: EngineType = EngineType.ANDROID_TTS,
    val modelPath: String? = null, // For custom models (.onnx, .zip files)
    val language: String = "en-US",
    val gender: Gender = Gender.UNSPECIFIED,
    val speed: Float = 1.0f,
    val pitch: Float = 1.0f,
    val isDownloaded: Boolean = false,
    val isBuiltIn: Boolean = false,
    val androidVoiceId: String? = null, // For Android TTS built-in voices
    val fileSize: Long = 0,
    val isEnabled: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
) {
    enum class EngineType {
        ANDROID_TTS, ONNX, COQUI, PIPER, ESPEAK
    }

    enum class Gender {
        MALE, FEMALE, UNSPECIFIED
    }
}