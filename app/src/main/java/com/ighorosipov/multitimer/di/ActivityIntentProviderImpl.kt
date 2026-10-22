package com.ighorosipov.multitimer.di

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import com.ighorosipov.domain.timer.notification.ActivityIntentProvider
import com.ighorosipov.domain.timer.notification.NotificationTarget
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

const val ACTION_OPEN_TIMER = "ACTION_OPEN_TIMER"

class ActivityIntentProviderImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ActivityIntentProvider {

    override fun intentFor(target: NotificationTarget): PendingIntent {
        val uri = when (target) {
            NotificationTarget.Timers -> "multitimer://timers".toUri()
            is NotificationTarget.TimerDetail -> "multitimer://timer?id=${target.id}".toUri()
        }

        val intent = Intent(Intent.ACTION_VIEW, uri).apply {
            setPackage(context.packageName)  // ← ограничиваем своим пакетом
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }

        val requestCode = target.requestCode()

        return PendingIntent.getActivity(
            context, requestCode, intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    private fun NotificationTarget.requestCode(): Int = when (this) {
        is NotificationTarget.Timers -> 0
        is NotificationTarget.TimerDetail -> id.hashCode()
    }
}