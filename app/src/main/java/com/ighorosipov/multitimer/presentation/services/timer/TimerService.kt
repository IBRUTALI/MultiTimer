package com.ighorosipov.multitimer.presentation.services.timer

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat
import com.ighorosipov.multitimer.R
import com.ighorosipov.multitimer.di.IODispatcher
import com.ighorosipov.multitimer.domain.model.Timer
import com.ighorosipov.multitimer.domain.use_case.AddTimerUseCase
import com.ighorosipov.multitimer.domain.use_case.DeleteTimerUseCase
import com.ighorosipov.multitimer.domain.use_case.PauseTimerUseCase
import com.ighorosipov.multitimer.domain.use_case.StartTimerUseCase
import com.ighorosipov.multitimer.utils.base.safeCollect
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.timeout
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

@AndroidEntryPoint
class TimerService : Service() {

    @IODispatcher
    @Inject
    lateinit var dispatcher: CoroutineDispatcher

    @Inject
    lateinit var addTimerUseCase: AddTimerUseCase

    @Inject
    lateinit var deleteTimerUseCase: DeleteTimerUseCase

    @Inject
    lateinit var startTimerUseCase: StartTimerUseCase

    @Inject
    lateinit var pauseTimerUseCase: PauseTimerUseCase

    private var notificationBuilder: NotificationCompat.Builder? = null
    private var notificationManager: NotificationManager? = null

    private val binder = LocalBinder()

    // Is timer active?
    private var isTimerRunning = AtomicBoolean(true)

    private var timerJob: Job? = null

    inner class LocalBinder : Binder() {
        fun getService(): TimerService = this@TimerService
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        notificationBuilder = createNotification()
        notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager?.createNotificationChannel(channel)
        }

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        notificationBuilder?.let { notificationBuilder ->
            ServiceCompat.startForeground(
                this,
                NOTIFICATION_ID,
                notificationBuilder.build(),
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                    ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE
                } else {
                    0
                })
        }
        return START_NOT_STICKY
    }

    fun startTimer(timer: Timer) {
        timerJob?.cancel()
        timerJob = CoroutineScope(dispatcher).launch {
            startTimerUseCase(timer)
                .transform {
                    if (isTimerRunning.get()) {
                        emit(it.copy(isRunning = true))
                    }
                    if (!isTimerRunning.get()) {
                        while (!isTimerRunning.get() && isActive) {
                            emit(it.copy(isRunning = false))
                        }
                    }
                }
                .collect {
                    ensureActive()
                    setTextToNotificationAndNotify(it.timeString)
                }
        }
    }

    fun pauseTimer() {
        isTimerRunning.set(!isTimerRunning.get())
    }

    fun stopTimer() {
        timerJob?.cancel()
    }

    private fun createNotification(): NotificationCompat.Builder {
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_time)
            .setContentTitle(this.resources.getString(R.string.timer_active))
            .setSilent(true)
            .setOngoing(true)

        val resultIntent = Intent(
            this,
            TimerService::class.java
        )
        val resultPendingIntent = PendingIntent.getActivity(
            this,
            0,
            resultIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        builder.setContentIntent(resultPendingIntent)
        return builder
    }

    private fun setTextToNotificationAndNotify(contextText: String) {
        if (isTimerRunning.get()) {
            notificationBuilder?.setContentTitle(this.resources.getString(R.string.timer_active))
        }
        if (!isTimerRunning.get()) {
            notificationBuilder?.setContentTitle(this.resources.getString(R.string.timer_inactive))
        }
        notificationBuilder?.setContentText(contextText)
        notificationManager?.notify(NOTIFICATION_ID, notificationBuilder?.build())
    }

    private fun removeNotification() {
        notificationManager?.cancel(NOTIFICATION_ID)
    }

    override fun onDestroy() {
        super.onDestroy()
        timerJob = null
        removeNotification()
    }

    companion object {
        const val NOTIFICATION_ID = 101
        const val CHANNEL_ID = "MULTI_TIMER_CHANNEL_TIMER"
        const val CHANNEL_NAME = "MULTI_TIMER_NAME_TIMER"
    }

}