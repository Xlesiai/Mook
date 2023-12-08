package com.example.mook.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mook.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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
                icon = { Icon(painter = painterResource(id = R.drawable.baseline_menu_book_24), contentDescription = null) },
                label = { Text(text = "Library") },
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
                icon = { Icon(painter = painterResource(id = R.drawable.baseline_trending_up_24), contentDescription = null) },
                label = { Text(text = "Trending") },
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
                icon = { Icon(imageVector = Icons.Default.Settings, contentDescription = null) },
                label = { Text(text = "Settings") },
                selected = false,
                onClick = {
                    navController.navigate("settings")
                },
                modifier = Modifier
            )
        }


    }
}

