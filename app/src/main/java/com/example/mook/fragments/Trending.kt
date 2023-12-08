package com.example.mook.fragments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Trending() {
    Surface (modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
    ){
        Text(text = "Trending Fragment")
    }
}