package com.example.mook.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.mook.database.LibraryEvent
import com.example.mook.database.LibraryState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDialog(state: LibraryState,
               onEvent: (LibraryEvent) -> Unit,
               modifier: Modifier
) {
    AlertDialog(
        onDismissRequest = {
           onEvent(LibraryEvent.HideDialog)
        },
        modifier = modifier
    ) {
        Text(

        )
    }
}