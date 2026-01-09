package com.example.mook.data.local.database.daos


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mook.data.local.database.entities.BookEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(book: BookEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(books: List<BookEntity>)

    @Update
    suspend fun update(book: BookEntity)

    @Query("SELECT * FROM books ORDER BY lastAccessedAt DESC")
    fun getAllBooks(): Flow<List<BookEntity>>

    @Query("SELECT * FROM books WHERE id = :bookId")
    suspend fun getBookById(bookId: Long): BookEntity?

    @Query("SELECT * FROM books WHERE folderPath = :folderPath LIMIT 1")
    suspend fun getBookByPath(folderPath: String): BookEntity?

    @Query("SELECT * FROM books WHERE author = :author ORDER BY title")
    fun getBooksByAuthor(author: String): Flow<List<BookEntity>>

    @Query("DELETE FROM books WHERE id = :bookId")
    suspend fun deleteBook(bookId: Long)

    @Query("UPDATE books SET lastAccessedAt = :timestamp WHERE id = :bookId")
    suspend fun updateLastAccessed(bookId: Long, timestamp: Long = System.currentTimeMillis())

    @Query("SELECT COUNT(*) FROM books")
    suspend fun getBookCount(): Int
}