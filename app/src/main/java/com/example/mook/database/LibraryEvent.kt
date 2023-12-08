package com.example.mook.database

sealed interface LibraryEvent {
    object SaveBook: LibraryEvent
    // Setters
    data class SetTitle(val title: String) : LibraryEvent
    data class SetAuthor(val author: String) : LibraryEvent
    data class SetDescription(val description: String) : LibraryEvent
    data class SetCover(val cover: String) : LibraryEvent
    data class SetText(val text: String) : LibraryEvent
    data class SetAudio(val audio: String) : LibraryEvent

    // Dialog
    object ShowDialog: LibraryEvent
    object HideDialog: LibraryEvent

    // Helper
    data class SortBooks(val sortType: SortType): LibraryEvent
    data class DeleteBook(val book: LibraryBook) : LibraryEvent
}