package com.example.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface BookDao {
    // Insert
    @Upsert
    suspend fun insertBook(book: Book)

    // Deletes
    @Query("DELETE FROM books WHERE author = :author AND title = :title")
    suspend fun deleteBook(author: String, title: String)

    @Query("DELETE FROM books")
    suspend fun deleteAllBooks()

    @Query("DELETE FROM books WHERE author = :author")
    suspend fun deleteBooksByAuthor(author: String)

    @Query("DELETE FROM books WHERE collection = :collection AND author = :author")
    suspend fun deleteBooksByCollection(author: String, collection: String)

    // GET
    @Query("SELECT * FROM books")
    fun getAllBooks(): LiveData<List<Book>>

    @Query("SELECT * FROM books WHERE author = :author AND title = :title")
    suspend fun getBookByAuthorAndTitle(author: String, title: String): Book?

    @Query("SELECT DISTINCT author FROM books ORDER BY author ASC")
    fun getAuthors(): LiveData<List<String>>

    // SET
    @Query("UPDATE books SET lastPlayed = :lastPlayed WHERE author = :author AND title = :title")
    suspend fun updateLastPlayed(author: String, title: String, lastPlayed: Long)

    @Query("UPDATE books SET progress = :progress WHERE author = :author AND title = :title")
    suspend fun updateProgress(author: String, title: String, progress: Float)

    @Query("UPDATE books SET rating = :rating WHERE author = :author AND title = :title")
    suspend fun updateRating(author: String, title: String, rating: Float)

}


