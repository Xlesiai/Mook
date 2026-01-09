package com.example.mook.domain.repository

import com.example.mook.data.local.database.entities.AudioVersionEntity
import kotlinx.coroutines.flow.Flow

interface AudioVersionRepository {
    fun getAudioVersionsForBook(bookId: Long): Flow<List<AudioVersionEntity>>
    suspend fun getAudioVersionById(id: Long): AudioVersionEntity?
    suspend fun getDefaultAudioVersion(bookId: Long): AudioVersionEntity?
    suspend fun insertAudioVersion(audioVersion: AudioVersionEntity): Long
    suspend fun setAsDefault(audioVersionId: Long, bookId: Long)
    suspend fun deleteAudioVersion(id: Long)
}