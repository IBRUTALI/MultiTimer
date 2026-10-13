package com.ighorosipov.timer.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.ighorosipov.domain.model.TimerRuntimeState
import com.ighorosipov.domain.model.isRunning
import com.ighorosipov.utils.R
import com.ighorosipov.utils.formatTime
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TimerNotificationHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        const val CHANNEL_ONGOING = "timers_ongoing"
        const val CHANNEL_FINISHED = "timers_finished"
        const val ONGOING_ID = 1001
    }

    private val manager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(
                NotificationChannel(
                    CHANNEL_ONGOING,
                    "Active timers",
                    NotificationManager.IMPORTANCE_LOW
                )
            )
            manager.createNotificationChannel(
                NotificationChannel(
                    CHANNEL_FINISHED,
                    "Finished timers",
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    enableVibration(true)
                }
            )
        }
    }

    fun buildOngoingNotification(timers: List<TimerRuntimeState>): Notification {
        val running = timers.filter { it.status.isRunning() }
        val title = when (running.size) {
            0 -> "Timers"
            1 -> running.first().name.ifBlank { "Timer" }
            else -> "${running.size} timers running"
        }
        val text = running.joinToString(" • ") {
            "${it.name.ifBlank { "Timer" }} ${formatTime(it.remainingMs)}"
        }.ifBlank { "No active timers" }

        val openApp = PendingIntent.getActivity(
            context, 0,
            context.packageManager.getLaunchIntentForPackage(context.packageName),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(context, CHANNEL_ONGOING)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(text)
            .setOnlyAlertOnce(true)
            .setOngoing(true)
            .setContentIntent(openApp)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .build()
    }

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
            .build()

        manager.notify(timer.id.hashCode(), notification)
    }
}