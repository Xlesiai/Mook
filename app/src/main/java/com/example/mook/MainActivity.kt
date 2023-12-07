package com.example.mook

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.example.mook.helper.Book
import com.example.mook.ui.theme.MookTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import okhttp3.Headers
import kotlin.system.exitProcess

lateinit var context: Context

class MainActivity : ComponentActivity() {
private lateinit var library: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            context = LocalContext.current
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
                    .background(Color(0, 255, 0, 20))
            ){
                // Display User's Library
                items(list.size){
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
    val searchResults = remember { mutableStateListOf(Book())}
    var title by remember{ mutableStateOf("") }
    var author by remember{ mutableStateOf("") }
    val client = AsyncHttpClient()

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val (
            searchTitleText, searchTitleBox,
            searchAuthorText, searchAuthorBox,
            enterButton, results
        ) = createRefs()

        Text(
            text = "Title: ",
            color = MaterialTheme.colorScheme.inversePrimary,
            modifier = Modifier
                .constrainAs(searchTitleText){
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(searchTitleBox.start)
                    bottom.linkTo(searchTitleBox.bottom)
                }
        )
        TextField(
            value = title,
            onValueChange = {
                 title = it
            },
            singleLine = true,
            modifier = Modifier
                .constrainAs(searchTitleBox) {
                    top.linkTo(parent.top, 16.dp)
                    end.linkTo(parent.end, 16.dp)
                }
                .clip(RoundedCornerShape(50))

        )
        Text(
            text = "Author: ",
            color = MaterialTheme.colorScheme.inversePrimary,
            modifier = Modifier
                .constrainAs(searchAuthorText){
                    top.linkTo(searchAuthorBox.top)
                    bottom.linkTo(searchAuthorBox.bottom)
                    start.linkTo(searchTitleText.start)

            }
        )
        TextField(
            value = author,
            onValueChange = {
                author = it
            },
            singleLine = true, modifier = Modifier
                .constrainAs(searchAuthorBox) {
                    top.linkTo(searchTitleBox.bottom, 16.dp)
                    end.linkTo(searchTitleBox.end)

                }
                .clip(RoundedCornerShape(50))


        )
        LazyColumn(
            modifier = Modifier
                .constrainAs(results) {
                    top.linkTo(searchAuthorBox.bottom, 16.dp)
                    start.linkTo(parent.start, 10.dp)
                    end.linkTo(parent.end, 10.dp)
                    bottom.linkTo(enterButton.bottom)

                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
                .clip(RoundedCornerShape(10))
                .background(Color(0, 0, 0, 50)),

        ){
            items(searchResults.size){
                SearchBookResult(book = searchResults[it])
                Divider()

            }

        }
        IconButton(
            onClick = {
                Log.d("Send Button", "Title: $title, Author: $author")
                searchResults.clear()

                val new_title = title.replace(" ", "+")
                val new_author = author.replace(" ", "+")
                var link = "https://openlibrary.org/search.json?title=$new_title&author=$new_author&fields=title,author_name,cover_edition_key"
                if (new_title.isBlank())
                    link = "https://openlibrary.org/search.json?author=$new_author&fields=title,author_name,cover_edition_key"
                if (new_author.isBlank())
                    link = "https://openlibrary.org/search.json?title=$new_title&fields=title,author_name,cover_edition_key"
                if (new_author.isBlank() and new_title.isBlank())
                    exitProcess(0)

                client[link,
                    object: JsonHttpResponseHandler(){
                    override fun onFailure(
                        statusCode: Int,
                        headers: Headers?,
                        response: String?,
                        throwable: Throwable?
                    ) {

                    }

                    override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON?) {
                        json?.jsonObject?.getJSONArray("docs")?.let { docsArray ->
                            for (i in 0 until docsArray.length()) {
                                val obj = docsArray.getJSONObject(i)
                                try {
                                    val book = Book(
                                        obj.getString("title"),
                                        obj.getJSONArray("author_name")[0].toString(),
                                        "https://covers.openlibrary.org/b/olid/${obj.getString("cover_edition_key")}-M.jpg"
                                    )
                                    searchResults.add(book)
                                }
                                catch (e:Exception){
                                    Log.e("OpenLibrary", e.toString())
                                }


                            }
                        }
                    }

                }]
            },
            modifier = Modifier
                .padding(30.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .constrainAs(enterButton) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom, 16.dp)

                }

        ) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = null

            )
        }
    }
}
// ---------------------------Dialogs---------------------------


// ---------------------------Helpers---------------------------
@Composable
fun LibraryBook() {
    ConstraintLayout(
        modifier = Modifier
            .height(400.dp)
            .width(300.dp),
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

@Composable
fun SearchBookResult(book: Book) {
    ConstraintLayout (
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
            .clickable {
                
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
    }
    
}

// ---------------------------Preview---------------------------
@Preview(showBackground = true)
@Composable
fun prev() {
    MookTheme {
        SearchBookResult(Book("Harry Potter", "J.K Rowling", "https://covers.openlibrary.org/a/olid/OL23919A-M.jpg"))
    }
}