// com.example.mook.data.local.database.Converters.kt
package com.example.mook.data.local.database

import androidx.room.TypeConverter
import com.example.mook.data.local.database.entities.BookEntity
import com.example.mook.data.local.database.entities.AudioVersionEntity
import com.example.mook.data.local.database.entities.VoiceModelEntity

class Converters {

    @TypeConverter
    fun fromBookFileFormat(value: String): BookEntity.FileFormat {
        return BookEntity.FileFormat.valueOf(value)
    }

    @TypeConverter
    fun toBookFileFormat(format: BookEntity.FileFormat): String {
        return format.name
    }

    @TypeConverter
    fun fromAudioSourceType(value: String): AudioVersionEntity.SourceType {
        return AudioVersionEntity.SourceType.valueOf(value)
    }

    @TypeConverter
    fun toAudioSourceType(type: AudioVersionEntity.SourceType): String {
        return type.name
    }

    // NEW: Add converters for VoiceModelEntity
    @TypeConverter
    fun fromEngineType(value: String): VoiceModelEntity.EngineType {
        return VoiceModelEntity.EngineType.valueOf(value)
    }

    @TypeConverter
    fun toEngineType(type: VoiceModelEntity.EngineType): String {
        return type.name
    }

    @TypeConverter
    fun fromGender(value: String): VoiceModelEntity.Gender {
        return VoiceModelEntity.Gender.valueOf(value)
    }

    @TypeConverter
    fun toGender(gender: VoiceModelEntity.Gender): String {
        return gender.name
    }
}