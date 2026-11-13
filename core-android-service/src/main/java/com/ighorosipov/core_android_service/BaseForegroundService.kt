package com.ighorosipov.core_android_service

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseForegroundService : Service() {

    @Inject
    lateinit var notificationChannelHelper: NotificationChannelHelper

    protected val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    private var wakeLock: PowerManager.WakeLock? = null

    abstract val notificationId: Int
    abstract fun buildPlaceholderNotification(): Notification
    abstract fun onServiceCreated()
    abstract fun updateNotification()

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        notificationChannelHelper.ensureChannelsCreated()
        startForegroundSafely(buildPlaceholderNotification())
        acquireWakeLock()
        onServiceCreated()
    }

    private fun startForegroundSafely(notification: Notification) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            startForeground(
                notificationId,
                notification,
                ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE
            )
        } else {
            startForeground(notificationId, notification)
        }
    }

    private fun acquireWakeLock() {
        wakeLock = (getSystemService(POWER_SERVICE) as PowerManager)
            .newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "${javaClass.simpleName}::wake")
            .apply {
                setReferenceCounted(false)
                acquire(60 * 60 * 1000L)
            }
    }

    protected fun renewWakeLock() {
        wakeLock?.let {
            if (it.isHeld) {
                it.acquire(60 * 60 * 1000L)
            }
        }
    }

    override fun onDestroy() {
        wakeLock?.takeIf { it.isHeld }?.release()
        serviceScope.cancel()
        super.onDestroy()
    }
}