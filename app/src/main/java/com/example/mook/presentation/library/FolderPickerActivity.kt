// com.example.mook.presentation.library.FolderPickerActivity.kt
package com.example.mook.presentation.library

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.DocumentsContract
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.mook.ui.theme.MookTheme
import androidx.core.content.edit
import com.example.mook.data.local.datasource.SettingsDataSourceImpl
import dagger.hilt.android.AndroidEntryPoint

// FolderPickerActivity.kt - Simplified version
@AndroidEntryPoint
class FolderPickerActivity : ComponentActivity() {

    companion object {
        const val REQUEST_CODE_FOLDER_PICKER = 1001
        const val EXTRA_SELECTED_URI = "selected_uri"

        fun createIntent(context: Activity): Intent {
            return Intent(context, FolderPickerActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MookTheme {
                FolderPickerScreen(
                    onPickFolder = { launchFolderPicker() },
                    onCancel = { finish() }
                )
            }
        }
    }

    private fun launchFolderPicker() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE).apply {
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or
                    Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
        }

        startActivityForResult(intent, REQUEST_CODE_FOLDER_PICKER)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_FOLDER_PICKER) {
            if (resultCode == RESULT_OK) {
                val uri = data?.data
                if (uri != null) {
                    // Take persistable permission
                    contentResolver.takePersistableUriPermission(
                        uri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )

                    // Just return the URI - ViewModel will handle saving
                    val resultIntent = Intent().apply {
                        putExtra(EXTRA_SELECTED_URI, uri.toString())
                    }
                    setResult(RESULT_OK, resultIntent)
                }
            } else {
                setResult(RESULT_CANCELED)
            }
            finish()
        }
    }

    @Composable
    fun FolderPickerScreen(
        onPickFolder: () -> Unit,
        onCancel: () -> Unit
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    imageVector = Icons.Default.Folder,
                    contentDescription = "Folder",
                    modifier = Modifier.size(80.dp),
                    tint = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = "Select Audiobook Folder",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Choose the folder where your audiobooks are stored. Mook will scan this folder and all subfolders for audiobook files.",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.weight(1f))

                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = onPickFolder,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Choose Folder")
                    }

                    TextButton(
                        onClick = onCancel,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Cancel")
                    }
                }
            }
        }
    }
}