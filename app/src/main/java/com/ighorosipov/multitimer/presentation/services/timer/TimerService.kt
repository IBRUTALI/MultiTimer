package com.ighorosipov.multitimer.presentation.services.timer

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.ighorosipov.multitimer.R
import com.ighorosipov.multitimer.di.DefaultDispatcher
import com.ighorosipov.multitimer.domain.model.Timer
import com.ighorosipov.multitimer.domain.use_case.AddTimerUseCase
import com.ighorosipov.multitimer.domain.use_case.DeleteTimerUseCase
import com.ighorosipov.multitimer.domain.use_case.PauseTimerUseCase
import com.ighorosipov.multitimer.domain.use_case.StartTimerUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TimerService : Service() {

    @DefaultDispatcher
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

    // Is the service tracking time?
    private var isTimerRunning = false

    private var timerJob: Job? = null

    inner class LocalBinder : Binder() {
        fun getService(): TimerService = this@TimerService
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationBuilder = createNotification()
            startForeground(NOTIFICATION_ID, notificationBuilder?.build())
            notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager?.createNotificationChannel(channel)
        }
        return START_STICKY
    }

    fun startTimer(timer: Timer) {
        timerJob = CoroutineScope(dispatcher).launch {
            startTimerUseCase(timer).collect {
                notificationBuilder?.setContentText(it.timeString)
                notificationManager?.notify(NOTIFICATION_ID, notificationBuilder?.build())
            }
        }
    }

    fun pauseTimer() {

    }

    fun stopTimer() {
        timerJob?.cancel()
        removeNotification()
    }

    fun isTimerRunning(): Boolean {
        return isTimerRunning
    }

    private fun createNotification(): NotificationCompat.Builder {
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Timer Active")
            .setContentText("Tap to return to the timer")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setSilent(true)
        val resultIntent = Intent(this, TimerService::class.java)
        val resultPendingIntent = PendingIntent.getActivity(
            this, 0, resultIntent,
            PendingIntent.FLAG_MUTABLE
        )
        builder.setContentIntent(resultPendingIntent)
        return builder
    }

    private fun removeNotification() {
        notificationManager?.cancel(NOTIFICATION_ID)
    }

    override fun onDestroy() {
        super.onDestroy()
        removeNotification()
        stopTimer()
    }

    companion object {
        const val NOTIFICATION_ID = 1
        const val CHANNEL_ID = "MULTI_TIMER"
        const val CHANNEL_NAME = "Multi timer"
    }

}