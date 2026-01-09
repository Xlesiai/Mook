package com.example.mook.data.local.database


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mook.data.local.database.daos.AudioVersionDao
import com.example.mook.data.local.database.daos.BookDao
import com.example.mook.data.local.database.daos.VoiceModelDao
import com.example.mook.data.local.database.entities.AudioVersionEntity
import com.example.mook.data.local.database.entities.BookEntity
import com.example.mook.data.local.database.entities.VoiceModelEntity

@Database(
    entities = [
        BookEntity::class,
        AudioVersionEntity::class,
        VoiceModelEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun bookDao(): BookDao
    abstract fun audioVersionDao(): AudioVersionDao
    abstract fun voiceModelDao(): VoiceModelDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "audiobook_database"
                )
                    .fallbackToDestructiveMigration() // For now, remove in production
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}