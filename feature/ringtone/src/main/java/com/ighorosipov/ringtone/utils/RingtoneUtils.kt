package com.ighorosipov.ringtone.utils

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.ighorosipov.domain.ringtone.model.Ringtone

@Composable
fun CustomRingtonePicker(
    onAddRingtoneClick: (Ringtone) -> Unit
) {
    val context = LocalContext.current

    val customAudioLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri ?: return@rememberLauncherForActivityResult

        val title = getDisplayName(context, uri)
            ?: uri.lastPathSegment
            ?: "Custom Music"

        onAddRingtoneClick(
            Ringtone(
                title = title,
                uri = uri.toString()
            )
        )
    }

    Button(
        onClick = {
            customAudioLauncher.launch("audio/*")
        }
    ) {
        Text("Выбрать аудио")
    }
}

private fun getDisplayName(
    context: Context,
    uri: Uri,
): String? {
    return context.contentResolver.query(
        uri,
        arrayOf(OpenableColumns.DISPLAY_NAME),
        null,
        null,
        null
    )?.use { cursor ->
        val nameIndex = cursor.getColumnIndex(
            OpenableColumns.DISPLAY_NAME
        )

        if (cursor.moveToFirst() && nameIndex >= 0) {
            cursor.getString(nameIndex)
        } else {
            null
        }
    }
}