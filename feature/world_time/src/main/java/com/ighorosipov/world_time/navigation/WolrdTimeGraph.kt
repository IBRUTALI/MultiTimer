package com.ighorosipov.world_time.navigation

import androidx.compose.material3.SnackbarDuration
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ighorosipov.core_presentation.components.MainAppState
import com.ighorosipov.core_presentation.navigation.Routes
import com.ighorosipov.world_time.screen.WorldTimeScreen

fun NavGraphBuilder.worldTimeGraph(
    appState: MainAppState,
    showSnackbar: (
        String,
        SnackbarDuration,
        String?,
        actionPerformed: () -> Unit
    ) -> Unit
) {

    navigation<Routes.WorldTimeGraph>(
        startDestination = Routes.WorldTimeScreen
    ) {
        composable<Routes.WorldTimeScreen> {
            WorldTimeScreen(
                appState = appState,
                showSnackbar = showSnackbar
            )
        }
    }

}