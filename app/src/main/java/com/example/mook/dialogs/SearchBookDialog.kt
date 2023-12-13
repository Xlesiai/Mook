package com.example.mook.dialogs

import android.util.Log
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.example.mook.R
import com.example.mook.database.LibraryBook
import com.example.mook.database.LibraryEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDialog(book: LibraryBook,
               onEvent: (LibraryEvent) -> Unit,
               modifier: Modifier
) {
    var showable by remember { mutableStateOf(true) }
    if (showable){
        AlertDialog(
            onDismissRequest = {
                showable = true
            },
            modifier = modifier,
            properties = DialogProperties(
                dismissOnClickOutside = true,
                dismissOnBackPress = true,
            )
        ) {
            Card(
                modifier = Modifier
                    .fillMaxSize()

            ){
                Scaffold (
                    modifier = Modifier,
                    topBar = {
                        Column(
                            Modifier.horizontalScroll(rememberScrollState()),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            val textInput by remember { mutableStateOf("") }
                            val audioInput by remember { mutableStateOf("") }

                            Row {
                                Column {
                                    AsyncImage(model = book.cover,
                                               contentDescription = null,
                                               modifier = Modifier.width(100.dp),
                                               placeholder = painterResource(id = R.drawable.baseline_square_24)

                                    )
                                }
                                Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                                    Row {
                                        Text(book.title)
                                    }
                                    Row {
                                        Text(book.author)
                                    }
                                }
                            }
                            Row {
                                Text(
                                    text = book.description ?: "No description",
                                    softWrap = true,
                                    style = MaterialTheme.typography.bodySmall,

                                    )
                            }

                            Row {
                                Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                                    Text(text = "PDF:")
                                }
                                Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                                    TextField(value = textInput, onValueChange = {
                                        book.text = it
                                    })
                                }
                            }
                            Row {

                                Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                                    Text(text = "Audio:")
                                }
                                Column(modifier = Modifier.align(Alignment.CenterVertically)) {

                                    TextField(value = audioInput, onValueChange = {
                                        book.audio = it
                                    })
                                }
                            }
                        }
                    },
                    floatingActionButton = {
                        SmallFloatingActionButton(
                            onClick = {
                                onEvent(LibraryEvent.SetTitle(book.title))
                                onEvent(LibraryEvent.SetAuthor(book.author))
                                onEvent(LibraryEvent.SetDescription(book.description?: ""))
                                onEvent(LibraryEvent.SetCover(book.cover ?: ""))
                                onEvent(LibraryEvent.SetText(book.text ?: ""))
                                onEvent(LibraryEvent.SetAudio(book.audio ?: ""))
                                onEvent(LibraryEvent.SaveBook)
                                Log.d("BookDialog", "Saved")
                                showable = !showable
                            },
                            modifier = Modifier,
                            shape = CircleShape,
                        ) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = null)

                        }
                    },
                    floatingActionButtonPosition = FabPosition.Center
                ){}

            }
        }
    }


}


