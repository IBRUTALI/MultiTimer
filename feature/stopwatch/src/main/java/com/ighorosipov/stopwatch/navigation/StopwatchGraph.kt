package com.ighorosipov.stopwatch.navigation

import androidx.compose.material3.SnackbarDuration
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ighorosipov.core_presentation.components.MainAppState
import com.ighorosipov.core_presentation.navigation.Routes
import com.ighorosipov.stopwatch.screen.StopwatchScreen

fun NavGraphBuilder.stopwatchGraph(
    appState: MainAppState,
    showSnackbar: (
        String,
        SnackbarDuration,
        String?,
        actionPerformed: () -> Unit
    ) -> Unit
) {
    navigation<Routes.StopwatchGraph>(
        startDestination = Routes.StopwatchScreen,
    ) {
        composable<Routes.StopwatchScreen> {
            StopwatchScreen(
                appState = appState,
                showSnackbar = showSnackbar
            )
        }
    }
}