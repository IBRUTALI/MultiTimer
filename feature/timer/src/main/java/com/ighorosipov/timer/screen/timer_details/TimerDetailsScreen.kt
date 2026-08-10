package com.ighorosipov.timer.screen.timer_details

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.ighorosipov.core_presentation.components.MainAppState
import com.ighorosipov.core_presentation.components.handleGlobalIntent
import com.ighorosipov.timer.viewmodel.TimerDetailsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerDetailsScreen(
    appState: MainAppState,
    viewModel: TimerDetailsViewModel = hiltViewModel(),
    showSnackbar: (
        String,
        SnackbarDuration,
        String?,
        actionPerformed: () -> Unit,
    ) -> Unit
) {
    val context = LocalContext.current
    val state = viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.globalIntents.collect { intent ->
            handleGlobalIntent(
                appState = appState,
                context = context,
                showSnackbar = showSnackbar,
                intent = intent
            )
        }
    }

    LaunchedEffect(Unit) {
        viewModel.intents.collect { intent ->
            handleIntent(
                appState = appState,
                context = context,
                intent = intent
            )
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {

    }
}

private fun handleIntent(
    appState: MainAppState,
    context: Context,
    intent: TimerDetailsScreenIntent
) {
    //todo
}