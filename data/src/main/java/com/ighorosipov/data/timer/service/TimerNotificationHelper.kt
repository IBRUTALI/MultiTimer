package com.ighorosipov.data.timer.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import androidx.core.app.NotificationCompat
import com.ighorosipov.domain.timer.model.TimerRuntimeState
import com.ighorosipov.domain.timer.model.TimerStatus
import com.ighorosipov.domain.timer.notification.ActivityIntentProvider
import com.ighorosipov.domain.timer.notification.NotificationTarget
import com.ighorosipov.utils.R
import com.ighorosipov.utils.formatTime
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TimerNotificationHelper @Inject constructor(
    @ApplicationContext private val context: Context,
    private val activityIntentProvider: ActivityIntentProvider
) {
    companion object {
        const val CHANNEL_ONGOING = "timers_ongoing"
        const val CHANNEL_FINISHED = "timers_finished"
        const val ONGOING_ID = 1001
    }

    private val manager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun buildOngoingNotification(states: List<TimerRuntimeState>): Notification {
        val active = states.filter {
            it.status == TimerStatus.RUNNING || it.status == TimerStatus.PAUSED
        }

        if (active.isEmpty()) return buildPlaceholderNotification()

        val sorted = active.sortedBy { it.remainingMs }

        val title = if (sorted.size == 1) {
            "⏱ ${sorted.first().name} — ${formatTime(sorted.first().remainingMs)}"
        } else {
            "Активных таймеров: ${sorted.size}"
        }

        val body = sorted.joinToString("\n") { state ->
            val icon = when (state.status) {
                TimerStatus.RUNNING  -> "▶"
                TimerStatus.PAUSED   -> "⏸"
                TimerStatus.FINISHED -> "✓"
                else -> "•"
            }
            "$icon ${state.name} — ${formatTime(state.remainingMs)}"
        }

        return NotificationCompat.Builder(context, CHANNEL_ONGOING)
            .setSmallIcon(R.drawable.ic_time)
            .setContentTitle(title)
            .setContentText(body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setOngoing(true)
            .setOnlyAlertOnce(true)
            .setShowWhen(false)
            .setCategory(NotificationCompat.CATEGORY_STOPWATCH)
            .setForegroundServiceBehavior(NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE)
            .setContentIntent(openAppIntent())
            .build()
    }

    fun buildPlaceholderNotification(): Notification =
        NotificationCompat.Builder(context, CHANNEL_ONGOING)
            .setSmallIcon(R.drawable.ic_time)
            .setContentTitle("MultiTimer")
            .setContentText("Запуск...")
            .setOngoing(true)
            .setOnlyAlertOnce(true)
            .setContentIntent(openAppIntent())
            .build()

    fun notifyOngoing(notification: Notification) {
        manager.notify(ONGOING_ID, notification)
    }

    fun showFinishedNotification(timer: TimerRuntimeState) {
        val notification = NotificationCompat.Builder(context, CHANNEL_FINISHED)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(timer.name.ifBlank { "Timer" })
            .setContentText("Time is up")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setContentIntent(openAppIntent())
            .build()

        manager.notify(timer.id.hashCode(), notification)
    }

    fun createChannels() {
        val ongoing = NotificationChannel(
            CHANNEL_ONGOING,
            "Активные таймеры",
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            description = "Показывает запущенные таймеры"
            setShowBadge(false)
        }

        val finished = NotificationChannel(
            CHANNEL_FINISHED,
            "Таймер завершён",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            enableVibration(true)
            vibrationPattern = longArrayOf(0, 500, 250, 500)
        }

        manager.createNotificationChannels(listOf(ongoing, finished))
    }

    private fun openAppIntent(): PendingIntent {
        return activityIntentProvider.intentFor(NotificationTarget.Timers)
    }
}