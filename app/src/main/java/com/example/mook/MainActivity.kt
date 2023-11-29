package com.example.mook

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mook.ui.theme.MookTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
private lateinit var library: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            library = getSharedPreferences("library", MODE_PRIVATE)
            MookTheme {
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()
                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        ModalDrawerSheet {
                            Drawer(navController, scope, drawerState)
                        }
                    }
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = "library"
                    ) {
                        composable("library")   { Library(library, navController)}
                        composable("trending")  { Trending() }
                        composable("settings")  { Settings() }
                        composable("add books") { AddBooks() }
                    }
                }

            }
        }
    }
}

// ---------------------------Nav Drawer---------------------------
@Composable
fun Drawer(navController: NavHostController, scope: CoroutineScope, drawerState: DrawerState) {
    Text("Navigation Drawer",
        modifier = Modifier
            .padding(16.dp))
    Divider()
    Column (
        modifier = Modifier
            .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
    ){
        Column {
            NavigationDrawerItem(
                icon = {Icon(painter = painterResource(id = R.drawable.baseline_menu_book_24), contentDescription = null)},
                label = {Text(text = "Library")},
                selected = false,
                onClick = {
                    navController.navigate("library")
                    scope.launch {
                        drawerState.apply {
                            if (isClosed) open() else close()
                        }
                    }
                },
                modifier = Modifier
            )
            NavigationDrawerItem(
                icon = {Icon(painter = painterResource(id = R.drawable.baseline_trending_up_24), contentDescription = null)},
                label = {Text(text = "Trending")},
                selected = false,
                onClick = {
                    navController.navigate("trending")
                    scope.launch {
                        drawerState.apply {
                            if (isClosed) open() else close()
                        }
                    }
                },
                modifier = Modifier
            )
        }
        Row (modifier = Modifier
            .weight(1f, false)){
            NavigationDrawerItem(
                icon = {Icon(imageVector = Icons.Default.Settings, contentDescription = null)},
                label = {Text(text = "Settings")},
                selected = false,
                onClick = {
                    navController.navigate("settings")
                },
                modifier = Modifier
            )
        }


    }
}

// ---------------------------Main Fragments---------------------------
@Composable
fun Library(library: SharedPreferences, navController: NavHostController) {
    Surface (modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
    ){
        ConstraintLayout{
            val (editButton, addButton, grid) = createRefs()
            var isEditClicked by remember{ mutableStateOf(false)}
            var isAddClicked by remember{ mutableStateOf(false)}
            var isEditable by remember{ mutableStateOf(false)}
            val list = remember{ mutableStateListOf(library.all) }
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
                    .background(Color.Green)
            ){
                // Display User's Library
                items(list.size){it ->
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
@Composable
fun Trending() {
    Surface (modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
    ){
        Text(text = "Trending Fragment")
    }
}
@Composable
fun Settings() {
    Surface (modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
    ){
        Text(text = "Settings Fragment")
    }
}
@Composable
fun AddBooks() {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (
            searchTitleText, searchTitleBox,
            searchAuthorText, searchAuthorBox,
            enterButton, results
        ) = createRefs()
        Text(
            text = "Title: ",
            modifier = Modifier
                .constrainAs(searchTitleText){
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(searchTitleBox.start)
                    bottom.linkTo(searchTitleBox.bottom)
                }
        )
        TextField(
            value = "Title",
            onValueChange = {},
            singleLine = true,
            modifier = Modifier
                .constrainAs(searchTitleBox){
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
        )
        Text(
            text = "Author: ",
            modifier = Modifier
                .constrainAs(searchAuthorText){
                    top.linkTo(searchTitleText.bottom)
                    start.linkTo(searchTitleText.start)

            }
        )
        TextField(
            value = "Author",
            onValueChange = {},
            singleLine = true, modifier = Modifier
                .constrainAs(searchAuthorBox){
                    top.linkTo(searchTitleBox.bottom)
                    end.linkTo(searchTitleBox.end)

                }

        )
    }
}
// ---------------------------Dialogs---------------------------


// ---------------------------Helpers---------------------------
@Composable
fun LibraryBook() {
    ConstraintLayout(
        modifier = Modifier
            .height(400.dp)
            .width(300.dp)
    ) {
        val (image, title, author) = createRefs()
        Image(
            painter = painterResource(id = R.drawable.baseline_square_24),
            contentDescription = null,
            modifier = Modifier
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .width(280.dp)
                .height(350.dp)
                .background(Color.Red),
            contentScale = ContentScale.FillBounds
        )
        Text(
            text = "Title",
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(image.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .background(Color.Blue)
        )
        Text(
            text = "Author",
            modifier = Modifier
                .constrainAs(author) {
                    top.linkTo(title.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .background(Color.Green)

        )

    }
}


// ---------------------------Preview---------------------------
@Preview(showBackground = true)
@Composable
fun prev() {
    MookTheme {
        AddBooks()
    }
}