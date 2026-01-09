// com.example.mook.di.DataSourceModule.kt
package com.example.mook.di

import com.example.mook.data.local.datasource.SettingsDataSource
import com.example.mook.data.local.datasource.SettingsDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindSettingsDataSource(
        settingsDataSourceImpl: SettingsDataSourceImpl
    ): SettingsDataSource
}