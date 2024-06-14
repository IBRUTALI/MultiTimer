package com.ighorosipov.multitimer.presentation.screens.timer

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.ighorosipov.multitimer.domain.model.Timer
import com.ighorosipov.multitimer.presentation.services.timer.TimerService
import com.ighorosipov.multitimer.presentation.services.timer.TimerServiceConnection

@RequiresApi(Build.VERSION_CODES.O)
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

    val isBound by connection.isBound.collectAsState()

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
            if (!isBound) {
                connection.bind()
            }
                connection.startService()
                connection.service?.startTimer(timerState)
                viewModel.onEvent(TimerScreenEvent.StartTimer)
        }) {
            Text("START")
        }
        Button(onClick = {
            viewModel.onEvent(TimerScreenEvent.PauseTimer)
        }) {
            Text("PAUSE")
        }
        Button(onClick = {
            if (isBound) {
                connection.unbind()
                connection.stopService()
                connection.service?.stopTimer()
                viewModel.onEvent(TimerScreenEvent.StartTimer)
            }
        }) {
            Text("STOP")
        }
    }

}