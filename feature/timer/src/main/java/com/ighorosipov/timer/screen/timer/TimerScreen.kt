package com.ighorosipov.timer.screen.timer

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ighorosipov.core_presentation.components.BaseActionButton
import com.ighorosipov.core_presentation.components.MainAppState
import com.ighorosipov.core_presentation.components.handleGlobalIntent
import com.ighorosipov.timer.components.ItemTimer
import com.ighorosipov.timer.viewmodel.TimerViewModel
import com.ighorosipov.utils.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerScreen(
    appState: MainAppState,
    viewModel: TimerViewModel = hiltViewModel(),
    showSnackbar: (
        String,
        SnackbarDuration,
        String?,
        actionPerformed: () -> Unit,
    ) -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

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

    LaunchedEffect(Unit) {
        viewModel.intents.collect { intent ->
            handleIntent(
                appState = appState,
                context = context,
                intent = intent
            )
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (state.timers.isEmpty()) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = stringResource(R.string.no_timers),
                style = MaterialTheme.typography.titleSmall
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .align(Alignment.Center)
            ) {
                items(state.timers) { timer ->
                    ItemTimer(
                        time = timer.timeString,
                        onItemClick = {
                            // todo
                        },
                        onPlayClick = {
                            // todo
                        },
                        onPauseClick = {
                            // todo
                        },
                        onStopClick = {
                            // todo
                        }
                    )
                }
            }
        }
        BaseActionButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(6.dp),
            imageVector = Icons.Filled.Add,
            onClick = {
                // todo
            }
        )
    }

}

private fun handleIntent(
    appState: MainAppState,
    context: Context,
    intent: TimerScreenIntent
) {
    //todo
}