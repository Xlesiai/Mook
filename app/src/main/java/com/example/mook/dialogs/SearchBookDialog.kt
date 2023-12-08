package com.example.mook.dialogs

import android.widget.ImageButton
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.example.mook.database.LibraryBook
import com.example.mook.database.LibraryEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDialog(book: LibraryBook,
               onEvent: (LibraryEvent) -> Unit,
               modifier: Modifier
) {
    AlertDialog(
        onDismissRequest = {
           onEvent(LibraryEvent.HideDialog)
        },
        modifier = modifier
    ) {
        Card(
            modifier = Modifier.
            fillMaxSize()
        ){
            ConstraintLayout {
                val (
                    image, title, author,
                    description, addButton,
                    text, editText,
                    audio, editAudio
                ) = createRefs()

                AsyncImage(model = book.cover, contentDescription = null)
                Text(book.title)
                Text(book.author)
                Text(book.description?: "No description")
                ImageButton()
                {

                }


            }
        }
    }
}


