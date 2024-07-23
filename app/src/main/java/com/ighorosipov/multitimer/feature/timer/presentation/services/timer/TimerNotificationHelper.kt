package com.ighorosipov.multitimer.feature.timer.presentation.services.timer

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.View
import android.widget.RemoteViews
import androidx.annotation.IdRes
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.ighorosipov.multitimer.R
import com.ighorosipov.multitimer.feature.timer.domain.model.Timer
import com.ighorosipov.multitimer.feature.timer.domain.model.TimerEvent
import com.ighorosipov.multitimer.feature.timer.presentation.MainActivity
import com.ighorosipov.multitimer.ui.components.navigation.Screen

class TimerNotificationHelper(
    private val context: Context,
) {

    var notificationBuilder: NotificationCompat.Builder? = null
        private set
    private var notificationManager: NotificationManager? = null

    // Get the layouts to use in the custom notification.
    private val notificationLayout = RemoteViews(context.packageName, R.layout.notification_small)
    private val notificationLayoutExpanded = RemoteViews(context.packageName, R.layout.notification_big)

    init {
        notificationBuilder = createNotification()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager?.createNotificationChannel(channel)
    }

    private fun createNotification(): NotificationCompat.Builder {
        notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val routeIntent = Intent(
            Intent.ACTION_VIEW,
            Screen.TimerGraph.TimerDetails().deeplink,
            context,
            MainActivity::class.java
        ).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        }

        val routeFlags = PendingIntent.FLAG_IMMUTABLE or
                PendingIntent.FLAG_UPDATE_CURRENT

        val routePendingIntent = PendingIntent.getActivity(
            context,
            0,
            routeIntent,
            routeFlags
        )

        notificationLayoutExpanded.setOnClickPendingIntent(
            R.id.notification_play, timerNotificationAction(TimerAction.START)
        )
        notificationLayoutExpanded.setOnClickPendingIntent(
            R.id.notification_pause, timerNotificationAction(TimerAction.PAUSE)
        )
        notificationLayoutExpanded.setOnClickPendingIntent(
            R.id.notification_stop, timerNotificationAction(TimerAction.STOP)
        )

        notificationLayoutExpanded.setTextViewText(
            R.id.notification_title, context.resources.getString(R.string.timer_active)
        )

        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_time)
            .setSilent(true)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(notificationLayout)
            .setCustomBigContentView(notificationLayoutExpanded)
            .setContentIntent(routePendingIntent)
    }


    fun updateNotificationAndNotify(timer: Timer) {
        when (timer.event) {
            TimerEvent.Count -> {
                notificationBuilder?.setOngoing(true)
                changeRemoteViewTextView(
                    R.id.notification_title,
                    context.resources.getString(R.string.timer_active)
                )
                notificationLayoutExpanded.setViewVisibility(R.id.notification_play, View.GONE)
                notificationLayoutExpanded.setViewVisibility(R.id.notification_pause, View.VISIBLE)
            }

            TimerEvent.Pause -> {
                notificationBuilder?.setOngoing(false)
                changeRemoteViewTextView(
                    R.id.notification_title,
                    context.resources.getString(R.string.timer_inactive)
                )
                notificationLayoutExpanded.setViewVisibility(R.id.notification_play, View.VISIBLE)
                notificationLayoutExpanded.setViewVisibility(R.id.notification_pause, View.GONE)
            }

            TimerEvent.Overtime -> {
                notificationBuilder?.setOngoing(true)
                changeRemoteViewTextView(
                    R.id.notification_title,
                    context.resources.getString(R.string.time_is_up)
                )
                notificationLayoutExpanded.setViewVisibility(R.id.notification_play, View.GONE)
                notificationLayoutExpanded.setViewVisibility(R.id.notification_pause, View.GONE)
            }
        }
        changeRemoteViewTextView(R.id.notification_body, timer.timeString)
        notificationManager?.notify(NOTIFICATION_ID, notificationBuilder?.build())
    }

    private fun timerNotificationAction(timerAction: TimerAction): PendingIntent? {
        val intent = Intent()
        when (timerAction) {
            TimerAction.START, TimerAction.PAUSE, TimerAction.STOP -> {
                intent.action = timerAction.action
                return PendingIntent.getBroadcast(
                    context,
                    0,
                    intent,
                    PendingIntent.FLAG_IMMUTABLE
                )
            }
        }
    }

    private fun changeRemoteViewTextView(@IdRes viewId: Int, text: String) {
        notificationLayout.setTextViewText(viewId, text)
        notificationLayoutExpanded.setTextViewText(viewId, text)
    }

    companion object {
        const val NOTIFICATION_ID = 101
        const val CHANNEL_ID = "MULTI_TIMER_CHANNEL_TIMER"
        const val CHANNEL_NAME = "Timer"
    }

}