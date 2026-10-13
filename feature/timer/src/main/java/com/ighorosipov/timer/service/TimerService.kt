package com.ighorosipov.timer.service

import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.os.SystemClock
import com.ighorosipov.domain.model.TimerRuntimeState
import com.ighorosipov.domain.repository.TimerEngine
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@AndroidEntryPoint
class TimerService : Service() {

    @Inject
    lateinit var engine: TimerEngine
    @Inject lateinit var notificationHelper: TimerNotificationHelper

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private var tickerJob: Job? = null
    private var mediaPlayer: MediaPlayer? = null

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            TimerServiceActions.START -> {
                val id = intent.getStringExtra(TimerServiceActions.EXTRA_TIMER_ID) ?: return START_STICKY
                val duration = intent.getLongExtra(TimerServiceActions.EXTRA_DURATION_MS, 0L)
                val name = intent.getStringExtra(TimerServiceActions.EXTRA_NAME).orEmpty()
                val ringtone = intent.getStringExtra(TimerServiceActions.EXTRA_RINGTONE_URI)

                engine.start(id, duration, name, ringtone)
                startForegroundIfNeeded()
                ensureTicker()
            }

            TimerServiceActions.PAUSE -> {
                val id = intent.getStringExtra(TimerServiceActions.EXTRA_TIMER_ID) ?: return START_STICKY
                engine.pause(id)
                updateNotification()
                stopIfNothingRunning()
            }

            TimerServiceActions.STOP -> {
                val id = intent.getStringExtra(TimerServiceActions.EXTRA_TIMER_ID) ?: return START_STICKY
                val duration = intent.getLongExtra(TimerServiceActions.EXTRA_DURATION_MS, 0L)
                engine.stop(id, duration)
                updateNotification()
                stopIfNothingRunning()
            }

            TimerServiceActions.STOP_ALL -> {
                engine.stopAll()
                stopTicker()
                stopForeground(STOP_FOREGROUND_REMOVE)
                stopSelf()
            }
        }
        return START_STICKY
    }

    private fun startForegroundIfNeeded() {
        val notification = notificationHelper.buildOngoingNotification(engine.snapshot())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(
                TimerNotificationHelper.ONGOING_ID,
                notification,
                ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE
            )
        } else {
            startForeground(TimerNotificationHelper.ONGOING_ID, notification)
        }
    }

    private fun ensureTicker() {
        if (tickerJob?.isActive == true) return

        tickerJob = serviceScope.launch {
            var last = SystemClock.elapsedRealtime()
            while (true) {
                delay(250L.milliseconds)
                val now = SystemClock.elapsedRealtime()
                val delta = now - last
                last = now

                val finished = engine.tick(delta)
                updateNotification()

                finished.forEach { timer ->
                    onTimerFinished(timer)
                }

                if (!engine.hasRunning()) break
            }
            tickerJob = null
            stopIfNothingRunning()
        }
    }

    private fun onTimerFinished(timer: TimerRuntimeState) {
        notificationHelper.showFinishedNotification(timer)
        playRingtone(timer.ringtoneUri)
        // optional: vibrate
    }

    private fun playRingtone(uriString: String?) {
        runCatching {
            mediaPlayer?.release()
            val uri = uriString?.let { Uri.parse(it) }
                ?: RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)

            mediaPlayer = MediaPlayer().apply {
                setDataSource(this@TimerService, uri)
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_ALARM)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build()
                )
                isLooping = true
                prepare()
                start()
            }
        }
    }

    private fun updateNotification() {
        val notification = notificationHelper.buildOngoingNotification(engine.snapshot())
        notificationHelper.notifyOngoing(notification)
    }

    private fun stopIfNothingRunning() {
        if (!engine.hasRunning()) {
            stopTicker()
            mediaPlayer?.release()
            mediaPlayer = null
            stopForeground(STOP_FOREGROUND_REMOVE)
            stopSelf()
        }
    }

    private fun stopTicker() {
        tickerJob?.cancel()
        tickerJob = null
    }

    override fun onDestroy() {
        stopTicker()
        mediaPlayer?.release()
        serviceScope.cancel()
        super.onDestroy()
    }
}