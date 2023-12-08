package com.example.mook.fragments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.mook.database.LibraryState

@Composable
fun Library(state: LibraryState, navController: NavHostController) {
    Surface (modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
    ){
        ConstraintLayout{
            val (editButton, addButton, grid) = createRefs()
            var isEditClicked by remember{ mutableStateOf(false) }
            var isAddClicked by remember{ mutableStateOf(false) }
            var isEditable by remember{ mutableStateOf(false) }


            LazyVerticalGrid(
                columns = GridCells.Adaptive(3.dp),
                modifier = Modifier
                    .constrainAs(grid) {
                        top.linkTo(editButton.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
                    .fillMaxSize()
                    .background(Color(0, 255, 0, 20))
            ){
                // Display User's Library
                items(state.books){
                    Text(it.toString())
                }
            }
            IconButton(
                modifier = Modifier
                    .padding(30.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
                    .constrainAs(editButton) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    },
                onClick = {
                    isEditClicked = !isEditClicked
                }
            ) {
                if (isEditClicked)
                {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        modifier = Modifier
                    )
                }
                else{
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        modifier = Modifier
                    )
                }

            }
            if (isEditClicked) {
                IconButton(
                    modifier = Modifier
                        .padding(30.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                        .constrainAs(addButton) {
                            bottom.linkTo(parent.bottom)
                            end.linkTo(parent.end)
                            start.linkTo(parent.start)
                        },
                    onClick = {
                        isAddClicked = !isAddClicked
                    }

                ){
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                }
            }
            if (isAddClicked) {
                navController.navigate("add books")
            }

        }
    }
}