package com.example.mook.di

import android.content.Context
import androidx.room.Room
import com.example.mook.data.local.database.AppDatabase
import com.example.mook.data.local.database.daos.AudioVersionDao
import com.example.mook.data.local.database.daos.BookDao
import com.example.mook.data.local.database.daos.VoiceModelDao
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
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "audiobook_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideBookDao(database: AppDatabase): BookDao {
        return database.bookDao()
    }

    @Provides
    fun provideAudioVersionDao(database: AppDatabase): AudioVersionDao {
        return database.audioVersionDao()
    }

    @Provides
    fun provideVoiceModelDao(database: AppDatabase): VoiceModelDao {
        return database.voiceModelDao()
    }
}