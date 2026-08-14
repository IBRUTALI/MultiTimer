package com.ighorosipov.timer.screen.timer

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ighorosipov.core_presentation.components.BaseToolbar
import com.ighorosipov.core_presentation.components.IconParams
import com.ighorosipov.core_presentation.components.IconPosition
import com.ighorosipov.core_presentation.components.MainAppState
import com.ighorosipov.core_presentation.components.handleGlobalIntent
import com.ighorosipov.timer.components.TimerCard
import com.ighorosipov.timer.viewmodel.TimerViewModel
import com.ighorosipov.utils.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerScreen(
    appState: MainAppState,
    viewModel: TimerViewModel = hiltViewModel(),
    showSnackbar: (
        String,
        SnackbarDuration,
        String?,
        actionPerformed: () -> Unit,
    ) -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

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

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        BaseToolbar(
            iconParams = IconParams(
                icon = R.drawable.ic_outline_add,
                iconSize = 24,
                iconPosition = IconPosition.END
            ),
            title = stringResource(R.string.timer),
            onIconClick = viewModel::onAddTimerClick
        )
        if (state.timers.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier,
                    text = stringResource(R.string.no_timers),
                    style = MaterialTheme.typography.titleSmall
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(state.timers, key = { it.id }) { timer ->
                    TimerCard(
                        timerName = timer.name,
                        time = timer.time,
                        isPlaying = true,
                        onPlayPauseClick = {
                            //todo
                        },
                        onStopClick = {
                            // todo
                        },
                        onDeleteClick = {
                            false
                            // todo
                        },
                        onEditClick = {
                            // todo
                        }
                    )
                }
            }
        }
    }

}

private fun handleIntent(
    appState: MainAppState,
    context: Context,
    intent: TimerScreenIntent
) {
    //todo
}