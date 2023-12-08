package com.example.mook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.mook.database.LibraryBook
import com.example.mook.database.LibraryDatabase
import com.example.mook.database.LibraryViewModel
import com.example.mook.dialogs.BookDialog
import com.example.mook.fragments.AddBooks
import com.example.mook.fragments.Library
import com.example.mook.fragments.Settings
import com.example.mook.fragments.Trending
import com.example.mook.navigation.Drawer
import com.example.mook.ui.theme.MookTheme



class MainActivity : ComponentActivity() {
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            LibraryDatabase::class.java,
            "library.db"
        ).build()
    }
    private val viewModel by viewModels<LibraryViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory{
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return LibraryViewModel(db.dao) as T
                }
            }
        }
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            MookTheme {
                val state by viewModel.state.collectAsState()
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
                        composable("library")   { Library(state, navController)}
                        composable("trending")  { Trending() }
                        composable("settings")  { Settings() }
                        composable("add books") { AddBooks(state, viewModel, applicationContext) }
                    }
                }

            }
        }
    }
}

// ---------------------------Nav Drawer---------------------------

// ---------------------------Preview---------------------------
@Preview(showBackground = true)
@Composable
fun prev() {
    MookTheme {
        BookDialog(LibraryBook("Harry", "Rowling"), onEvent = {}, modifier = Modifier)
    }
}