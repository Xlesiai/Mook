// com.example.mook.MainActivity.kt
package com.example.mook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mook.presentation.library.LibraryScreen
import com.example.mook.presentation.player.PlayerScreen
import com.example.mook.presentation.settings.SettingsScreen
import com.example.mook.ui.theme.MookTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MookTheme {
                MainApp()
            }
        }
    }
}

@Composable
fun MainApp() {
    val navController = rememberNavController()
    if (BuildConfig.DEBUG) {
        Timber.plant(Timber.DebugTree())
    }
    NavHost(
        navController = navController,
        startDestination = "library"
    ) {
        composable("library") {
            LibraryScreen(
                onBookClick = { bookId ->
                    // Navigate to player with book ID
                    navController.navigate("player/$bookId")
                },
                onSettingsClick = {
                    navController.navigate("settings")
                }
            )
        }

        composable("player/{bookId}") { backStackEntry ->
            val bookId = backStackEntry.arguments?.getString("bookId")
            PlayerScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("settings") {
            SettingsScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}

@Preview
@Composable
fun MainAppPreview() {
    MookTheme {
        MainApp()
    }
}