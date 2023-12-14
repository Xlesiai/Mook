package com.example.mook.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun BookSettingsDialog() {
    Card {
        Column {
            Row {
                Text("Change PDF")
            }
            Row {
                Text("Change Audio")
            }
            Row {
                Text("Remove",
                    color = Color.Red)
            }
        }
    }

}