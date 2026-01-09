package com.example.mook.data.local.database.daos

import androidx.room.*
import com.example.mook.data.local.database.entities.VoiceModelEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VoiceModelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(voiceModel: VoiceModelEntity): Long

    @Update
    suspend fun update(voiceModel: VoiceModelEntity)

    @Query("SELECT * FROM voice_models ORDER BY name")
    fun getAll(): Flow<List<VoiceModelEntity>>

    @Query("SELECT * FROM voice_models WHERE id = :id")
    suspend fun getById(id: Long): VoiceModelEntity?

    @Query("SELECT * FROM voice_models WHERE isBuiltIn = 1")
    fun getBuiltInVoices(): Flow<List<VoiceModelEntity>>

    @Query("SELECT * FROM voice_models WHERE isBuiltIn = 0")
    fun getCustomVoices(): Flow<List<VoiceModelEntity>>

    @Query("DELETE FROM voice_models WHERE id = :id")
    suspend fun delete(id: Long)
}