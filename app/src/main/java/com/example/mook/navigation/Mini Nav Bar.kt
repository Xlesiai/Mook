package com.example.mook.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mook.ui.theme.MookTheme


@Composable
fun MiniNav(modifier: Modifier = Modifier, onClick: () -> Unit) {
    var toggle by remember{ mutableStateOf(false) }
    Surface (
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
    ){
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {
            // Edit Button
            IconButton(
                modifier = Modifier
                    .padding(10.dp)
                    .clip(CircleShape),
                onClick = {
                    onClick.invoke()
                    toggle = !toggle
                }
            ) {
                if (toggle)
                {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        modifier = Modifier,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                else{
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        modifier = Modifier,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

            }
        }

    }
}


@Preview(showBackground = true)
@Composable
fun prev() {
    MookTheme {
        MiniNav(Modifier, {})
    }
}