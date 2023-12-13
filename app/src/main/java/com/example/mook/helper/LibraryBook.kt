package com.example.mook.helper

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.example.mook.R
import com.example.mook.database.LibraryBook

@Composable
fun LibraryBook(book: LibraryBook, edit: Boolean) {
    ConstraintLayout(
        modifier = Modifier,
    ) {
        val (image, title, author, editButton) = createRefs()
        AsyncImage(
            model = book.cover,
            contentDescription = null,
            modifier = Modifier
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            contentScale = ContentScale.FillBounds
        )
        Text(
            text = book.title,
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(image.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = book.author,
            modifier = Modifier
                .constrainAs(author) {
                    top.linkTo(title.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            style = MaterialTheme.typography.bodySmall

        )
        if (edit) {
            IconButton(
                onClick = { }
            ){
                
            }
        }

    }
}
