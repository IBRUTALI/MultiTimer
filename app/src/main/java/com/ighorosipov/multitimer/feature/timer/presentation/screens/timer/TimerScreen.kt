package com.ighorosipov.multitimer.feature.timer.presentation.screens.timer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.ighorosipov.multitimer.feature.timer.presentation.components.ItemTimer
import com.ighorosipov.multitimer.feature.timer.presentation.services.timer.TimerService.Companion.INTENT_UPDATE_TIMER
import com.ighorosipov.multitimer.feature.timer.presentation.services.timer.TimerServiceConnection
import com.ighorosipov.multitimer.ui.components.SystemBroadcastReceiver
import com.ighorosipov.multitimer.ui.components.button.BaseActionButton
import com.ighorosipov.multitimer.ui.components.navigation.Screen

@Composable
fun TimerScreen(
    modifier: Modifier,
    navController: NavController,
    viewModel: TimerViewModel = hiltViewModel(),
) {

    val context = LocalContext.current
    val connection = remember {
        TimerServiceConnection(context)
    }
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        connection.bind()
    }

    DisposableEffect(Unit) {

        onDispose {
            connection.unbind()
        }
    }

    SystemBroadcastReceiver(systemAction = INTENT_UPDATE_TIMER) { intent ->
        if (intent?.action == INTENT_UPDATE_TIMER) {
            val extras = intent.extras
            val time = extras?.getLong(INTENT_UPDATE_TIMER) ?: 0
            viewModel.onEvent(TimerScreenEvent.UpdateTimer(time))
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        if (state.timers.isEmpty()) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center),
                text = "Нет таймеров"
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .align(Alignment.Center)
            ) {
                items(state.timers) { timer ->
                    ItemTimer(
                        time = timer.timeString,
                        onItemClick = {
                            navController.navigate(Screen.TimerGraph.TimerDetails().route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        },
                        onPlayClick = {
                            connection.startTimer()
                            viewModel.onEvent(TimerScreenEvent.StartTimer)
                        },
                        onPauseClick = {
                            connection.pauseTimer(timer.id)
                            viewModel.onEvent(TimerScreenEvent.PauseTimer)
                        },
                        onStopClick = {
                            connection.stopTimer(timer.id)
                            viewModel.onEvent(TimerScreenEvent.StartTimer)
                        }
                    )
                }
            }
        }
        BaseActionButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(6.dp),
            imageVector = Icons.Filled.Add,
            onClick = {
                navController.navigate(Screen.TimerGraph.AddTimer().route)
            }
        )
    }

}