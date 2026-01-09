// com.example.mook.data.local.repository.InitialVoiceSetup.kt
package com.example.mook.data.local.repository

import android.content.Context
import android.speech.tts.TextToSpeech
import com.example.mook.data.local.database.daos.VoiceModelDao
import com.example.mook.data.local.database.entities.VoiceModelEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

class InitialVoiceSetup @Inject constructor(
    private val voiceModelDao: VoiceModelDao,
    private val context: Context
) {

    fun initializeBuiltInVoices(scope: CoroutineScope) {
        scope.launch {
            // Check if we already have voices in the database
            val existingVoices = voiceModelDao.getAll().firstOrNull()
            if (existingVoices.isNullOrEmpty()) {
                // Add default Android TTS voices
                addDefaultAndroidVoices()
            }
        }
    }

    private suspend fun addDefaultAndroidVoices() {
        val defaultVoices = listOf(
            VoiceModelEntity(
                name = "en-US-default",
                displayName = "English (US) - Default",
                engineType = VoiceModelEntity.EngineType.ANDROID_TTS,
                language = "en-US",
                gender = VoiceModelEntity.Gender.UNSPECIFIED,
                isBuiltIn = true,
                isEnabled = true
            ),
            VoiceModelEntity(
                name = "en-GB-default",
                displayName = "English (UK) - Default",
                engineType = VoiceModelEntity.EngineType.ANDROID_TTS,
                language = "en-GB",
                gender = VoiceModelEntity.Gender.UNSPECIFIED,
                isBuiltIn = true,
                isEnabled = true
            ),
            // Add more default voices as needed
            VoiceModelEntity(
                name = "default-male",
                displayName = "Male Voice",
                engineType = VoiceModelEntity.EngineType.ANDROID_TTS,
                language = "en-US",
                gender = VoiceModelEntity.Gender.MALE,
                isBuiltIn = true,
                isEnabled = true
            ),
            VoiceModelEntity(
                name = "default-female",
                displayName = "Female Voice",
                engineType = VoiceModelEntity.EngineType.ANDROID_TTS,
                language = "en-US",
                gender = VoiceModelEntity.Gender.FEMALE,
                isBuiltIn = true,
                isEnabled = true
            )
        )

        defaultVoices.forEach { voiceModelDao.insert(it) }
    }
}