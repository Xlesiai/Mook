package com.example.mook.presentation.library

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mook.data.local.database.entities.BookEntity
import com.example.mook.data.local.datasource.SettingsDataSourceImpl
import com.example.mook.domain.repository.BookRepository
import com.example.mook.service.scanner.AudiobookScanner
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.core.net.toUri

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val bookRepository: BookRepository,
    private val audiobookScanner: AudiobookScanner,
    private val settingsDataSource: SettingsDataSourceImpl,
    private val application: Application
) : ViewModel() {

    private val _uiState = MutableStateFlow(LibraryUiState())
    val uiState: StateFlow<LibraryUiState> = _uiState

    private val _scanningState = MutableStateFlow<ScanningState>(ScanningState.Idle)
    val scanningState: StateFlow<ScanningState> = _scanningState

    init {
        loadBooks()
    }

    private fun loadBooks() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                bookRepository.getAllBooks().collectLatest { books ->
                    _uiState.value = _uiState.value.copy(
                        books = books,
                        isLoading = false,
                        isEmpty = books.isEmpty()
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Failed to load books: ${e.message}"
                )
            }
        }
    }

    fun refresh() {
        loadBooks()
    }

    fun onBookClick(bookId: Long) {
        viewModelScope.launch {
            bookRepository.updateLastAccessed(bookId)
        }
    }

    fun scanFolder(uri: Uri) {
        viewModelScope.launch {
            _scanningState.value = ScanningState.Scanning(0)

            try {
                val scannedBooks = audiobookScanner.scanDirectory(uri)

                // Insert scanned books into database
                scannedBooks.forEach { book ->
                    // Check if book already exists
                    val existingBook = bookRepository.getBookByPath(book.folderPath)
                    if (existingBook == null) {
                        bookRepository.insertBook(book)
                    }
                }

                _scanningState.value = ScanningState.Success(scannedBooks.size)

                // Refresh the book list
                loadBooks()

            } catch (e: Exception) {
                _scanningState.value = ScanningState.Error(e.message ?: "Unknown error")
            }
        }
    }

    // method to handle folder picker result
    fun handleFolderPickerResult(resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            val uriString = data?.getStringExtra(FolderPickerActivity.EXTRA_SELECTED_URI)
            if (uriString != null) {
                viewModelScope.launch {
                    // 1. Save the URI
                    settingsDataSource.saveLibraryFolderUri(uriString)

                    // 2. Scan the folder
                    val uri = uriString.toUri()
                    scanFolder(uri)
                }
            }
        }
    }

    // method to scan from saved URI
    fun scanFromSavedLibrary() {
        viewModelScope.launch {
            val savedUri = settingsDataSource.getLibraryFolderUri()
            if (savedUri != null) {
                val uri = savedUri.toUri()
                scanFolder(uri)
            } else {
                // No saved folder - maybe show a message
                _uiState.value = _uiState.value.copy(
                    errorMessage = "No library folder selected. Please choose a folder first."
                )
            }
        }
    }


    // For testing - add sample data
    fun addSampleBooks() {
        viewModelScope.launch {
            val sampleBooks = listOf(
                BookEntity(
                    title = "The Hobbit",
                    author = "J.R.R. Tolkien",
                    folderPath = "/storage/emulated/0/Audiobooks/Tolkien/The Hobbit",
                    description = "Bilbo Baggins embarks on an unexpected journey",
                    year = 1937,
                    isTTSGenerated = false
                ),
                BookEntity(
                    title = "Dune",
                    author = "Frank Herbert",
                    folderPath = "/storage/emulated/0/Audiobooks/Herbert/Dune",
                    description = "Epic science fiction set in the distant future",
                    year = 1965,
                    isTTSGenerated = true
                )
            )

            sampleBooks.forEach { bookRepository.insertBook(it) }
            loadBooks()
        }
    }
}

data class LibraryUiState(
    val books: List<BookEntity> = emptyList(),
    val isLoading: Boolean = false,
    val isEmpty: Boolean = true,
    val errorMessage: String? = null
)

sealed class ScanningState {
    object Idle : ScanningState()
    data class Scanning(val progress: Int) : ScanningState()
    data class Success(val booksFound: Int) : ScanningState()
    data class Error(val message: String) : ScanningState()
}