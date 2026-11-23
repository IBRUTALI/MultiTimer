package com.ighorosipov.data.timer.service

import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.SystemClock
import android.util.Log
import androidx.core.app.ServiceCompat
import com.ighorosipov.core_android_service.BaseForegroundService
import com.ighorosipov.data.timer.sync.TimerEngineSynchronizer
import com.ighorosipov.domain.timer.TimerServiceActions
import com.ighorosipov.domain.timer.model.TimerRuntimeState
import com.ighorosipov.domain.timer.model.TimerStatus
import com.ighorosipov.domain.timer.model.isRunning
import com.ighorosipov.domain.timer.repository.TimerEngine
import com.ighorosipov.domain.timer.repository.TimerRuntimeRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

const val TAG = "TIMER_SERVICE"
@AndroidEntryPoint
class TimerService : BaseForegroundService() {

    @Inject lateinit var engine: TimerEngine
    @Inject lateinit var notificationHelper: TimerNotificationHelper
    @Inject lateinit var runtimeRepository: TimerRuntimeRepository
    @Inject lateinit var timerEngineSynchronizer: TimerEngineSynchronizer

    private var tickerJob: Job? = null
    private var persistJob: Job? = null
    private var mediaPlayer: MediaPlayer? = null

    override val notificationId: Int = TimerNotificationHelper.ONGOING_ID

    override fun buildPlaceholderNotification() =
        notificationHelper.buildPlaceholderNotification()

    override fun onServiceCreated() {
        Log.d(TAG, "Timer service created!")
        timerEngineSynchronizer.start(serviceScope)
        restoreStateIfNeeded()
    }

    override fun updateNotification() {
        val notification = notificationHelper.buildOngoingNotification(engine.snapshot())
        notificationHelper.notifyOngoing(notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "Timer service action: ${intent?.action}!")

        when (intent?.action) {
            TimerServiceActions.START -> {
                val id = intent.getStringExtra(TimerServiceActions.EXTRA_TIMER_ID) ?: return START_STICKY
                val duration = intent.getLongExtra(TimerServiceActions.EXTRA_DURATION_MS, 0L)
                val name = intent.getStringExtra(TimerServiceActions.EXTRA_NAME).orEmpty()
                val ringtone = intent.getStringExtra(TimerServiceActions.EXTRA_RINGTONE_URI)
                val colorHex = intent.getStringExtra(TimerServiceActions.EXTRA_COLOR_HEX).orEmpty()

                engine.start(id, duration, name, ringtone, colorHex)
                updateNotification()
                ensureTicker()
                startPersistenceLoop()
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
                serviceScope.launch(Dispatchers.IO) { runtimeRepository.delete(id) }
                updateNotification()
                stopIfNothingRunning()
            }

            TimerServiceActions.STOP_ALL -> {
                engine.stopAll()
                serviceScope.launch(Dispatchers.IO) { runtimeRepository.clear() }
                stopTicker()
                persistJob?.cancel()
                ServiceCompat.stopForeground(this, ServiceCompat.STOP_FOREGROUND_REMOVE)
                stopSelf()
            }

            null -> {
                if (!engine.hasRunning()) stopIfNothingRunning()
            }
        }
        return START_STICKY
    }

    private fun restoreStateIfNeeded() {
        serviceScope.launch {
            val active = runtimeRepository.getActive()
            if (active.isEmpty()) {
                stopIfNothingRunning()
                return@launch
            }
            engine.restore(active)
            updateNotification()
            ensureTicker()
            startPersistenceLoop()
        }
    }

    private fun ensureTicker() {
        if (tickerJob?.isActive == true) return

        tickerJob = serviceScope.launch(Dispatchers.Default) {
            var last = SystemClock.elapsedRealtime()
            var lastNotificationUpdate = 0L

            updateNotification()
            lastNotificationUpdate = SystemClock.elapsedRealtime()

            while (true) {
                delay(250L.milliseconds)
                val now = SystemClock.elapsedRealtime()
                val delta = now - last
                last = now

                val finished = engine.tick(delta)

                if (now - lastNotificationUpdate >= 1000L) {
                    try { updateNotification() } catch (e: Exception) {
                        Log.e(TAG, "notif update failed", e)
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
                        try { runtimeRepository.upsert(state) } catch (e: Exception) {
                            Log.e(TAG, "Persist failed for ${state.id}", e)
                        }
                    }
                }
                delay(5.seconds)
            }
        }
    }

    private fun onTimerFinished(timer: TimerRuntimeState) {
        notificationHelper.showFinishedNotification(timer)
        updateNotification()
        serviceScope.launch(Dispatchers.IO) {
            try { runtimeRepository.delete(timer.id) } catch (e: Exception) {
                Log.w(TAG, "delete runtime failed for ${timer.id}", e)
            }
            playRingtone(timer.ringtoneUri)
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
                setOnPreparedListener { start() }
                prepareAsync()
            }
        }.onFailure { Log.e(TAG, "playRingtone failed", it) }
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
            ServiceCompat.stopForeground(this, ServiceCompat.STOP_FOREGROUND_REMOVE)
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
                    try { runtimeRepository.upsert(state) } catch (e: Exception) {
                        Log.e(TAG, "Final persist failed for ${state.id}", e)
                    }
                }
            }
        }
        stopTicker()
        persistJob?.cancel()
        mediaPlayer?.release()

        Log.d(TAG, "Timer service destroyed!")
        super.onDestroy()
    }
}