package com.example.mook.fragments

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.example.mook.database.LibraryBook
import com.example.mook.database.LibraryState
import com.example.mook.database.LibraryViewModel
import com.example.mook.helper.SearchBookResult
import okhttp3.Headers
import kotlin.system.exitProcess

@Composable
fun AddBooks(state: LibraryState, viewModel: LibraryViewModel, context: Context) {
    val searchResults = remember { mutableStateListOf<LibraryBook>() }
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
            enterButton, results, dialog
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
                SearchBookResult(searchResults[it], viewModel::onEvent, state)
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
                    link = "https://openlibrary.org/search.json?author=$new_author&fields=title,author_name,cover_edition_key,key"
                if (new_author.isBlank())
                    link = "https://openlibrary.org/search.json?title=$new_title&fields=title,author_name,cover_edition_key,key"
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
                                    var desc = "No Description"
                                    val key = obj.getString("key")

                                    try {
                                        client["https://openlibrary.org${key}.json", object: JsonHttpResponseHandler() {
                                            override fun onFailure(
                                                statusCode: Int,
                                                headers: Headers?,
                                                response: String?,
                                                throwable: Throwable?
                                            ) {

                                            }

                                            override fun onSuccess(
                                                statusCode: Int,
                                                headers: Headers?,
                                                json: JSON
                                            ) {
                                                try {
                                                    desc =
                                                        json.jsonObject.getJSONObject("description")
                                                            .getString("value") as String
                                                } catch (e: Exception) {
                                                    Log.e("$key Description: ", e.toString().removePrefix("org.json.JSONException: Value "))

                                                }
                                            }

                                        }]
                                        if (desc == "") {
                                            continue
                                        }
                                        val book = LibraryBook(
                                            obj.getString("title"),
                                            obj.getJSONArray("author_name")[0].toString(),
                                            desc,
                                            "https://covers.openlibrary.org/b/olid/${obj.getString("cover_edition_key")}-M.jpg",

                                        )
                                        searchResults.add(book)
                                        Log.d("OpenLibrary Book", book.toString())
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