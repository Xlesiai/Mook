package com.example.mook.di

import android.content.Context
import com.example.mook.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideBookDao(database: AppDatabase) = database.bookDao()

    @Provides
    @Singleton
    fun provideAudioVersionDao(database: AppDatabase) = database.audioVersionDao()
}