package com.example.mook.di

import com.example.mook.data.local.repository.BookRepositoryImpl
import com.example.mook.data.local.repository.VoiceModelRepositoryImpl
import com.example.mook.domain.repository.BookRepository
import com.example.mook.domain.repository.VoiceModelRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindBookRepository(
        bookRepositoryImpl: BookRepositoryImpl
    ): BookRepository

    // Add more bindings as you create implementations
    @Binds
    @Singleton
    abstract fun bindVoiceModelRepository(
        voiceModelRepositoryImpl: VoiceModelRepositoryImpl
    ): VoiceModelRepository
}