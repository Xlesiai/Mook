package com.example.mook

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.documentfile.provider.DocumentFile
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.arthenica.ffmpegkit.FFmpegKit
import com.arthenica.ffmpegkit.FFmpegKitConfig
import com.arthenica.ffmpegkit.FFprobeKit
import com.example.database.Book
import com.example.database.LibraryDatabase
import com.example.mook.ui.theme.CustomIcons
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject
import com.example.ai.Whisper


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryPage(navController: NavController, library: LibraryDatabase) {
    val context = LocalContext.current
    val userPref = context.getSharedPreferences("userPref", Context.MODE_PRIVATE)
    val scope = rememberCoroutineScope()
    val bookDao = library.bookDao()
    var books = remember { mutableListOf<Book>() }
    var authors by remember { mutableStateOf<List<String>>(emptyList()) }
    var sort by remember { mutableIntStateOf(0) }

    bookDao.getAllBooks().observeForever {
        books = it.toMutableList()
        Log.d("Library", "Books: $books")
    }
    bookDao.getAuthors().observeForever {
        authors = it
        Log.d("Library", "Authors: $authors")
    }
    LaunchedEffect(Unit) {
        delay(1000)
        sort = (sort + 1) % 2
        sort = (sort + 1) % 2
        // Toast.makeText(context, "Library Refreshed", Toast.LENGTH_SHORT).show()
    }


    Scaffold (
        topBar = {
            TopAppBar(
                title = {Text("Library")},
                actions = {
                    // Refresh
                    Button(
                        onClick = {
                            scope.launch {
                                Log.d("Library", "Refreshing library")
                                scope.launch {
                                    refreshLibrary(navController, library, userPref.getString("libraryPath", "")!!.toUri(), context)}
                                }
                        },
                        shape = ShapeDefaults.Small,
                        colors = buttonColors(
                            containerColor = Color.Transparent
                        )
                    ) {
                        Icon(Icons.Filled.Refresh, contentDescription = "Refresh", tint = Color.White)
                    }
                    // Search
                    Button(
                        onClick = {
                            Toast.makeText(context, "Search", Toast.LENGTH_SHORT).show()
                        },
                        shape = ShapeDefaults.Small,
                        colors = buttonColors(
                            containerColor = Color.Transparent
                        )
                    ) {
                        Icon(Icons.Filled.Search, contentDescription = "Search", tint = Color.White)
                    }
                    // Grid/List
                    Button(
                        onClick = {
                            sort = (sort + 1) % 2
                        },
                        shape = ShapeDefaults.Small,
                        colors = buttonColors(
                            containerColor = Color.Transparent
                        )) {
                        if (sort == 0) {
                            Icon(
                                CustomIcons.gridView(),
                                contentDescription = "Search",
                                tint = Color.White,
                                modifier = Modifier.fillMaxSize(.11f)
                            )
                        } else {
                            Icon(
                                Icons.Filled.Menu,
                                contentDescription = "Search",
                                tint = Color.White
                            )
                        }
                    }
                }
            )
        },
        content = {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                if (authors.isNotEmpty()) {
                    Pager(
                        navController,
                        authors,
                        books,
                        sort == 0
                    )
                }
                else {
                    Text("No Books Found")
                }
            }
        }
    )

}

suspend fun refreshLibrary(navController: NavController, library: LibraryDatabase, path: Uri, context: Context) {
    val bookDao = library.bookDao()
    val documentFile: DocumentFile?
    try {
        documentFile = DocumentFile.fromTreeUri(context, path)
    } catch (_: IllegalArgumentException) {
        Toast.makeText(context, "No Library Path Specified, Please go to the Settings Page <3", Toast.LENGTH_SHORT).show()
        return
    }
    val hasPermission = context.contentResolver.persistedUriPermissions.any {
        it.uri == path && it.isReadPermission
    }
    if (!hasPermission) {
        navController.navigate("settings")
        return
    }
    if (documentFile != null && documentFile.isDirectory) {
        documentFile.listFiles().forEach { author ->
            if (author.isDirectory)
            {
                // Log.d("Refresh Library", "Author: ${author.name}")
                author.listFiles().forEach {collection ->
                    if (collection.isDirectory)
                    {
                        // Log.d("Refresh Library", "\t Collection: ${collection.name}")
                        collection.listFiles().forEach { book ->
                            if (book.isFile && book.name!!.split(".")[1] == "m4b") {
                                var found = bookDao.getBookByAuthorAndTitle(author.name!!, book.name!!.split('.')[0])
                                if (found == null) {
                                    found = Book(
                                        author = author.name!!,
                                        title = book.name!!.split('.')[0],
                                        filePath = book.uri.toString(),
                                        collection = collection.name!!
                                    )
                                    // Log.d("Refresh Library", "\t\t Inserted $found")
                                    bookDao.insertBook(found)
                                }
                                else {
                                    // Log.d("Refresh Library", "\t\t Books Found: $found")
                                }
                            }
                        }

                    }
                }
            }
        }
    }


}

@SuppressLint("MutableCollectionMutableState")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Pager(navController: NavController, authors: List<String>, books: List<Book>, isGrid: Boolean) {
    val pagerState = rememberPagerState(pageCount = {authors.size})
    var selectedTab by remember { mutableIntStateOf(0) }
    var collections by remember { mutableStateOf(mutableMapOf<String, List<Book>>()) }
    Column (
        Modifier.fillMaxHeight()){
        ScrollableTabRow(
            selectedTabIndex = selectedTab
        ) {
            authors.forEachIndexed { i, author ->
                Tab(
                    text = { Text(author) },
                    selected = selectedTab == i,
                    onClick = {
                        selectedTab = i
                    }
                )
            }
        }
        LaunchedEffect(selectedTab) {
            pagerState.animateScrollToPage(selectedTab)
        }
        LaunchedEffect(pagerState.currentPage) {
            selectedTab = pagerState.currentPage
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.DarkGray),
            verticalAlignment = Alignment.Top
        ) { _ ->
            collections = books
                .filter { it.author == authors[selectedTab] }
                .groupBy { it.collection } as MutableMap<String, List<Book>>
            Column {
                collections.forEach { (collection, books) ->
                    CollectionTab(
                        collection,
                        books,
                        isGrid,
                        navController
                    )
                }
            }
        }
    }
}

@Composable
fun CollectionTab(
    collection: String,
    books: List<Book>,
    isGrid: Boolean,
    navController: NavController
) {
    var hidden by remember { mutableStateOf(false) }
    Column {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .clickable { hidden = !hidden }
        ){
            Text(
                text = collection,
                color = Color.Black
            )
            if (hidden) {
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "",
                    tint = Color.Black)
            }
            else {
                Icon(
                    Icons.Filled.KeyboardArrowDown,
                    contentDescription = "",
                    tint = Color.Black
                )
            }
        }
        if (!hidden) {
            if (isGrid) {
                LazyVerticalGrid(columns = GridCells.Adaptive(135.dp)) {
                    items(books.size) {
                        Column(
                            modifier = Modifier.clickable {
                                // Go to AudioPlayer
                                navController.navigate("audioPlayer/${books[it].author}/${books[it].title}")
                            }
                        ) {
                            if (books[it].coverImage != ""){
                                AsyncImage(
                                    model = books[it].coverImage,
                                    contentDescription = "",
                                    placeholder = ColorPainter(Color.Black),
                                    error = ColorPainter(Color.Black),
                                    modifier = Modifier.requiredWidthIn(128.dp)
                                )
                            }
                            else {
                                AsyncImage(
                                    model = "https://lgimages.s3.amazonaws.com/nc-md.gif",
                                    contentDescription = "",
                                    placeholder = ColorPainter(Color.Black),
                                    error = ColorPainter(Color.Black),
                                    modifier = Modifier.requiredWidthIn(128.dp)
                                )
                            }

                            Text(text = books[it].title.split(',')[0])
                        }
                    }
                }
            }
            else {
                books.forEach { book ->
                    Text(
                        text = book.title.split(',')[0],
                        modifier = Modifier.clickable {
                            // Go to AudioPlayer
                            navController.navigate("audioPlayer/${book.author}/${book.title}")
                        }
                    )
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AudioPlayerPage(title: String, author: String, context: Context, library: LibraryDatabase, navController: NavController, scope: CoroutineScope = rememberCoroutineScope()) {
    val bookDao = library.bookDao()
    var book by remember { mutableStateOf<Book?>(null) }
    var progress by remember { mutableFloatStateOf(0f) }
    val mediaPlayer = remember { MediaPlayer() }
    var isPlaying by remember { mutableStateOf(false) }
    val chapters = remember { mutableListOf<String>() }
    val chapterTimes = remember { mutableListOf<Pair<Long, Long>>() }
    val currentChapter = remember { mutableIntStateOf(0) }
    val isDisposing = remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    var STT by remember { mutableStateOf(false) }
    val whisper = Whisper()

    // Update progress every second
    val handler = remember { Handler(Looper.getMainLooper()) }
    val updateProgressTask = object : Runnable {
        override fun run() {
            if (isDisposing.value) {
                return
            }
            progress = ((mediaPlayer.currentPosition.toFloat() - chapterTimes[currentChapter.intValue].first) / (chapterTimes[currentChapter.intValue].second - chapterTimes[currentChapter.intValue].first))
            handler.postDelayed(this, 1000) // Update every second
            if (mediaPlayer.currentPosition >= chapterTimes[currentChapter.intValue].second) {
                currentChapter.intValue += 1
            }
        }
    }

    // Update book in database
    fun updateBook() {
        scope.launch {
            Log.d("AudioPlayerPage", "Updating Database")
            bookDao.updateLastPlayed(
                author, title,
                mediaPlayer.currentPosition.toLong()
            )
            bookDao.updateProgress(
                author, title,
                ((mediaPlayer.currentPosition.toFloat() - chapterTimes[currentChapter.intValue].first) / (chapterTimes[currentChapter.intValue].second - chapterTimes[currentChapter.intValue].first))
            )
            Toast.makeText(context, "Updated Database", Toast.LENGTH_SHORT).show()
        }
    }


    // On load, grab book from database
    LaunchedEffect(key1 = author, key2 = title) {
        book = bookDao.getBookByAuthorAndTitle(author, title)
    }

    // When book changes, update media player
    LaunchedEffect(key1 = book) {
        // If book is null, return
        if (book == null) {
            return@LaunchedEffect
        }

        // Grab chapters chapters & times
        val link = FFmpegKitConfig.getSafParameterForRead(context, book!!.filePath.toUri())
        val session = FFprobeKit.execute("-i $link -print_format json -show_chapters -loglevel quiet")
        val json = JSONObject( session.output ).getJSONArray("chapters")
        for (i in 0 until json.length()) {
            val chapter = json.getJSONObject(i)
            // Get chapter title
            chapters.add(chapter.getJSONObject("tags").getString("title"))
            // Get chapter start and end time
            chapterTimes.add(Pair(chapter.getLong("start"), chapter.getLong("end")))
        }
        // Set up media player
        try {
            mediaPlayer.setDataSource(context, book!!.filePath.toUri())
            mediaPlayer.prepareAsync()
            mediaPlayer.setOnPreparedListener {
                // Start playback when prepared
                mediaPlayer.start()
                isPlaying = true
                mediaPlayer.seekTo(book!!.lastPlayed.toInt())
            }
            } catch (e: Exception) {
            Log.e("AudioPlayerPage", "Error initializing MediaPlayer", e)
        }
        // Start updating progress
        handler.post(updateProgressTask)
    }
    LaunchedEffect(key1 = currentChapter) {
        if (chapterTimes.isNotEmpty()) {
            progress = ((mediaPlayer.duration - chapterTimes[currentChapter.intValue].first)/(chapterTimes[currentChapter.intValue].second - chapterTimes[currentChapter.intValue].first)).toFloat()
        }
    }

    // On dispose, release media player
    DisposableEffect(Unit) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            if (destination.route != "audioPlayer/{author}/{title}") {
                updateBook()
            }
        }
        navController.addOnDestinationChangedListener(listener)

        onDispose {
            navController.removeOnDestinationChangedListener(listener)
            isDisposing.value = true
            FFmpegKit.cancel()
            mediaPlayer.release()
            handler.removeCallbacks(updateProgressTask)
        }
    }

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(title.split(',')[0]) },
                actions = {
                    Button(
                        onClick = {
                            expanded = true
                        },
                        colors = buttonColors(Color.Transparent)
                    ) {
                        Icon(
                            Icons.Filled.Menu,
                            contentDescription = "Menu",
                            tint = Color.White
                        )
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            // Edit
                            DropdownMenuItem(
                                text = {
                                    Row {
                                        Icon(Icons.Default.Edit, "")
                                        Text("Edit")
                                    }
                                       },
                                onClick = {
                                    expanded = false
                                }
                            )
                            // Characters
                            DropdownMenuItem(
                                text = {
                                    Row {
                                        Icon(Icons.Default.Person, "")
                                        Text("Characters")
                                    }
                                       },
                                onClick = {
                                    expanded = false
                                }
                            )
                            // Speech-to-Text
                            DropdownMenuItem(
                                text = {
                                    Row (
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Checkbox(checked = STT, onCheckedChange = { STT = it })
                                        Text("Speech-to-Text")
                                    }
                                       },
                                onClick = {
                                    whisper.speechToText(book!!.filePath)
                                    expanded = false
                                }
                            )


                        }
                    }
                }
            )
        },
        content = {
            Surface(
                modifier = Modifier.padding(it)
            ) {
                AsyncImage(
                    model = if(book?.coverImage != "") book?.coverImage else "https://lgimages.s3.amazonaws.com/nc-md.gif" ,
                    contentDescription = "",
                    placeholder = ColorPainter(Color.Black),
                    error = ColorPainter(Color.Black),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        bottomBar = {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text( if (chapters.isNotEmpty()) "Chapter ${chapters[currentChapter.intValue]}" else "Chapter ")
                // Top
                Row {
                    // Previous Chapter
                    Button(
                        onClick = {
                            if (mediaPlayer.currentPosition < chapterTimes[currentChapter.intValue].first + 2000 && currentChapter.intValue > 0) {
                                currentChapter.intValue -= 1
                                mediaPlayer.seekTo(chapterTimes[currentChapter.intValue].first.toInt())
                            }
                            else {
                                mediaPlayer.seekTo(chapterTimes[currentChapter.intValue].first.toInt())
                            }
                        },
                        colors = ButtonDefaults.buttonColors(Color.Transparent)
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Previous Chapter",
                            tint = Color.White
                        )
                    }
                    // Slider
                    Slider(
                        value = progress,
                        onValueChange = {
                            progress = it
                            val pos = chapterTimes[currentChapter.intValue]
                            // P_0 + (progress)*(pos_1 - pos_0)
                            mediaPlayer.seekTo((pos.first + (it * (pos.second - pos.first))).toInt())
                        },
                        modifier = Modifier.weight(1f)
                    )
                    // Next Chapter
                    Button(
                        onClick = {
                            if (currentChapter.intValue + 1 <= chapterTimes.size) {
                                currentChapter.intValue += 1
                                mediaPlayer.seekTo(chapterTimes[currentChapter.intValue].first.toInt())
                            }
                            else {
                                mediaPlayer.seekTo(mediaPlayer.duration)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(Color.Transparent)
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = "Next Chapter",
                            tint = Color.White
                        )
                    }
                }
                // Bottom
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    // Back Step (30 seconds)
                    Button(onClick = {
                        val newPos = mediaPlayer.currentPosition - 30000
                        // If user clicks too far back, go to beginning
                        if (newPos < 0) mediaPlayer.seekTo(0)
                        // Otherwise, go to new position
                        else {
                            // If user clicks jumps to previous chapter, change chapter
                            if (newPos < chapterTimes[currentChapter.intValue].first) currentChapter.intValue -= 1
                            mediaPlayer.seekTo(newPos)
                        }

                    },
                        colors = buttonColors(Color.Transparent)
                    ) {
                        Icon(
                            CustomIcons.replay_30(),
                            contentDescription = "Back Step",
                            tint = Color.White,
                            modifier = Modifier.width(35.dp)
                        )
                    }
                    // Play/Pause Button
                    Button(
                        onClick = {
                            if (isPlaying) {
                                mediaPlayer.pause()
                            } else {
                                mediaPlayer.start()
                            }
                            isPlaying = !isPlaying
                        },
                        colors = buttonColors(Color.Transparent)
                    ) {
                        Icon(
                            if (isPlaying) CustomIcons.pause() else Icons.Filled.PlayArrow,
                            contentDescription = "Play/Pause",
                            tint = Color.White,
                            modifier = Modifier.width(35.dp)
                        )
                        // Forward Step (30 seconds)
                        Button(
                            onClick = {
                                val newPos = mediaPlayer.currentPosition + 30000
                                // If user clicks too far forward, go to end
                                if (newPos > mediaPlayer.duration) mediaPlayer.seekTo(mediaPlayer.duration)
                                // Otherwise, go to new position
                                else {
                                    // If user clicks jumps to previous chapter, change chapter
                                    if (newPos < chapterTimes[currentChapter.intValue].second) currentChapter.intValue += 1
                                    mediaPlayer.seekTo(newPos)
                                }
                            },
                            colors = buttonColors(Color.Transparent)
                        ) {
                            Icon(
                                CustomIcons.forward_30(),
                                contentDescription = "Forward Step",
                                tint = Color.White,
                                modifier = Modifier.width(35.dp)
                            )
                        }
                    }
                }
            }
        }
    )
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsPage(){
    val context = LocalContext.current
    val userPref = context.getSharedPreferences("userPref", Context.MODE_PRIVATE)
    val dirPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocumentTree(),
        onResult = { uri: Uri? ->
            if (uri != null) {
                context.contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                userPref.edit().putString("libraryPath", uri.toString()).apply()
            }
        }
    )
    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text("Settings") }
            )
        },
        content = {
            Column (
                modifier = Modifier.padding(it)
            ){
                Row (verticalAlignment = Alignment.CenterVertically){
                    Text("Library Path: ")
                    Button(onClick = {
                        // Open file picker
                        dirPicker.launch(userPref.getString("libraryPath", "")?.toUri())
                    }){
                        Icon(Icons.Filled.Add, contentDescription = "Add Library")
                    }
                }
            }
        }
    )
}

