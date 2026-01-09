// com.example.mook.presentation.settings.SettingsScreen.kt
package com.example.mook.presentation.settings

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mook.presentation.library.FolderPickerActivity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBackClick: () -> Unit,
//    viewModel: SettingsViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        SettingsContent(
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun SettingsContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Playback Settings
        Card(
            modifier = Modifier.fillMaxWidth()
        )
        {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Playback",
                    style = MaterialTheme.typography.titleLarge
                )

                // Sleep Timer
                var sleepTimer by remember { mutableStateOf("Off") }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Sleep Timer")
                    Text(sleepTimer, color = MaterialTheme.colorScheme.primary)
                }

                Divider()

                // Rewind/Fast Forward
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Rewind")
                    Text("30 sec", color = MaterialTheme.colorScheme.primary)
                }

                Divider()

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Fast Forward")
                    Text("30 sec", color = MaterialTheme.colorScheme.primary)
                }
            }
        }

        // Audio Settings
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Audio",
                    style = MaterialTheme.typography.titleLarge
                )

                var volumeBoost by remember { mutableStateOf(false) }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Volume Boost")
                    Switch(
                        checked = volumeBoost,
                        onCheckedChange = { volumeBoost = it }
                    )
                }

                Divider()

                var skipSilence by remember { mutableStateOf(false) }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Skip Silence")
                    Switch(
                        checked = skipSilence,
                        onCheckedChange = { skipSilence = it }
                    )
                }
            }
        }

        // Library Settings
        Card(
            modifier = Modifier.fillMaxWidth()
        )
        {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                val activity = LocalContext.current as Activity

                Text(
                    text = "Library",
                    style = MaterialTheme.typography.titleLarge
                )

                // In SettingsScreen.kt, update the folder picker button:
                Button(
                    onClick = {
                        // Launch folder picker activity
                        val intent = FolderPickerActivity.createIntent(activity)
                        activity.startActivityForResult(
                            intent,
                            FolderPickerActivity.REQUEST_CODE_FOLDER_PICKER
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Folder, null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Choose Library Folder")
                }

                Divider()

                Button(
                    onClick = { /* TODO: Implement scan */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Scan for Audiobooks")
                }
            }
        }

        // App Info
        Card(
            modifier = Modifier.fillMaxWidth()
        )
        {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "App Info",
                    style = MaterialTheme.typography.titleLarge
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Version")
                    Text("1.0.0", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Build")
                    Text("100", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }

        // Support
        Card(
            modifier = Modifier.fillMaxWidth()
        )
        {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Support",
                    style = MaterialTheme.typography.titleLarge
                )

                OutlinedButton(
                    onClick = { /* TODO: Feedback */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Send Feedback")
                }

                OutlinedButton(
                    onClick = { /* TODO: Rate app */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Rate App")
                }
            }
        }
    }
}

// Simple ViewModel for Settings
class SettingsViewModel {
    // We'll add settings logic here later
}