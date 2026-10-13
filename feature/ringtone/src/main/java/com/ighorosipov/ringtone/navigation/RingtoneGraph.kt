package com.ighorosipov.ringtone.navigation

import androidx.compose.material3.SnackbarDuration
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ighorosipov.core_presentation.components.MainAppState
import com.ighorosipov.core_presentation.navigation.Routes
import com.ighorosipov.ringtone.screen.SelectRingtoneScreen

fun NavGraphBuilder.ringtoneGraph(
    appState: MainAppState,
    showSnackbar: (
        String,
        SnackbarDuration,
        String?,
        actionPerformed: () -> Unit
    ) -> Unit
) {
    composable<Routes.SelectRingtoneScreen> {
        SelectRingtoneScreen(
            appState = appState,
            showSnackbar = showSnackbar
        )
    }
}
