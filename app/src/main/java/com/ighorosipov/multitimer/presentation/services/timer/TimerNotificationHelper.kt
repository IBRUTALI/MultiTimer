package com.ighorosipov.multitimer.presentation.services.timer

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.ighorosipov.multitimer.R
import com.ighorosipov.multitimer.domain.model.Timer
import com.ighorosipov.multitimer.domain.model.TimerEvent
import com.ighorosipov.multitimer.presentation.MainActivity
import com.ighorosipov.multitimer.presentation.ui.components.navigation.Screen

class TimerNotificationHelper(
    private val context: Context
) {

    var notificationBuilder: NotificationCompat.Builder? = null
        private set
    private var notificationManager: NotificationManager? = null

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
            Screen.TimerScreen.deeplink,
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
        val fullScreenPendingIntent = PendingIntent.getActivity(
            context,
            0,
            routeIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        // Get the layouts to use in the custom notification.
        val notificationLayout = RemoteViews(context.packageName, R.layout.notification_small)
        val notificationLayoutExpanded = RemoteViews(context.packageName, R.layout.notification_big)

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
            .setContentTitle(context.resources.getString(R.string.timer_active))
            .setSilent(true)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(notificationLayout)
            .setCustomBigContentView(notificationLayoutExpanded)
//            .addAction(R.drawable.ic_pause, "Pause/Start", timerNotificationAction(TimerAction.PAUSE))
//            .addAction(R.drawable.ic_stop, "Stop", timerNotificationAction(TimerAction.STOP))
            .setContentIntent(routePendingIntent)
    }


    fun updateNotificationAndNotify(timer: Timer) {
        when (timer.event) {
            TimerEvent.Count -> {
                updateNotification(
                    contentTitle = context.resources.getString(R.string.timer_active),
                    setOngoing = true
                )
            }

            TimerEvent.Pause -> {
                updateNotification(
                    contentTitle = context.resources.getString(R.string.timer_inactive),
                    setOngoing = false
                )
            }

            TimerEvent.Overtime -> {
                updateNotification(
                    contentTitle = context.resources.getString(R.string.time_is_up),
                    setOngoing = true,
                    isOvertime = true
                )
            }
        }
        notificationBuilder?.setContentText(timer.timeString)
        notificationManager?.notify(NOTIFICATION_ID, notificationBuilder?.build())
    }

    @SuppressLint("RestrictedApi")
    private fun updateNotification(contentTitle: String, setOngoing: Boolean, isOvertime: Boolean = false) {
        val action = if(setOngoing) {
            NotificationCompat.Action(R.drawable.ic_pause, "Pause", timerNotificationAction(TimerAction.PAUSE))
        } else {
            NotificationCompat.Action(R.drawable.ic_play, "Start", timerNotificationAction(TimerAction.START))
        }
        notificationBuilder?.let {
            it.setContentTitle(contentTitle)
            it.setOngoing(setOngoing)
//            if (isOvertime) {
//                it.mActions.clear()
//                it.addAction(R.drawable.ic_stop, "Stop", timerNotificationAction(TimerAction.STOP))
//            } else {
//                it.mActions[0] = action
//            }

        }

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

    companion object {
        const val NOTIFICATION_ID = 101
        const val CHANNEL_ID = "MULTI_TIMER_CHANNEL_TIMER"
        const val CHANNEL_NAME = "Timer"
    }

}