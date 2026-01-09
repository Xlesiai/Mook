package com.example.mook.data.local.repository

import com.example.mook.data.local.database.daos.BookDao
import com.example.mook.data.local.database.entities.BookEntity
import com.example.mook.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val bookDao: BookDao
) : BookRepository {

    override fun getAllBooks(): Flow<List<BookEntity>> {
        return bookDao.getAllBooks()
    }

    override suspend fun getBookById(bookId: Long): BookEntity? {
        return bookDao.getBookById(bookId)
    }

    override suspend fun insertBook(book: BookEntity): Long {
        return bookDao.insert(book)
    }

    override suspend fun updateLastAccessed(bookId: Long) {
        bookDao.updateLastAccessed(bookId)
    }

    override suspend fun getBookCount(): Int {
        return bookDao.getBookCount()
    }

    override suspend fun getBooksByAuthor(author: String): Flow<List<BookEntity>> {
        return bookDao.getBooksByAuthor(author)
    }

    override suspend fun deleteBook(bookId: Long) {
        bookDao.deleteBook(bookId)
    }
    override suspend fun getBookByPath(path: String): BookEntity? {
        // You need to add this to your BookDao and BookRepository
        return bookDao.getBookByPath(path)
    }
}