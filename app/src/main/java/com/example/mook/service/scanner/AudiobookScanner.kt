// com.example.mook.service.scanner.AudiobookScanner.kt
package com.example.mook.service.scanner

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.DocumentsContract
import android.util.Log
import com.example.mook.data.local.database.entities.BookEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject


class AudiobookScanner @Inject constructor(
    @ApplicationContext
    private val context: Context
) {

    companion object {
        private const val TAG = "AudiobookScanner"

        // Supported audio file extensions
        val SUPPORTED_EXTENSIONS = listOf(
            ".m4b", ".m4a", ".mp3", ".mp4", ".ogg", ".wav", ".flac", ".aac"
        )

        // Supported document extensions for TTS
        val SUPPORTED_DOC_EXTENSIONS = listOf(
            ".epub", ".pdf", ".txt"
        )
    }

    /**
     * Scan a directory tree for audiobook files
     */
    suspend fun scanDirectory(rootUri: Uri): List<BookEntity> = withContext(Dispatchers.IO) {
        val books = mutableListOf<BookEntity>()

        try {
            Timber.tag(TAG).d("Starting scan from URI: $rootUri")
            scanDirectoryRecursive(rootUri, null, null, books)
            Timber.tag(TAG).d("Scan completed. Found ${books.size} books")
        } catch (e: Exception) {
            Timber.tag(TAG).e(e, "Error scanning directory")
        }

        return@withContext books
    }

    /**
     * Recursively scan directory structure
     * Structure: root -> author -> collection -> book -> files
     */
    private suspend fun scanDirectoryRecursive(
        directoryUri: Uri,
        parentAuthor: String?,
        parentCollection: String?,
        books: MutableList<BookEntity>
    ) {
        val resolver = context.contentResolver

        try {
            val childrenUri = DocumentsContract.buildChildDocumentsUriUsingTree(
                directoryUri,
                DocumentsContract.getDocumentId(directoryUri)
            )

            resolver.query(
                childrenUri,
                arrayOf(
                    DocumentsContract.Document.COLUMN_DOCUMENT_ID,
                    DocumentsContract.Document.COLUMN_DISPLAY_NAME,
                    DocumentsContract.Document.COLUMN_MIME_TYPE,
                    DocumentsContract.Document.COLUMN_SIZE,
                    DocumentsContract.Document.COLUMN_LAST_MODIFIED
                ),
                null,
                null,
                null
            )?.use { cursor ->
                while (cursor.moveToNext()) {
                    val documentId = cursor.getString(0)
                    val displayName = cursor.getString(1)
                    val mimeType = cursor.getString(2)
                    val size = cursor.getLong(3)
                    val lastModified = cursor.getLong(4)

                    val childUri = DocumentsContract.buildDocumentUriUsingTree(
                        directoryUri,
                        documentId
                    )

                    // Check if it's a directory or file
                    if (mimeType == DocumentsContract.Document.MIME_TYPE_DIR) {
                        // This is a directory - could be author, collection, or book folder
                        processDirectory(
                            childUri,
                            displayName,
                            parentAuthor,
                            parentCollection,
                            books
                        )
                    } else {
                        // This is a file - check if it's an audiobook file
                        processFile(
                            childUri,
                            displayName,
                            size,
                            lastModified,
                            parentAuthor,
                            parentCollection,
                            books
                        )
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error scanning directory: $directoryUri", e)
        }
    }

    private suspend fun processDirectory(
        directoryUri: Uri,
        directoryName: String,
        parentAuthor: String?,
        parentCollection: String?,
        books: MutableList<BookEntity>
    ) {
        when {
            // If we don't have a parent author yet, this is likely an author folder
            parentAuthor == null -> {
                // This directory could be an author folder
                scanDirectoryRecursive(directoryUri, directoryName, null, books)
            }

            // If we have an author but no collection, this could be collection or book folder
            parentCollection == null -> {
                // Need to check if this contains audiobook files directly
                if (containsAudioFiles(directoryUri)) {
                    // This is a book folder
                    createBookFromFolder(directoryUri, directoryName, parentAuthor, null, books)
                } else {
                    // This is a collection folder
                    scanDirectoryRecursive(directoryUri, parentAuthor, directoryName, books)
                }
            }

            // We have both author and collection, so this must be a book folder
            else -> {
                createBookFromFolder(directoryUri, directoryName, parentAuthor, parentCollection, books)
            }
        }
    }

    private suspend fun containsAudioFiles(directoryUri: Uri): Boolean {
        val resolver = context.contentResolver

        return try {
            val childrenUri = DocumentsContract.buildChildDocumentsUriUsingTree(
                directoryUri,
                DocumentsContract.getDocumentId(directoryUri)
            )

            resolver.query(
                childrenUri,
                arrayOf(DocumentsContract.Document.COLUMN_DISPLAY_NAME),
                null,
                null,
                null
            )?.use { cursor ->
                while (cursor.moveToNext()) {
                    val fileName = cursor.getString(0)?.lowercase()
                    if (fileName != null && SUPPORTED_EXTENSIONS.any { fileName.endsWith(it) }) {
                        return@use true
                    }
                }
                false
            } ?: false
        } catch (e: Exception) {
            false
        }
    }

    private fun processFile(
        fileUri: Uri,
        fileName: String,
        size: Long,
        lastModified: Long,
        parentAuthor: String?,
        parentCollection: String?,
        books: MutableList<BookEntity>
    ) {
        val lowerFileName = fileName.lowercase()

        // Check if it's an audio file
        val isAudioFile = SUPPORTED_EXTENSIONS.any { lowerFileName.endsWith(it) }
        val isDocumentFile = SUPPORTED_DOC_EXTENSIONS.any { lowerFileName.endsWith(it) }

        if (isAudioFile || isDocumentFile) {
            // Create a book from a single file
            val book = createBookFromFile(
                fileUri,
                fileName,
                size,
                lastModified,
                parentAuthor,
                parentCollection
            )

            if (book != null) {
                books.add(book)
            }
        }
    }

    private fun createBookFromFolder(
        folderUri: Uri,
        folderName: String,
        author: String,
        collection: String?,
        books: MutableList<BookEntity>
    ) {
        // Extract book title from folder name
        val title = cleanBookTitle(folderName)

        val book = BookEntity(
            title = title,
            author = author,
            folderPath = folderUri.toString(),
            fileFormat = if (containsDocumentFiles(folderUri)) {
                BookEntity.FileFormat.FOLDER
            } else {
                BookEntity.FileFormat.M4B // Default for audio folders
            },
            description = null,
            createdAt = System.currentTimeMillis(),
            lastAccessedAt = System.currentTimeMillis()
        )

        books.add(book)
    }

    private fun createBookFromFile(
        fileUri: Uri,
        fileName: String,
        size: Long,
        lastModified: Long,
        author: String?,
        collection: String?
    ): BookEntity? {
        // Extract title from filename (remove extension)
        val title = cleanBookTitle(fileName.substringBeforeLast("."))

        // Determine file format
        val format = when {
            fileName.lowercase().endsWith(".m4b") -> BookEntity.FileFormat.M4B
            fileName.lowercase().endsWith(".epub") -> BookEntity.FileFormat.EPUB
            fileName.lowercase().endsWith(".pdf") -> BookEntity.FileFormat.PDF
            fileName.lowercase().endsWith(".txt") -> BookEntity.FileFormat.TXT
            else -> BookEntity.FileFormat.M4B // Default
        }

        return BookEntity(
            title = title,
            author = author ?: "Unknown",
            folderPath = getParentUri(fileUri)?.toString() ?: fileUri.toString(),
            fileFormat = format,
            description = null,
            createdAt = lastModified,
            lastAccessedAt = System.currentTimeMillis()
        )
    }

    private fun cleanBookTitle(title: String): String {
        // Remove common patterns from audiobook filenames
        return title
            .replace(Regex("\\[.*?\\]"), "") // Remove brackets content
            .replace(Regex("\\(.*?\\)"), "") // Remove parentheses content
            .replace(Regex("[_-]+"), " ") // Replace underscores/dashes with spaces
            .replace(Regex("\\s+"), " ") // Collapse multiple spaces
            .trim()
    }

    private fun containsDocumentFiles(folderUri: Uri): Boolean {
        val resolver = context.contentResolver

        return try {
            val childrenUri = DocumentsContract.buildChildDocumentsUriUsingTree(
                folderUri,
                DocumentsContract.getDocumentId(folderUri)
            )

            resolver.query(
                childrenUri,
                arrayOf(DocumentsContract.Document.COLUMN_DISPLAY_NAME),
                null,
                null,
                null
            )?.use { cursor ->
                while (cursor.moveToNext()) {
                    val fileName = cursor.getString(0)?.lowercase()
                    if (fileName != null && SUPPORTED_DOC_EXTENSIONS.any { fileName.endsWith(it) }) {
                        return@use true
                    }
                }
                false
            } ?: false
        } catch (e: Exception) {
            false
        }
    }

    private fun getParentUri(fileUri: Uri): Uri? {
        return try {
            DocumentsContract.buildDocumentUriUsingTree(
                fileUri,
                DocumentsContract.getTreeDocumentId(fileUri)
            )
        } catch (e: Exception) {
            null
        }
    }
}