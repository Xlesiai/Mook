// com.example.mook.data.local.repository.VoiceModelRepositoryImpl.kt
package com.example.mook.data.local.repository

import com.example.mook.data.local.database.daos.VoiceModelDao
import com.example.mook.data.local.database.entities.VoiceModelEntity
import com.example.mook.domain.repository.VoiceModelRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class VoiceModelRepositoryImpl @Inject constructor(
    private val voiceModelDao: VoiceModelDao
) : VoiceModelRepository {

    override fun getAllVoiceModels(): Flow<List<VoiceModelEntity>> {
        return voiceModelDao.getAll()
    }

    override suspend fun getVoiceModelById(id: Long): VoiceModelEntity? {
        return voiceModelDao.getById(id)
    }

    override fun getBuiltInVoices(): Flow<List<VoiceModelEntity>> {
        return voiceModelDao.getBuiltInVoices()
    }

    override fun getCustomVoices(): Flow<List<VoiceModelEntity>> {
        return voiceModelDao.getCustomVoices()
    }

    override suspend fun insertVoiceModel(voiceModel: VoiceModelEntity): Long {
        return voiceModelDao.insert(voiceModel)
    }

    override suspend fun updateVoiceModel(voiceModel: VoiceModelEntity) {
        voiceModelDao.update(voiceModel)
    }

    override suspend fun deleteVoiceModel(id: Long) {
        voiceModelDao.delete(id)
    }

    override suspend fun importVoiceModel(modelFileUri: String): VoiceModelEntity {
        // TODO: Implement actual model import logic
        // For now, create a placeholder voice model
        val voiceModel = VoiceModelEntity(
            name = "Custom Voice",
            displayName = "Imported Voice",
            engineType = VoiceModelEntity.EngineType.ONNX,
            modelPath = modelFileUri,
            language = "en-US",
            isDownloaded = true,
            isBuiltIn = false,
            fileSize = 0 // Would calculate from actual file
        )

        val id = voiceModelDao.insert(voiceModel)
        return voiceModel.copy(id = id)
    }
}