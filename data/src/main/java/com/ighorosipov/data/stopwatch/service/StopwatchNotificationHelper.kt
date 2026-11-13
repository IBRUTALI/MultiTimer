package com.ighorosipov.data.stopwatch.service

import android.app.Notification
import android.content.Context
import androidx.core.app.NotificationCompat
import com.ighorosipov.domain.stopwatch.model.Stopwatch
import com.ighorosipov.domain.timer.notification.ActivityIntentProvider
import com.ighorosipov.domain.timer.notification.NotificationTarget
import com.ighorosipov.utils.R
import com.ighorosipov.utils.TimeFormat
import com.ighorosipov.utils.formatTime
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class StopwatchNotificationHelper @Inject constructor(
    @ApplicationContext private val context: Context,
    private val activityIntentProvider: ActivityIntentProvider
) {

    companion object {
        const val CHANNEL_ONGOING = "ongoing_low"
        const val CHANNEL_FINISHED = "stopwatch_finished"
        const val ONGOING_ID = 2002
    }

    fun buildPlaceholderNotification(): Notification {
        return NotificationCompat.Builder(context, CHANNEL_ONGOING)
            .setSmallIcon(R.drawable.ic_stopwatch_outline)
            .setContentTitle("Stopwatch running")
            .setOngoing(true)
            .setOnlyAlertOnce(true)
            .setForegroundServiceBehavior(NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE)
            .setContentIntent(activityIntentProvider.intentFor(NotificationTarget.Stopwatch))
            .build()
    }

    fun buildOngoingNotification(states: List<Stopwatch>): Notification {
        val active = states.filter { it.isRunning || it.isPaused }.sortedBy { it.id }
        if (active.isEmpty()) return buildPlaceholderNotification()

        val first = active.first()
        val timeStr = formatTime(first.elapsedMs, TimeFormat.HH_MM_SS)
        val title = if (first.name.isBlank()) "Stopwatch" else first.name

        return NotificationCompat.Builder(context, CHANNEL_ONGOING)
            .setSmallIcon(R.drawable.ic_stopwatch_outline)
            .setContentTitle(title)
            .setContentText("Elapsed: $timeStr")
            .setOngoing(true)
            .setOnlyAlertOnce(true)
            .setForegroundServiceBehavior(NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE)
            .setContentIntent(activityIntentProvider.intentFor(NotificationTarget.Stopwatch))
            .build()
    }
}