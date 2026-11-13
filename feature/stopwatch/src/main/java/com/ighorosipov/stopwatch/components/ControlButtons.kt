package com.ighorosipov.stopwatch.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ighorosipov.core_presentation.theme.LocalCustomColorsPalette
import com.ighorosipov.core_presentation.theme.MultiTimerTheme
import com.ighorosipov.domain.stopwatch.model.StopwatchStatus
import com.ighorosipov.stopwatch.screen.StopwatchState

@Composable
fun LeftControlButton(
    state: StopwatchState,
    onLapClick: () -> Unit,
    onResetClick: () -> Unit
) {
    when {
        state.isIdle -> CircleButton(
            text = "Lap",
            enabled = false,
            containerColor = LocalCustomColorsPalette.current.surfaceVariant,
            contentColor = LocalCustomColorsPalette.current.onSurfaceVariant,
            onClick = {}
        )

        state.isRunning -> CircleButton(
            text = "Lap",
            enabled = true,
            containerColor = LocalCustomColorsPalette.current.surfaceVariant,
            contentColor = LocalCustomColorsPalette.current.onSurfaceVariant,
            onClick = onLapClick
        )

        state.isPaused -> CircleButton(
            text = "Reset",
            enabled = true,
            containerColor = LocalCustomColorsPalette.current.surfaceVariant,
            contentColor = LocalCustomColorsPalette.current.onSurfaceVariant,
            onClick = onResetClick
        )
    }
}

@Composable
fun RightControlButton(
    state: StopwatchState,
    onStartClick: () -> Unit,
    onPauseClick: () -> Unit,
    onResumeClick: () -> Unit
) {
    when {
        state.isIdle -> CircleButton(
            text = "Start",
            enabled = true,
            containerColor = LocalCustomColorsPalette.current.primary,
            contentColor = LocalCustomColorsPalette.current.onPrimary,
            onClick = onStartClick
        )

        state.isRunning -> CircleButton(
            text = "Stop",
            enabled = true,
            containerColor = LocalCustomColorsPalette.current.error,
            contentColor = LocalCustomColorsPalette.current.onError,
            onClick = onPauseClick
        )

        state.isPaused -> CircleButton(
            text = "Resume",
            enabled = true,
            containerColor = LocalCustomColorsPalette.current.primary,
            contentColor = LocalCustomColorsPalette.current.onPrimary,
            onClick = onResumeClick
        )
    }
}

@Composable
@Preview
fun LeftControlButtonPreviewLight() {
    MultiTimerTheme(darkTheme = false) {
        LeftControlButton(
            state = StopwatchState(status = StopwatchStatus.IDLE),
            onLapClick = {},
            onResetClick = {}
        )
    }
}

@Composable
@Preview
fun LeftControlButtonPreviewDark() {
    MultiTimerTheme(darkTheme = true) {
        LeftControlButton(
            state = StopwatchState(status = StopwatchStatus.PAUSED, elapsedMs = 15000L),
            onLapClick = {},
            onResetClick = {}
        )
    }
}

@Composable
@Preview
fun RightControlButtonPreviewLight() {
    MultiTimerTheme(darkTheme = false) {
        RightControlButton(
            state = StopwatchState(status = StopwatchStatus.RUNNING, elapsedMs = 25000L),
            onStartClick = {},
            onPauseClick = {},
            onResumeClick = {}
        )
    }
}

@Composable
@Preview
fun RightControlButtonPreviewDark() {
    MultiTimerTheme(darkTheme = true) {
        RightControlButton(
            state = StopwatchState(status = StopwatchStatus.IDLE),
            onStartClick = {},
            onPauseClick = {},
            onResumeClick = {}
        )
    }
}