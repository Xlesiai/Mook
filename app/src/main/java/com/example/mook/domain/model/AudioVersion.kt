package com.example.mook.domain.model

data class AudioVersion(
    val id: Long = 0,
    val bookId: Long,
    val title: String,
    val audioFilePath: String,
    val sourceType: SourceType = SourceType.PRE_RECORDED,
    val voiceModelId: Long? = null,
    val duration: Long = 0,
    val isDefault: Boolean = false
) {
    enum class SourceType {
        PRE_RECORDED, TTS_GENERATED, USER_UPLOADED
    }
}