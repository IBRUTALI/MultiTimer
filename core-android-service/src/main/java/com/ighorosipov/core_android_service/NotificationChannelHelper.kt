package com.ighorosipov.core_android_service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.content.getSystemService
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.concurrent.atomics.AtomicBoolean
import kotlin.concurrent.atomics.ExperimentalAtomicApi

@Singleton
class NotificationChannelHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val manager = context.getSystemService<NotificationManager>()
    @OptIn(ExperimentalAtomicApi::class)
    private val created = AtomicBoolean(false)

    @OptIn(ExperimentalAtomicApi::class)
    fun ensureChannelsCreated() {
        if (created.compareAndSet(false, true)) {
            createChannels()
        }
    }

    private fun createChannels() {
        manager?.createNotificationChannels(
            listOf(
                NotificationChannel(
                    CHANNEL_ONGOING_LOW,
                    "Ongoing (Low)",
                    NotificationManager.IMPORTANCE_LOW
                ).apply {
                    description = "Постоянные уведомления для активных таймеров/секундомеров"
                    setShowBadge(false)
                },
                NotificationChannel(
                    CHANNEL_ALERT_HIGH,
                    "Alerts (High)",
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = "Звуковые уведомления о завершении"
                    enableVibration(true)
                    vibrationPattern = longArrayOf(0, 500, 250, 500)
                }
            )
        )
    }

    companion object {
        const val CHANNEL_ONGOING_LOW = "ongoing_low"
        const val CHANNEL_ALERT_HIGH = "alert_high"
    }
}