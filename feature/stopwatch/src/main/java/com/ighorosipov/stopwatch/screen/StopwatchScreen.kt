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
import com.ighorosipov.core_presentation.components.rememberSmoothElapsedMs
import com.ighorosipov.core_presentation.permission.NotificationPermissionController
import com.ighorosipov.core_presentation.permission.rememberNotificationPermissionController
import com.ighorosipov.core_presentation.theme.LocalCustomColorsPalette
import com.ighorosipov.stopwatch.components.LapsList
import com.ighorosipov.stopwatch.components.LeftControlButton
import com.ighorosipov.stopwatch.components.RightControlButton
import com.ighorosipov.stopwatch.components.StopwatchDial
import com.ighorosipov.stopwatch.viewmodel.StopwatchViewModel
import com.ighorosipov.utils.R

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

    val permissionController = rememberNotificationPermissionController(
        onGranted = { viewModel.showSnackbar(context.getString(R.string.notification_permission_granted)) },
        onDenied = { viewModel.showSnackbar(context.getString(R.string.notification_permission_denied)) }
    )

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
                intent = intent,
                permissionController = permissionController,
                onPermissionGranted = viewModel::onPermissionGranted
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

    val smoothMs = rememberSmoothElapsedMs(
        isRunning = state.isRunning,
        engineElapsedMs = state.elapsedMs
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalCustomColorsPalette.current.background)
            .padding(horizontal = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            StopwatchDial(
                timeMs = smoothMs,
                progress = if (smoothMs > 0) ((smoothMs % 60_000L) / 60_000f) else 0f,
                isRunning = state.isRunning,
                modifier = Modifier.size(230.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            LapsList(
                laps = state.laps
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.2f),
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
    }
}

private fun handleIntent(
    appState: MainAppState,
    context: Context,
    intent: StopwatchScreenIntent,
    permissionController: NotificationPermissionController,
    onPermissionGranted: () -> Unit
) {
    when(intent) {
        is StopwatchScreenIntent.RequestNotificationPermission -> {
            permissionController.requestPermission {
                onPermissionGranted()
            }
        }
    }
}