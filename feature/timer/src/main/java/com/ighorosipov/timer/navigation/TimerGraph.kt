package com.ighorosipov.timer.navigation

import androidx.compose.material3.SnackbarDuration
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ighorosipov.core_presentation.components.MainAppState
import com.ighorosipov.core_presentation.navigation.Routes
import com.ighorosipov.timer.screen.add_timer.AddTimerScreen
import com.ighorosipov.timer.screen.timer.TimerScreen
import com.ighorosipov.timer.screen.timer_details.TimerDetailsScreen

fun NavGraphBuilder.timerGraph(
    appState: MainAppState,
    showSnackbar: (
        String,
        SnackbarDuration,
        String?,
        actionPerformed: () -> Unit
    ) -> Unit
) {
    navigation<Routes.TimerGraph>(
        startDestination = Routes.TimerScreen,
    ) {

        composable<Routes.TimerScreen> {
            TimerScreen(
                appState = appState,
                showSnackbar = showSnackbar
            )
        }

        composable<Routes.AddTimerScreen> {
            AddTimerScreen(
                appState = appState,
                showSnackbar = showSnackbar
            )
        }

        composable<Routes.TimerDetailsScreen> {
            TimerDetailsScreen(
                appState = appState,
                showSnackbar = showSnackbar
            )
        }
    }
}