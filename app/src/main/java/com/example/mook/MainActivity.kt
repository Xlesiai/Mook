package com.example.mook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mook.ui.theme.MookTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            MookTheme {
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()
                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        ModalDrawerSheet {
                            drawer(navController, scope, drawerState)
                        }
                    }
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = "library"
                    ) {
                        composable("library") {library()}
                        composable("trending") {trending()}
                        composable("settings") {settings()}
                    }
                }

            }
        }
    }
}


@Composable
fun drawer(navController: NavHostController, scope: CoroutineScope, drawerState: DrawerState) {
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

@Composable
fun library() {
    Surface (modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
    ){
        Text(text = "Library Fragment"
        )
    }
}
@Composable
fun trending() {
    Surface (modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
    ){
        Text(text = "Trending Fragment")
    }
}
@Composable
fun settings() {
    Surface (modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
    ){
        Text(text = "Settings Fragment")
    }
}
@Preview(showBackground = true)
@Composable
fun prev() {
    MookTheme {
        library()
    }
}