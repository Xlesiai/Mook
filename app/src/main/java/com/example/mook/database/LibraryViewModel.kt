package com.example.mook.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mook.database.SortType.AUTHOR
import com.example.mook.database.SortType.TITLE
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LibraryViewModel(
    private val dao: LibraryDAO
): ViewModel() {
    private val _state = MutableStateFlow(LibraryState())
    private val _sortType = MutableStateFlow(TITLE)
    private val _books = _sortType.flatMapLatest {
        when(it){
            TITLE -> dao.getBooksByTitle()
            AUTHOR -> dao.getBooksByAuthor()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val state = combine(_state, _sortType, _books){ state, sortType, books ->
        state.copy(
            sortType = sortType,
            books = books
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), LibraryState())
    fun onEvent(event: LibraryEvent){
        when(event){
            is LibraryEvent.DeleteBook -> {
                viewModelScope.launch {
                    dao.deleteBook(event.book)
                }
            }
            LibraryEvent.HideDialog -> {
                _state.update {
                    it.copy(
                        isAddingBook = false
                    )
                }
            }
            LibraryEvent.SaveBook -> {
                val title = state.value.title
                val author = state.value.author
                val description = state.value.description
                val cover = state.value.cover
                val text = state.value.text
                val audio = state.value.audio

                if (title.isEmpty() || author.isEmpty()){
                    return
                }
                val book = LibraryBook(title, author, description, cover, text, audio)
                viewModelScope.launch {
                    dao.upsertBook(book)
                }
                _state.update {
                    it.copy(
                        isAddingBook = false,
                        title = "",
                        author = "",
                        description = "",
                        cover = "",
                        text = "",
                        audio = ""
                    )
                }
            }
            is LibraryEvent.SetAudio -> {
                _state.update {
                    it.copy(
                        audio = event.audio
                    )
                }
            }
            is LibraryEvent.SetAuthor -> {
                _state.update {
                    it.copy(
                        author = event.author
                    )
                }
            }
            is LibraryEvent.SetCover -> {
                _state.update {
                    it.copy(
                        cover = event.cover
                    )
                }
            }
            is LibraryEvent.SetDescription -> {
                _state.update {
                    it.copy(
                        description = event.description
                    )
                }
            }
            is LibraryEvent.SetText -> {
                _state.update {
                    it.copy(
                        text = event.text
                    )
                }
            }
            is LibraryEvent.SetTitle -> {
                _state.update {
                    it.copy(
                        title = event.title
                    )
                }
            }
            LibraryEvent.ShowDialog -> {
                _state.update {
                    it.copy(
                        isAddingBook = true
                    )
                }
            }
            is LibraryEvent.SortBooks -> {
                _sortType.value = event.sortType
            }
        }
    }
}