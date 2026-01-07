package com.example.mook

import android.Manifest.permission.MANAGE_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.database.LibraryDatabase
import com.example.mook.ui.theme.MookTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    @SuppressLint("QueryPermissionsNeeded")
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val scope = rememberCoroutineScope()
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            // this.deleteDatabase("library_database")
            val library = LibraryDatabase.getDatabase(this)

            val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
            intent.data = Uri.parse("package:$packageName")

            val packageManager: PackageManager = packageManager
            if (intent.resolveActivity(packageManager) != null && !Environment.isExternalStorageManager()) {
                startActivity(intent)
            } else {
                // Log.d("Permissions", "Bananas")
            }
            MookTheme {
                NavDrawer(
                    drawerState = drawerState,
                    navController = navController,
                    scope = scope,
                    content = {
                        NavHost(navController = navController, startDestination = "library") {
                            composable("library") {
                                LibraryPage(navController, library)
                            }
                            composable("settings") {
                                SettingsPage()
                            }
                            composable("audioPlayer/{author}/{title}") {
                                AudioPlayerPage(
                                    author = it.arguments?.getString("author") ?: "",
                                    title = it.arguments?.getString("title") ?: "",
                                    context = LocalContext.current,
                                    library = library,
                                    navController = navController
                                )
                            }
                        }
                    }
                )
            }
        }
    }
@Composable
fun NavDrawer(drawerState: DrawerState, navController: NavController, scope: CoroutineScope, content: @Composable () -> Unit = {}) {
    var selectedItem by remember { mutableIntStateOf(0) }
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                NavigationDrawerItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                    label = { Text("Library") },
                    selected = selectedItem == 0,
                    onClick = {
                        selectedItem = 0
                        navController.navigate("library")
                        scope.launch {
                            delay(300)
                            drawerState.close()
                        }
                    }
                )
                HorizontalDivider()
                ModalDrawerSheet {
                    NavigationDrawerItem(
                        icon = { Icon(Icons.Filled.Settings, contentDescription = "Settings") },
                        label = { Text("Settings") },
                        selected = selectedItem == 1,
                        onClick = {
                            selectedItem = 1
                            navController.navigate("settings")
                            scope.launch {
                                delay(300)
                                drawerState.close()
                            }
                        }
                    )
                }
            }
        },
        gesturesEnabled = true
    ) {
        content()
    }
}


}