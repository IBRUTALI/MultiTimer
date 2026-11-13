package com.ighorosipov.data.stopwatch.service

import android.annotation.SuppressLint
import android.app.Notification
import android.content.Intent
import android.os.SystemClock
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.ServiceCompat
import com.ighorosipov.core_android_service.BaseForegroundService
import com.ighorosipov.domain.stopwatch.StopwatchServiceActions
import com.ighorosipov.domain.stopwatch.repository.StopwatchEngine
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@AndroidEntryPoint
class StopwatchService : BaseForegroundService() {

    @Inject lateinit var engine: StopwatchEngine
    @Inject lateinit var notificationHelper: StopwatchNotificationHelper

    private var tickerJob: Job? = null

    override val notificationId: Int = NOTIFICATION_ID

    override fun buildPlaceholderNotification(): Notification =
        notificationHelper.buildPlaceholderNotification()

    override fun onServiceCreated() {}

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action == null) {
            if (!engine.hasRunning()) stopIfNothingRunning()
            return START_STICKY
        }

        when (intent.action) {
            StopwatchServiceActions.START -> {
                val id = intent.getStringExtra(StopwatchServiceActions.EXTRA_ID) ?: return START_STICKY
                val name = intent.getStringExtra(StopwatchServiceActions.EXTRA_NAME).orEmpty()
                val colorHex = intent.getStringExtra(StopwatchServiceActions.EXTRA_COLOR_HEX) ?: "#6750A4"

                engine.start(id, name, colorHex)
                updateNotification()
                ensureTicker()
            }
            StopwatchServiceActions.PAUSE -> {
                val id = intent.getStringExtra(StopwatchServiceActions.EXTRA_ID) ?: return START_STICKY
                engine.pause(id)
                updateNotification()
            }
            StopwatchServiceActions.RESUME -> {
                val id = intent.getStringExtra(StopwatchServiceActions.EXTRA_ID) ?: return START_STICKY
                engine.resume(id)
                updateNotification()
                ensureTicker()
            }
            StopwatchServiceActions.ADD_LAP -> {
                val id = intent.getStringExtra(StopwatchServiceActions.EXTRA_ID) ?: return START_STICKY
                engine.addLap(id)
                updateNotification()
            }
            StopwatchServiceActions.STOP -> {
                val id = intent.getStringExtra(StopwatchServiceActions.EXTRA_ID) ?: return START_STICKY
                engine.stop(id)
                updateNotification()
                stopIfNothingRunning()
            }
            StopwatchServiceActions.STOP_ALL -> {
                engine.stopAll()
                stopTicker()
                ServiceCompat.stopForeground(this, ServiceCompat.STOP_FOREGROUND_REMOVE)
                stopSelf()
            }
        }
        return START_NOT_STICKY
    }

    private fun ensureTicker() {
        if (tickerJob?.isActive == true) return
        tickerJob = serviceScope.launch(Dispatchers.Default) {
            updateNotification()
            var last = SystemClock.elapsedRealtime()
            var lastNotifUpdate = SystemClock.elapsedRealtime()

            while (true) {
                delay(250L.milliseconds)
                val now = SystemClock.elapsedRealtime()
                val delta = now - last
                last = now

                engine.tick(delta)

                if (now - lastNotifUpdate >= 500L) {
                    try { updateNotification() } catch (e: Exception) {}
                    lastNotifUpdate = now
                }

                if (!engine.hasRunning()) break
            }
            tickerJob = null
            stopIfNothingRunning()
        }
    }

    @SuppressLint("MissingPermission")
    override fun updateNotification() {
        val notification = notificationHelper.buildOngoingNotification(engine.snapshot())
        NotificationManagerCompat.from(this).notify(notificationId, notification)
    }

    private fun stopIfNothingRunning() {
        val hasActive = engine.snapshot().any { it.isRunning || it.isPaused }
        if (!hasActive) {
            stopTicker()
            ServiceCompat.stopForeground(this, ServiceCompat.STOP_FOREGROUND_REMOVE)
            stopSelf()
        }
    }

    private fun stopTicker() {
        tickerJob?.cancel()
        tickerJob = null
    }

    companion object {
        const val NOTIFICATION_ID = 2002
    }
}