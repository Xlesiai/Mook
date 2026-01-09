// com.example.mook.data.local.datasource.SettingsDataSource.kt
package com.example.mook.data.local.datasource

interface SettingsDataSource {
    suspend fun saveLibraryFolderUri(uriString: String)
    suspend fun getLibraryFolderUri(): String?
    suspend fun clearLibraryFolderUri()
}