package com.example.mook.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Dao
interface LibraryDAO {
    @Upsert
    suspend fun upsertBook(book: LibraryBook)

    @Delete
    suspend fun deleteBook(book: LibraryBook)

    @Query("SELECT * FROM Library Order by author ASC")
    fun getBooksByAuthor(): Flow<List<LibraryBook>>

    @Query("SELECT * FROM Library Order by title ASC")
    fun getBooksByTitle(): Flow<List<LibraryBook>>

}