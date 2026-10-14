package com.ighorosipov.ringtone.screen

import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ighorosipov.core_presentation.components.BaseContentCard
import com.ighorosipov.core_presentation.components.BaseToolbar
import com.ighorosipov.core_presentation.components.MainAppState
import com.ighorosipov.core_presentation.components.ToolbarIcon
import com.ighorosipov.core_presentation.components.handleGlobalIntent
import com.ighorosipov.ringtone.viewmodel.SelectRingtoneViewModel
import com.ighorosipov.utils.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectRingtoneScreen(
    appState: MainAppState,
    viewModel: SelectRingtoneViewModel = hiltViewModel(),
    showSnackbar: (
        String,
        SnackbarDuration,
        String?,
        actionPerformed: () -> Unit,
    ) -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    val customAudioLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            val cursor = context.contentResolver.query(uri, null, null, null, null)
            val title = cursor?.use { c ->
                val nameIndex = c.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (c.moveToFirst() && nameIndex != -1) c.getString(nameIndex) else null
            } ?: uri.lastPathSegment ?: "Custom Music"
            viewModel.onAddCustomRingtone(title = title, uri = uri.toString())
        }
    }

    LaunchedEffect(Unit) {
        viewModel.globalIntents.collect { intent ->
            handleGlobalIntent(
                appState = appState,
                context = context,
                showSnackbar = showSnackbar,
                intent = intent
            )
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        BaseToolbar(
            startIcons = listOf(
                ToolbarIcon(
                    icon = R.drawable.outline_arrow_back,
                    iconSize = 24,
                    contentDescription = "back",
                    onClick = viewModel::onBackClick
                )
            ),
            endIcons = listOf(
                ToolbarIcon(
                    icon = R.drawable.ic_apply,
                    iconSize = 24,
                    contentDescription = "save",
                    onClick = viewModel::onSaveClick
                )
            ),
            title = "Select Ringtone"
        )

        Spacer(modifier = Modifier.height(16.dp))

        BaseContentCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clickable {
                    customAudioLauncher.launch("audio/*")
                },
            contentPadding = PaddingValues(16.dp),
            content = {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_outline_add),
                            contentDescription = "add",
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = "Add custom music",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.ringtones) { ringtone ->
                val isSelected = state.selectedRingtone?.uri == ringtone.uri
                val isPlaying = state.playingRingtoneUri == ringtone.uri

                BaseContentCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.onRingtoneClick(ringtone)
                        },
                    contentPadding = PaddingValues(12.dp),
                    content = {
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = isSelected,
                                    onClick = { viewModel.onRingtoneClick(ringtone) }
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = ringtone.title,
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.weight(1f),
                                    maxLines = 1
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Icon(
                                    painter = painterResource(
                                        id = if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play
                                    ),
                                    contentDescription = "Preview",
                                    modifier = Modifier
                                        .clickable {
                                            viewModel.onPlayPauseClick(ringtone)
                                        }
                                        .padding(8.dp),
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}
