package com.example.mook.fragments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.mook.database.LibraryViewModel

@Composable
fun PlayBook(title: String, author: String, viewModel: LibraryViewModel) {
    // Get Book

    val book = viewModel.seachBook(title, author).collectAsState(initial = null)

    Surface (modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
    ){
        book.value?.let { foundBook ->
            // Here you can access the found book and display its information
            Text(text = "Title: ${foundBook.title}, Author: ${foundBook.author}")
            // You can display other book properties as needed
        } ?: run {
            Text(text = "Book not found")
        }
    }
}