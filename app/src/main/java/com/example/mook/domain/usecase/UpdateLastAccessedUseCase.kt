package com.example.mook.domain.usecase

import com.example.mook.domain.repository.BookRepository
import javax.inject.Inject

class UpdateLastAccessedUseCase @Inject constructor(
    private val bookRepository: BookRepository
) {
    suspend operator fun invoke(bookId: Long) {
        bookRepository.updateLastAccessed(bookId)
    }
}