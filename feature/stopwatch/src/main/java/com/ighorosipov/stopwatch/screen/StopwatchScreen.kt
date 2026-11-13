package com.ighorosipov.stopwatch.screen

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ighorosipov.core_presentation.components.MainAppState
import com.ighorosipov.core_presentation.components.handleGlobalIntent
import com.ighorosipov.core_presentation.theme.LocalCustomColorsPalette
import com.ighorosipov.stopwatch.components.LapItem
import com.ighorosipov.stopwatch.components.LeftControlButton
import com.ighorosipov.stopwatch.components.RightControlButton
import com.ighorosipov.stopwatch.components.StopwatchDial
import com.ighorosipov.stopwatch.viewmodel.StopwatchViewModel

@Composable
fun StopwatchScreen(
    appState: MainAppState,
    showSnackbar: (
        String,
        SnackbarDuration,
        String?,
        actionPerformed: () -> Unit
    ) -> Unit,
    viewModel: StopwatchViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()

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

    StopwatchContent(
        state = state,
        onStartClick = viewModel::onStart,
        onPauseClick = viewModel::onPause,
        onResumeClick = viewModel::onResume,
        onLapClick = viewModel::onLap,
        onResetClick = viewModel::onReset
    )
}

@Composable
private fun StopwatchContent(
    state: StopwatchState,
    onStartClick: () -> Unit,
    onPauseClick: () -> Unit,
    onResumeClick: () -> Unit,
    onLapClick: () -> Unit,
    onResetClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalCustomColorsPalette.current.background)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        StopwatchDial(
            timeMs = state.elapsedMs,
            progress = state.progress,
            isRunning = state.isRunning,
            modifier = Modifier.size(280.dp)
        )

        Spacer(modifier = Modifier.height(48.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            LeftControlButton(
                state = state,
                onLapClick = onLapClick,
                onResetClick = onResetClick
            )

            RightControlButton(
                state = state,
                onStartClick = onStartClick,
                onPauseClick = onPauseClick,
                onResumeClick = onResumeClick
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            reverseLayout = true
        ) {
            items(state.laps, key = { it.index }) { lap ->
                LapItem(lap = lap)
            }
        }
    }
}

private fun handleIntent(
    appState: MainAppState,
    context: Context,
    intent: StopwatchScreenIntent
) {
    //todo
}