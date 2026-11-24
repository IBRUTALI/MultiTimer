package com.ighorosipov.data.stopwatch.service

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.ighorosipov.domain.stopwatch.StopwatchServiceActions
import com.ighorosipov.domain.stopwatch.model.Stopwatch
import com.ighorosipov.domain.timer.notification.ActivityIntentProvider
import com.ighorosipov.domain.timer.notification.NotificationTarget
import com.ighorosipov.utils.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class StopwatchNotificationHelper @Inject constructor(
    @ApplicationContext private val context: Context,
    private val activityIntentProvider: ActivityIntentProvider
) {

    companion object {
        const val CHANNEL_ONGOING = "ongoing_low"
    }

    fun buildPlaceholderNotification(): Notification {
        val view = RemoteViews(context.packageName, R.layout.notification_stopwatch_compact)
        view.setChronometer(R.id.chronometer, SystemClock.elapsedRealtime(), null, false)

        return NotificationCompat.Builder(context, CHANNEL_ONGOING)
            .setSmallIcon(R.drawable.ic_stopwatch_outline)
            .setColorized(true)
            .setOngoing(true)
            .setOnlyAlertOnce(true)
            .setForegroundServiceBehavior(NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE)
            .setContentIntent(activityIntentProvider.intentFor(NotificationTarget.Stopwatch))
            .setCustomContentView(view)
            .setCustomBigContentView(view)
            .build()
    }

    fun buildOngoingNotification(states: List<Stopwatch>): Notification {
        val active = states.filter { it.isRunning || it.isPaused }.sortedBy { it.id }
        if (active.isEmpty()) return buildPlaceholderNotification()

        val first = active.first()
        val baseTime = SystemClock.elapsedRealtime() - first.elapsedMs

        val collapsedView = RemoteViews(context.packageName, R.layout.notification_stopwatch_compact)
        collapsedView.setChronometer(R.id.chronometer, baseTime, null, first.isRunning)

        val expandedView = RemoteViews(context.packageName, R.layout.notification_stopwatch_expanded)
        expandedView.setChronometer(R.id.chronometer_expanded, baseTime, null, first.isRunning)

        if (first.isRunning) {
            expandedView.setTextViewText(R.id.btn_left, context.getString(R.string.stopwatch_interval))
            expandedView.setOnClickPendingIntent(
                R.id.btn_left,
                buildActionPendingIntent(StopwatchServiceActions.ADD_LAP, first.id)
            )

            expandedView.setTextViewText(R.id.btn_right, context.getString(R.string.stopwatch_stop))
            expandedView.setOnClickPendingIntent(
                R.id.btn_right,
                buildActionPendingIntent(StopwatchServiceActions.PAUSE, first.id)
            )
        } else {
            expandedView.setTextViewText(R.id.btn_left, context.getString(R.string.stopwatch_reset))
            expandedView.setOnClickPendingIntent(
                R.id.btn_left,
                buildActionPendingIntent(StopwatchServiceActions.STOP, first.id)
            )

            expandedView.setTextViewText(R.id.btn_right, context.getString(R.string.stopwatch_resume))
            expandedView.setOnClickPendingIntent(
                R.id.btn_right,
                buildActionPendingIntent(StopwatchServiceActions.RESUME, first.id)
            )
        }

        return NotificationCompat.Builder(context, CHANNEL_ONGOING)
            .setSmallIcon(R.drawable.ic_stopwatch_outline)
            .setOngoing(true)
            .setOnlyAlertOnce(true)
            .setForegroundServiceBehavior(NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE)
            .setContentIntent(activityIntentProvider.intentFor(NotificationTarget.Stopwatch))
            .setCustomContentView(collapsedView)
            .setCustomBigContentView(expandedView)
            .build()
    }


    private fun buildActionPendingIntent(action: String, id: String): PendingIntent {
        val intent = Intent(context, StopwatchService::class.java).apply {
            this.action = action
            putExtra(StopwatchServiceActions.EXTRA_ID, id)
        }
        return PendingIntent.getService(
            context,
            (action + id).hashCode(),
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }
}