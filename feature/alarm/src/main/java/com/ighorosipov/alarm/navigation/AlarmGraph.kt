package com.ighorosipov.alarm.navigation

import androidx.compose.material3.SnackbarDuration
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ighorosipov.alarm.screen.AlarmScreen
import com.ighorosipov.core_presentation.components.MainAppState
import com.ighorosipov.core_presentation.navigation.Routes

fun NavGraphBuilder.alarmGraph(
    appState: MainAppState,
    showSnackbar: (
        String,
        SnackbarDuration,
        String?,
        actionPerformed: () -> Unit
    ) -> Unit
) {
    navigation<Routes.AlarmGraph>(
        startDestination = Routes.AlarmScreen,
    ) {

        composable<Routes.AlarmScreen> {
            AlarmScreen(
                appState = appState,
                showSnackbar = showSnackbar
            )
        }
    }
}