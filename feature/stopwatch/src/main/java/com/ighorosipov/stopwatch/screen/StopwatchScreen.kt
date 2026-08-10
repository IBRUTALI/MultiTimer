package com.ighorosipov.stopwatch.screen

import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.ighorosipov.core_presentation.components.MainAppState
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

}