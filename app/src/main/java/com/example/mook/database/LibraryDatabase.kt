package com.example.mook.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [LibraryBook::class], version = 1, exportSchema = false
)
abstract class LibraryDatabase: RoomDatabase() {
    abstract val dao: LibraryDAO
}