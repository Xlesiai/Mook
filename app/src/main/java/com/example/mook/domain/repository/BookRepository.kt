package com.example.mook.domain.repository

import com.example.mook.data.local.database.entities.BookEntity
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    fun getAllBooks(): Flow<List<BookEntity>>
    suspend fun getBookById(bookId: Long): BookEntity?
    suspend fun insertBook(book: BookEntity): Long
    suspend fun updateLastAccessed(bookId: Long)
    suspend fun getBookCount(): Int
    suspend fun getBooksByAuthor(author: String): Flow<List<BookEntity>>
    suspend fun deleteBook(bookId: Long)
    suspend fun getBookByPath(path: String): BookEntity?
}


