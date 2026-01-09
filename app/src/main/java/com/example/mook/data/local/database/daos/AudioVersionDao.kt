package com.example.mook.data.local.database.daos

import androidx.room.*
import com.example.mook.data.local.database.entities.AudioVersionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AudioVersionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(audioVersion: AudioVersionEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(audioVersions: List<AudioVersionEntity>)

    @Update
    suspend fun update(audioVersion: AudioVersionEntity)

    @Query("DELETE FROM audio_versions WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("DELETE FROM audio_versions WHERE bookId = :bookId")
    suspend fun deleteByBookId(bookId: Long)

    // Get all audio versions for a specific book
    @Query("SELECT * FROM audio_versions WHERE bookId = :bookId ORDER BY isDefault DESC, createdAt DESC")
    fun getByBookId(bookId: Long): Flow<List<AudioVersionEntity>>

    // Get a specific audio version
    @Query("SELECT * FROM audio_versions WHERE id = :id")
    suspend fun getById(id: Long): AudioVersionEntity?

    // Get the default audio version for a book
    @Query("SELECT * FROM audio_versions WHERE bookId = :bookId AND isDefault = 1 LIMIT 1")
    suspend fun getDefaultByBookId(bookId: Long): AudioVersionEntity?

    // Set an audio version as default (and unset others for the same book)
    @Transaction
    suspend fun setAsDefault(audioVersionId: Long, bookId: Long) {
        // First, unset default for all versions of this book
        clearDefaultForBook(bookId)
        // Then, set the specified version as default
        setDefaultFlag(audioVersionId, true)
    }

    @Query("UPDATE audio_versions SET isDefault = 0 WHERE bookId = :bookId")
    suspend fun clearDefaultForBook(bookId: Long)

    @Query("UPDATE audio_versions SET isDefault = :isDefault WHERE id = :id")
    suspend fun setDefaultFlag(id: Long, isDefault: Boolean)

    // For TTS functionality: get versions generated with a specific voice model
    @Query("SELECT * FROM audio_versions WHERE voiceModelId = :voiceModelId")
    fun getByVoiceModelId(voiceModelId: Long): Flow<List<AudioVersionEntity>>

    // Get all TTS-generated audio versions
    @Query("SELECT * FROM audio_versions WHERE sourceType = 'TTS_GENERATED'")
    fun getAllTtsGenerated(): Flow<List<AudioVersionEntity>>

    // Update file metadata (useful after generation completes)
    @Query("UPDATE audio_versions SET duration = :duration, fileSize = :fileSize WHERE id = :id")
    suspend fun updateMetadata(id: Long, duration: Long, fileSize: Long)
}