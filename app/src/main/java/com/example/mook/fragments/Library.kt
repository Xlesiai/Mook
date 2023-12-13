package com.example.mook.fragments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mook.database.LibraryState
import com.example.mook.helper.LibraryBook
import com.example.mook.navigation.MiniNav

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Library(state: LibraryState, navController: NavHostController) {
    Surface (modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
    ){
        var isEditClicked by remember{ mutableStateOf(false)}


        Scaffold(
            Modifier,
            topBar = {
                // Mini Navigation Bar
                TopAppBar(
                    title = {
                        MiniNav(
                            modifier = Modifier
                                .height(100.dp),
                            onClick = {
                                isEditClicked = !isEditClicked
                            }
                        )
                    },
                    Modifier
                )

            },
            floatingActionButton = {
                if (isEditClicked) {
                    IconButton(
                        modifier = Modifier
                            .padding(30.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary),
                        onClick = {
                            navController.navigate("add books")
                        }
                    ){
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.background
                        )
                    }
                }
            },
            floatingActionButtonPosition = FabPosition.Center
        ){ pad ->
                // Library Grid
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(100.dp),
                    modifier = Modifier
                        .padding(pad)
                        .fillMaxSize()
                ){
                    // Display User's Library
                    items(state.books){
                        LibraryBook(it, isEditClicked)
                    }
                }


        }
    }



}
