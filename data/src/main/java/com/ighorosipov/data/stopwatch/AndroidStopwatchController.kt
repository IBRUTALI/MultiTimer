package com.ighorosipov.data.stopwatch

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.ighorosipov.data.stopwatch.service.StopwatchService
import com.ighorosipov.domain.stopwatch.StopwatchController
import com.ighorosipov.domain.stopwatch.StopwatchServiceActions
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AndroidStopwatchController @Inject constructor(
    @ApplicationContext private val context: Context
) : StopwatchController {

    override fun start(id: String, name: String, colorHex: String) {
        val intent = Intent(context, StopwatchService::class.java).apply {
            action = StopwatchServiceActions.START
            putExtra(StopwatchServiceActions.EXTRA_ID, id)
            putExtra(StopwatchServiceActions.EXTRA_NAME, name)
            putExtra(StopwatchServiceActions.EXTRA_COLOR_HEX, colorHex)
        }
        ContextCompat.startForegroundService(context, intent)
    }

    override fun pause(id: String) {
        sendAction(StopwatchServiceActions.PAUSE, id)
    }

    override fun resume(id: String) {
        sendAction(StopwatchServiceActions.RESUME, id)
    }

    override fun stop(id: String) {
        sendAction(StopwatchServiceActions.STOP, id)
    }

    override fun addLap(id: String) {
        sendAction(StopwatchServiceActions.ADD_LAP, id)
    }

    override fun stopAll() {
        val intent = Intent(context, StopwatchService::class.java).apply {
            action = StopwatchServiceActions.STOP_ALL
        }
        ContextCompat.startForegroundService(context, intent)
    }

    private fun sendAction(action: String, id: String) {
        val intent = Intent(context, StopwatchService::class.java).apply {
            this.action = action
            putExtra(StopwatchServiceActions.EXTRA_ID, id)
        }
        ContextCompat.startForegroundService(context, intent)
    }
}