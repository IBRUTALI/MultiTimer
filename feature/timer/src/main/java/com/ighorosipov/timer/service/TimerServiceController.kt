package com.ighorosipov.timer.service

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TimerServiceController @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun start(id: String, durationMs: Long, name: String, ringtoneUri: String?) {
        val intent = Intent(context, TimerService::class.java).apply {
            action = TimerServiceActions.START
            putExtra(TimerServiceActions.EXTRA_TIMER_ID, id)
            putExtra(TimerServiceActions.EXTRA_DURATION_MS, durationMs)
            putExtra(TimerServiceActions.EXTRA_NAME, name)
            putExtra(TimerServiceActions.EXTRA_RINGTONE_URI, ringtoneUri)
        }
        ContextCompat.startForegroundService(context, intent)
    }

    fun pause(id: String) {
        context.startService(
            Intent(context, TimerService::class.java).apply {
                action = TimerServiceActions.PAUSE
                putExtra(TimerServiceActions.EXTRA_TIMER_ID, id)
            }
        )
    }

    fun stop(id: String, durationMs: Long) {
        context.startService(
            Intent(context, TimerService::class.java).apply {
                action = TimerServiceActions.STOP
                putExtra(TimerServiceActions.EXTRA_TIMER_ID, id)
                putExtra(TimerServiceActions.EXTRA_DURATION_MS, durationMs)
            }
        )
    }
}