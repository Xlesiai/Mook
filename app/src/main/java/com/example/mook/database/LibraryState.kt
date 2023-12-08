package com.example.mook.database

data class LibraryState(
    val books: List<LibraryBook> = emptyList(),
    val title: String = "",
    val author: String = "",
    val description: String = "",
    val cover: String = "",
    val text: String = "",
    val audio: String = "",
    val isAddingBook: Boolean = false,
    val sortType: SortType = SortType.TITLE
) {

}