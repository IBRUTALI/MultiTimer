package com.ighorosipov.domain.timer.notification

import android.app.PendingIntent

fun interface ActivityIntentProvider {
    fun intentFor(target: NotificationTarget): PendingIntent
}