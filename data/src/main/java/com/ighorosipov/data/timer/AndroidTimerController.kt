package com.ighorosipov.data.timer

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.ighorosipov.data.timer.service.TimerService
import com.ighorosipov.domain.timer.TimerController
import com.ighorosipov.domain.timer.TimerServiceActions
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AndroidTimerController @Inject constructor(
    @ApplicationContext private val context: Context
) : TimerController {

    override fun start(id: String, durationMs: Long, name: String, ringtoneUri: String?) {
        val intent = Intent(context, TimerService::class.java).apply {
            action = TimerServiceActions.START
            putExtra(TimerServiceActions.EXTRA_TIMER_ID, id)
            putExtra(TimerServiceActions.EXTRA_DURATION_MS, durationMs)
            putExtra(TimerServiceActions.EXTRA_NAME, name)
            ringtoneUri?.let { putExtra(TimerServiceActions.EXTRA_RINGTONE_URI, it) }
        }
        ContextCompat.startForegroundService(context, intent)
    }

    override fun pause(id: String) {
        val intent = Intent(context, TimerService::class.java).apply {
            action = TimerServiceActions.PAUSE
            putExtra(TimerServiceActions.EXTRA_TIMER_ID, id)
        }
        ContextCompat.startForegroundService(context, intent)
    }

    override fun stop(id: String, durationMs: Long) {
        val intent = Intent(context, TimerService::class.java).apply {
            action = TimerServiceActions.STOP
            putExtra(TimerServiceActions.EXTRA_TIMER_ID, id)
            putExtra(TimerServiceActions.EXTRA_DURATION_MS, durationMs)
        }
        ContextCompat.startForegroundService(context, intent)
    }

    override fun stopAll() {
        val intent = Intent(context, TimerService::class.java).apply {
            action = TimerServiceActions.STOP_ALL
        }
        ContextCompat.startForegroundService(context, intent)
    }
}