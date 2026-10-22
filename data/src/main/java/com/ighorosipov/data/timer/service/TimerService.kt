package com.ighorosipov.data.timer.service

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
import android.util.Log
import com.ighorosipov.data.timer.sync.TimerEngineSynchronizer
import com.ighorosipov.domain.timer.TimerServiceActions
import com.ighorosipov.domain.timer.model.TimerRuntimeState
import com.ighorosipov.domain.timer.model.TimerStatus
import com.ighorosipov.domain.timer.model.isRunning
import com.ighorosipov.domain.timer.repository.TimerEngine
import com.ighorosipov.domain.timer.repository.TimerRuntimeRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

const val TIMER_SERVICE_TAG = "TIMER_SERVICE"
@AndroidEntryPoint
class TimerService : Service() {

    @Inject lateinit var engine: TimerEngine
    @Inject lateinit var notificationHelper: TimerNotificationHelper
    @Inject lateinit var runtimeRepository: TimerRuntimeRepository

    @Inject lateinit var timerEngineSynchronizer: TimerEngineSynchronizer

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private var tickerJob: Job? = null
    private var mediaPlayer: MediaPlayer? = null
    private var persistJob: Job? = null
    override fun onBind(p0: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        Log.d(TIMER_SERVICE_TAG, "Timer service started!")
        notificationHelper.createChannels()
        timerEngineSynchronizer.start(serviceScope)
        restoreStateIfNeeded()
    }

    private fun restoreStateIfNeeded() {
        serviceScope.launch {
            val active = runtimeRepository.getActive()
            if (active.isEmpty()) {
                stopSelf()
                return@launch
            }
            engine.restore(active)

            startForegroundIfNeeded()
            ensureTicker()
            startPersistenceLoop()
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TIMER_SERVICE_TAG, "Timer service action: ${intent?.action}!")
        when (intent?.action) {
            TimerServiceActions.START -> {
                val id = intent.getStringExtra(TimerServiceActions.EXTRA_TIMER_ID)
                    ?: return START_STICKY
                val duration = intent.getLongExtra(TimerServiceActions.EXTRA_DURATION_MS, 0L)
                val name = intent.getStringExtra(TimerServiceActions.EXTRA_NAME).orEmpty()
                val ringtone = intent.getStringExtra(TimerServiceActions.EXTRA_RINGTONE_URI)

                engine.start(id, duration, name, ringtone)
                startForegroundIfNeeded()
                ensureTicker()
                startPersistenceLoop()
            }

            TimerServiceActions.PAUSE -> {
                val id = intent.getStringExtra(TimerServiceActions.EXTRA_TIMER_ID)
                    ?: return START_STICKY
                engine.pause(id)
                updateNotification()
                stopIfNothingRunning()
            }

            TimerServiceActions.STOP -> {
                val id = intent.getStringExtra(TimerServiceActions.EXTRA_TIMER_ID)
                    ?: return START_STICKY
                val duration = intent.getLongExtra(TimerServiceActions.EXTRA_DURATION_MS, 0L)
                engine.stop(id, duration)
                serviceScope.launch { runtimeRepository.delete(id) }
                updateNotification()
                stopIfNothingRunning()
            }

            TimerServiceActions.STOP_ALL -> {
                engine.stopAll()
                serviceScope.launch { runtimeRepository.clear() }
                stopTicker()
                stopForeground(STOP_FOREGROUND_REMOVE)
                stopSelf()
            }

            null -> {}
        }
        return START_STICKY
    }

    private fun startForegroundIfNeeded() {
        val notification = notificationHelper.buildOngoingNotification(engine.snapshot())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
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
            var lastNotificationUpdate = 0L

            // Мгновенный первый показ
            updateNotification()
            lastNotificationUpdate = SystemClock.elapsedRealtime()

            while (true) {
                delay(250L.milliseconds)              // engine тикает часто — нужна точность
                val now = SystemClock.elapsedRealtime()
                val delta = now - last
                last = now

                val finished = engine.tick(delta)

                // Нотификацию обновляем НЕ ЧАЩЕ 1 раза в секунду
                if (now - lastNotificationUpdate >= 1000L) {
                    try {
                        updateNotification()
                    } catch (e: Exception) {
                        Log.e("Ticker", "notif update failed", e)
                    }
                    lastNotificationUpdate = now
                }

                finished.forEach { onTimerFinished(it) }

                if (!engine.hasRunning()) break
            }
            tickerJob = null
            stopIfNothingRunning()
        }
    }

    private fun startPersistenceLoop() {
        if (persistJob?.isActive == true) return
        persistJob = serviceScope.launch {
            while (isActive) {
                engine.snapshot().forEach { state ->
                    if (state.status.isRunning() || state.status == TimerStatus.PAUSED) {
                        runtimeRepository.upsert(state)
                    }
                }
                delay(5.seconds)
            }
        }
    }

    private fun onTimerFinished(timer: TimerRuntimeState) {
        notificationHelper.showFinishedNotification(timer)
        updateNotification()
        serviceScope.launch {
            playRingtone(timer.ringtoneUri)
            runtimeRepository.delete(timer.id)
        }
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
        val hasActiveStates = engine.snapshot().any {
            it.status == TimerStatus.RUNNING || it.status == TimerStatus.PAUSED
        }

        if (!hasActiveStates) {
            stopTicker()
            persistJob?.cancel()
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
        timerEngineSynchronizer.stop()
        runBlocking {
            engine.snapshot().forEach { state ->
                if (state.status.isRunning() || state.status == TimerStatus.PAUSED) {
                    runtimeRepository.upsert(state)
                }
            }
        }
        stopTicker()
        persistJob?.cancel()
        mediaPlayer?.release()
        serviceScope.cancel()
        Log.d(TIMER_SERVICE_TAG, "Timer service destroyed!")
        super.onDestroy()
    }
}