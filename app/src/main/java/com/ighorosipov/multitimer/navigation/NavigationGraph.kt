package com.ighorosipov.multitimer.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.ighorosipov.alarm.navigation.alarmGraph
import com.ighorosipov.core_presentation.components.MainAppState
import com.ighorosipov.core_presentation.navigation.Routes
import com.ighorosipov.stopwatch.navigation.stopwatchGraph
import com.ighorosipov.timer.navigation.timerGraph
import com.ighorosipov.world_time.navigation.worldTimeGraph

@Composable
fun NavigationGraph(
    paddingValues: PaddingValues,
    appState: MainAppState,
    showSnackbar: (
        String,
        SnackbarDuration,
        String?,
        actionPerformed: () -> Unit
    ) -> Unit,
    startDestination: Routes
) {

    NavHost(
        navController = appState.navController,
        startDestination = startDestination,
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {

        alarmGraph(
            appState = appState,
            showSnackbar = showSnackbar
        )

        worldTimeGraph(
            appState = appState,
            showSnackbar = showSnackbar
        )

        stopwatchGraph(
            appState = appState,
            showSnackbar = showSnackbar
        )

        timerGraph(
            appState = appState,
            showSnackbar = showSnackbar
        )

    }
}