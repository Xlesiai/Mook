package com.example.mook.domain.repository

import com.example.mook.data.local.database.entities.VoiceModelEntity
import kotlinx.coroutines.flow.Flow

interface VoiceModelRepository {
    fun getAllVoiceModels(): Flow<List<VoiceModelEntity>>
    suspend fun getVoiceModelById(id: Long): VoiceModelEntity?
    fun getBuiltInVoices(): Flow<List<VoiceModelEntity>>
    fun getCustomVoices(): Flow<List<VoiceModelEntity>>
    suspend fun insertVoiceModel(voiceModel: VoiceModelEntity): Long
    suspend fun updateVoiceModel(voiceModel: VoiceModelEntity)
    suspend fun deleteVoiceModel(id: Long)
    suspend fun importVoiceModel(modelFileUri: String): VoiceModelEntity
}