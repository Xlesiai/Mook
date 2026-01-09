// com.example.mook.data.local.datasource.SettingsDataSourceImpl.kt
package com.example.mook.data.local.datasource

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Singleton
class SettingsDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : SettingsDataSource {

    private object PreferencesKeys {
        val LIBRARY_FOLDER_URI = stringPreferencesKey("library_folder_uri")
    }

    override suspend fun saveLibraryFolderUri(uriString: String) {
        // ✅ Use the 'edit' extension function
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.LIBRARY_FOLDER_URI] = uriString
        }
    }

    override suspend fun getLibraryFolderUri(): String? {
        return context.dataStore.data
            .map { preferences -> preferences[PreferencesKeys.LIBRARY_FOLDER_URI] }
            .first()
    }

    override suspend fun clearLibraryFolderUri() {
        context.dataStore.edit { preferences ->
            preferences.remove(PreferencesKeys.LIBRARY_FOLDER_URI)
        }
    }
}