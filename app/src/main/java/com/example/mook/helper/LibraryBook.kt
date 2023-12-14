package com.example.mook.helper

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.mook.database.LibraryBook
import com.example.mook.database.LibraryEvent

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LibraryBook(book: LibraryBook, edit: Boolean, onEvent: (LibraryEvent) -> Unit, navController: NavController) {
    ConstraintLayout(
        modifier = Modifier
            .padding(top = 10.dp)
            .clickable {
                navController.navigate("play book/${book.title}/${book.author}")
            }
            .combinedClickable {

            },
    ) {
        val (image, editButton) = createRefs()
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

        if (edit) {
            IconButton(
                onClick = {
                          onEvent(LibraryEvent.DeleteBook(book))
                },
                Modifier
                    .constrainAs(editButton) {
                        top.linkTo(image.top)
                        end.linkTo(image.end)

                    }
                    .clip(CircleShape)
                    .height(25.dp)
                    .width(25.dp)
                    .background(MaterialTheme.colorScheme.primary)
            ){
                Icon(
                    Icons.Default.Close,
                    null,
                    tint = MaterialTheme.colorScheme.background,
                    modifier = Modifier.size(17.dp)
                )
            }
        }

    }
}
