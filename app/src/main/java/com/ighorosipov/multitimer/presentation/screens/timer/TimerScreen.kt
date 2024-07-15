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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ighorosipov.multitimer.presentation.screens.components.SystemBroadcastReceiver
import com.ighorosipov.multitimer.presentation.services.timer.TimerService.Companion.INTENT_UPDATE_TIMER
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
    val timerState by viewModel.state

    LaunchedEffect(Unit) {
        connection.bind()
    }

    DisposableEffect(Unit) {

        onDispose {
            connection.unbind()
        }
    }

    SystemBroadcastReceiver(systemAction = INTENT_UPDATE_TIMER) { intent ->
        if( intent?.action == INTENT_UPDATE_TIMER ){
            val extras = intent.extras
            val time = extras?.getLong(INTENT_UPDATE_TIMER) ?: 0
            viewModel.onEvent(TimerScreenEvent.UpdateTimer(time))
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = timerState.timer.timeString,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Button(onClick = {
            connection.startTimer()
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