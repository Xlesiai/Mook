package com.example.mook.domain.usecase

import com.example.mook.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBooksUseCase @Inject constructor(
    private val bookRepository: BookRepository
) {
    operator fun invoke(): Flow<List<com.example.mook.data.local.database.entities.BookEntity>> {
        return bookRepository.getAllBooks()
    }
}