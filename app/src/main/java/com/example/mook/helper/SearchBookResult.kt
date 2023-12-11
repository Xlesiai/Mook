package com.example.mook.helper

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.mook.R
import com.example.mook.database.LibraryBook
import com.example.mook.database.LibraryEvent
import com.example.mook.database.LibraryState
import com.example.mook.dialogs.BookDialog

@Composable
fun SearchBookResult(book: LibraryBook, onEvent: (LibraryEvent) -> Unit, state: LibraryState) {
    var clickBook by remember{ mutableStateOf(false) }
    ConstraintLayout (
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
            .clickable {
                clickBook = true
            }
    ){
        val (image, title, author) = createRefs()
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(book.cover)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.baseline_square_24),
            contentDescription = null,
            modifier = Modifier
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                }
                .width(70.dp)
        )
        Text(
            text = book.title,
            modifier = Modifier
                .constrainAs(title){
                    top.linkTo(parent.top)
                    start.linkTo(image.end, 16.dp)
                    bottom.linkTo(author.top)
                },
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = book.author,
            modifier = Modifier
                .constrainAs(author){
                    top.linkTo(title.bottom)
                    start.linkTo(image.end, 32.dp)
                    bottom.linkTo(parent.bottom)
                },
            color = MaterialTheme.colorScheme.primary

        )
        if (clickBook) {
            // Figure out how to pass clickBook to quit dialog
            BookDialog(book, onEvent, Modifier)
        }
    }

}
