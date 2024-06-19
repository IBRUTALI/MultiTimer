package com.ighorosipov.multitimer.presentation.screens.timer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.ighorosipov.multitimer.domain.model.Timer
import com.ighorosipov.multitimer.presentation.services.timer.TimerServiceConnection

@Composable
fun TimerScreen(
    modifier: Modifier,
    viewModel: TimerViewModel = hiltViewModel(),
) {

    val context = LocalContext.current
    val connection = remember {
        TimerServiceConnection(context)
    }
    val timerState by remember {
        mutableStateOf(Timer(startTime = 10000))
    }

    LaunchedEffect(Unit) {
        connection.bind()
    }

    DisposableEffect(Unit) {
        onDispose {
            connection.unbind()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            connection.startTimer(timerState)
            viewModel.onEvent(TimerScreenEvent.StartTimer)
        }) {
            Text("START")
        }
        Button(onClick = {
            connection.pauseTimer()
            viewModel.onEvent(TimerScreenEvent.PauseTimer)
        }) {
            Text("PAUSE")
        }
        Button(onClick = {
            connection.stopTimer()
            viewModel.onEvent(TimerScreenEvent.StartTimer)
        }) {
            Text("STOP")
        }
    }

}