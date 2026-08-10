package com.ighorosipov.world_time.screen

import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.ighorosipov.core_presentation.components.MainAppState
import com.ighorosipov.world_time.viewmodel.WorldTimeViewModel

@Composable
fun WorldTimeScreen(
    appState: MainAppState,
    showSnackbar: (
        String,
        SnackbarDuration,
        String?,
        actionPerformed: () -> Unit
    ) -> Unit,
    viewModel: WorldTimeViewModel = hiltViewModel()
) {

}