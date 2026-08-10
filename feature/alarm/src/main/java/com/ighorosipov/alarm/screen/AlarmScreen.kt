package com.ighorosipov.alarm.screen

import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.ighorosipov.alarm.viewmodel.AlarmViewModel
import com.ighorosipov.core_presentation.components.MainAppState

@Composable
fun AlarmScreen(
    appState: MainAppState,
    viewModel: AlarmViewModel = hiltViewModel(),
    showSnackbar: (
        String,
        SnackbarDuration,
        String?,
        actionPerformed: () -> Unit,
    ) -> Unit
) {

}