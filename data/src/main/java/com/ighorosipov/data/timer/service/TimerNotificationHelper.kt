package com.ighorosipov.data.timer.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.view.View
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.graphics.toColorInt
import com.ighorosipov.domain.timer.TimerServiceActions
import com.ighorosipov.domain.timer.model.TimerRuntimeState
import com.ighorosipov.domain.timer.model.TimerStatus
import com.ighorosipov.domain.timer.model.isRunning
import com.ighorosipov.domain.timer.notification.ActivityIntentProvider
import com.ighorosipov.domain.timer.notification.NotificationTarget
import com.ighorosipov.utils.R
import com.ighorosipov.utils.TimeFormat
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
        const val MAX_VISIBLE_TIMERS = 5
    }

    private val manager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun buildOngoingNotification(states: List<TimerRuntimeState>): Notification {
        val active = states.filter {
            it.status == TimerStatus.RUNNING || it.status == TimerStatus.PAUSED
        }.sortedBy { it.remainingMs }

        if (active.isEmpty()) return buildPlaceholderNotification()

        return buildCustomNotification(active)
    }

    private fun buildCustomNotification(states: List<TimerRuntimeState>): Notification {
        val expandedView = buildExpandedView(states)
        val collapsedView = buildCollapsedView(states)

        return NotificationCompat.Builder(context, CHANNEL_ONGOING)
            .setSmallIcon(R.drawable.ic_timer_small)
            .setOngoing(true)
            .setOnlyAlertOnce(true)
            .setShowWhen(false)
            .setCategory(NotificationCompat.CATEGORY_STOPWATCH)
            .setForegroundServiceBehavior(NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE)
            .setCustomContentView(collapsedView)
            .setCustomBigContentView(expandedView)
            .setContentIntent(openAppIntent())
            .build()
    }

    private fun buildExpandedView(states: List<TimerRuntimeState>): RemoteViews {
        val view = RemoteViews(context.packageName, R.layout.notification_timer_expanded)

        val countText = if (states.size == 1) {
            context.getString(R.string.notif_single_active_timer)
        } else {
            context.getString(R.string.notif_active_timers_format, states.size)
        }
        view.setTextViewText(R.id.tv_count, countText)

        val stopAllPI = buildStopAllPendingIntent()
        view.setOnClickPendingIntent(R.id.btn_stop_all, stopAllPI)

        val visible = states.take(MAX_VISIBLE_TIMERS)
        val overflow = states.size - visible.size

        visible.forEach { state ->
            view.addView(R.id.container_timers, buildTimerItemView(state))
        }

        if (overflow > 0) {
            view.setTextViewText(
                R.id.tv_overflow,
                context.resources.getQuantityString(R.plurals.timers_amount, overflow, overflow)
            )
            view.setViewVisibility(R.id.tv_overflow, View.VISIBLE)
        } else {
            view.setViewVisibility(R.id.tv_overflow, View.GONE)
        }
        return view
    }

    private fun buildCollapsedView(states: List<TimerRuntimeState>): RemoteViews {
        val view = RemoteViews(context.packageName, R.layout.notification_timer_collapsed)

        val firstState = states
            .sortedWith(compareBy(
                { if (it.status.isRunning()) 0 else 1 },
                { it.remainingMs }
            ))
            .first()

        view.addView(R.id.collapsed_timer_container, buildCompactTimerItemView(firstState))
        return view
    }

    private fun buildCompactTimerItemView(state: TimerRuntimeState): RemoteViews {
        val item = RemoteViews(context.packageName, R.layout.notification_timer_item_compact)

        val pastelBg = pastelColor(state.colorHex)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            item.setColorStateList(
                R.id.timer_item_root_compact,
                "setBackgroundTintList",
                ColorStateList.valueOf(pastelBg)
            )
        }

        val timeStr = formatTime(state.remainingMs, TimeFormat.HH_MM_SS)
        item.setTextViewText(R.id.tv_timer_name_compact, timeStr)

        val isRunning = state.status.isRunning()
        item.setImageViewResource(
            R.id.btn_play_pause_compact,
            if (isRunning) R.drawable.ic_pause_dark else R.drawable.ic_play_dark
        )

        val playPauseAction = if (isRunning) TimerServiceActions.PAUSE else TimerServiceActions.START
        val playPauseIntent = Intent(context, TimerService::class.java).apply {
            action = playPauseAction
            putExtra(TimerServiceActions.EXTRA_TIMER_ID, state.id)
            if (!isRunning) {
                putExtra(TimerServiceActions.EXTRA_DURATION_MS, state.remainingMs)
                putExtra(TimerServiceActions.EXTRA_NAME, state.name)
                putExtra(TimerServiceActions.EXTRA_COLOR_HEX, state.colorHex)
                state.ringtoneUri?.let { putExtra(TimerServiceActions.EXTRA_RINGTONE_URI, it) }
            }
        }
        item.setOnClickPendingIntent(
            R.id.btn_play_pause_compact,
            PendingIntent.getService(
                context,
                ("pp_compact_${state.id}").hashCode(),
                playPauseIntent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
        )

        val resetIntent = Intent(context, TimerService::class.java).apply {
            action = TimerServiceActions.STOP
            putExtra(TimerServiceActions.EXTRA_TIMER_ID, state.id)
            putExtra(TimerServiceActions.EXTRA_DURATION_MS, state.durationMs)
        }
        item.setOnClickPendingIntent(
            R.id.btn_reset_compact,
            PendingIntent.getService(
                context,
                ("stop_compact_${state.id}").hashCode(),
                resetIntent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
        )

        return item
    }

    private fun buildTimerItemView(state: TimerRuntimeState): RemoteViews {
        val item = RemoteViews(context.packageName, R.layout.notification_timer_item)

        val pastelBg = pastelColor(state.colorHex)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val colorStateList = ColorStateList.valueOf(pastelBg)
            item.setColorStateList(
                R.id.timer_item_root,
                "setBackgroundTintList",
                colorStateList
            )
        } else {
            item.setInt(R.id.timer_item_root, "setBackgroundColor", pastelBg)
        }

        if (state.name.isBlank()) {
            item.setViewVisibility(R.id.tv_timer_name, View.GONE)
        } else {
            item.setViewVisibility(R.id.tv_timer_name, View.VISIBLE)
            item.setTextViewText(R.id.tv_timer_name, state.name)
        }

        item.setTextViewText(
            R.id.tv_timer_time,
            formatTime(state.remainingMs, TimeFormat.HH_MM_SS)
        )

        val isRunning = state.status.isRunning()
        item.setImageViewResource(
            R.id.btn_play_pause,
            if (isRunning) R.drawable.ic_pause_dark else R.drawable.ic_play_dark
        )
        item.setContentDescription(
            R.id.btn_play_pause,
            context.getString(if (isRunning) R.string.notif_pause else R.string.notif_play)
        )

        val playPauseAction = if (isRunning) TimerServiceActions.PAUSE else TimerServiceActions.START
        val playPauseIntent = Intent(context, TimerService::class.java).apply {
            action = playPauseAction
            putExtra(TimerServiceActions.EXTRA_TIMER_ID, state.id)
            if (!isRunning) {
                putExtra(TimerServiceActions.EXTRA_DURATION_MS, state.remainingMs)
                putExtra(TimerServiceActions.EXTRA_NAME, state.name)
                putExtra(TimerServiceActions.EXTRA_COLOR_HEX, state.colorHex)
                state.ringtoneUri?.let { putExtra(TimerServiceActions.EXTRA_RINGTONE_URI, it) }
            }
        }
        item.setOnClickPendingIntent(
            R.id.btn_play_pause,
            PendingIntent.getService(
                context,
                ("pp_${state.id}").hashCode(),
                playPauseIntent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
        )

        val resetIntent = Intent(context, TimerService::class.java).apply {
            action = TimerServiceActions.STOP
            putExtra(TimerServiceActions.EXTRA_TIMER_ID, state.id)
            putExtra(TimerServiceActions.EXTRA_DURATION_MS, state.durationMs)
        }
        item.setOnClickPendingIntent(
            R.id.btn_reset,
            PendingIntent.getService(
                context,
                ("stop_${state.id}").hashCode(),
                resetIntent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
        )

        return item
    }

    private fun buildStopAllPendingIntent(): PendingIntent {
        val intent = Intent(context, TimerService::class.java).apply {
            action = TimerServiceActions.STOP_ALL
        }
        return PendingIntent.getService(
            context,
            "stop_all_btn".hashCode(),
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
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

    private fun pastelColor(hex: String, alpha: Float = 0.5f): Int {
        return try {
            val color = hex.toColorInt()
            val r = Color.red(color)
            val g = Color.green(color)
            val b = Color.blue(color)
            val mixedR = (r * alpha + 255 * (1 - alpha)).toInt()
            val mixedG = (g * alpha + 255 * (1 - alpha)).toInt()
            val mixedB = (b * alpha + 255 * (1 - alpha)).toInt()
            Color.rgb(mixedR, mixedG, mixedB)
        } catch (e: Exception) {
            Color.rgb(244, 184, 184)
        }
    }
}