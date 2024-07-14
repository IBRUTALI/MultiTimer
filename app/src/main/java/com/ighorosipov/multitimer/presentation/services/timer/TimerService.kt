package com.ighorosipov.multitimer.presentation.services.timer

import android.app.AlarmManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ServiceInfo
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.ServiceCompat
import androidx.core.content.ContextCompat
import com.ighorosipov.multitimer.di.DefaultDispatcher
import com.ighorosipov.multitimer.domain.model.Timer
import com.ighorosipov.multitimer.domain.model.TimerEvent
import com.ighorosipov.multitimer.domain.use_case.DeleteTimerUseCase
import com.ighorosipov.multitimer.domain.use_case.GetTimersUseCase
import com.ighorosipov.multitimer.domain.use_case.StartTimerUseCase
import com.ighorosipov.multitimer.presentation.services.timer.TimerNotificationHelper.Companion.NOTIFICATION_ID
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TimerService : Service() {

    @DefaultDispatcher
    @Inject
    lateinit var dispatcher: CoroutineDispatcher

    @Inject
    lateinit var deleteTimerUseCase: DeleteTimerUseCase

    @Inject
    lateinit var startTimerUseCase: StartTimerUseCase

    @Inject
    lateinit var getTimersUseCase: GetTimersUseCase

    private lateinit var timerNotificationHelper: TimerNotificationHelper

    private var alarmManager: AlarmManager? = null

    private val binder = LocalBinder()

    private var timerJob: Job? = null

    private var timers = emptyList<Timer>()

    private var currentTimer = Timer(time = 10000)


    /**
     * Receives notifications when the user presses the Start, Pause, Stop buttons in the notification
     * Reacts to incoming intents depending on the type
     * [TimerAction.START.action] when the user wants to start the timer
     * [TimerAction.PAUSE.action] when the user wants to pause the timer
     * [TimerAction.STOP.action] wants to stop the timer completely
     */
    private val timerReceiver: BroadcastReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                TimerAction.START.action -> {
                    resumeTimer()
                }

                TimerAction.PAUSE.action -> {
                    pauseTimer()
                }

                TimerAction.STOP.action -> {
                    stopTimer()
                }
            }
        }

    }

    inner class LocalBinder : Binder() {
        fun getService(): TimerService = this@TimerService
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        timerNotificationHelper = TimerNotificationHelper(this)
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        timerNotificationHelper.notificationBuilder?.let { notificationBuilder ->
            ServiceCompat.startForeground(
                this,
                NOTIFICATION_ID,
                notificationBuilder.build(),
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                    ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE
                } else {
                    0
                }
            )
            val filter = IntentFilter().apply {
                addAction(TimerAction.START.action)
                addAction(TimerAction.PAUSE.action)
                addAction(TimerAction.STOP.action)
            }

            ContextCompat.registerReceiver(
                this,
                timerReceiver,
                filter,
                ContextCompat.RECEIVER_EXPORTED
            )
            startTimer(Timer(time = 10000))
        }
        return START_STICKY
    }

    private fun startTimer(timer: Timer) {
        timerJob?.cancel()
        timerJob = CoroutineScope(dispatcher).launch {
            startTimerUseCase(timer)
                .collect { mTimer ->
                    currentTimer = mTimer
                    timerNotificationHelper.updateNotificationAndNotify(mTimer)
                    delay(300)
                }
        }
    }

    /**
     * Starts the timer depending on the state of [timerJob].
     * Updates the data in the notification if [timerJob] is cancelled.
     */
    fun resumeTimer() {
        if (timerJob?.isActive == false) {
            currentTimer = currentTimer.copy(
                event = TimerEvent.Count
            )
            startTimer(currentTimer)
            timerNotificationHelper.updateNotificationAndNotify(currentTimer)
        }
    }

    /**
     * Stops the timer depending on the state of [timerJob].
     * Updates the data in the notification if [timerJob] is active.
     */
    fun pauseTimer() {
        if (timerJob?.isActive == true) {
            timerJob?.cancel()
            currentTimer = currentTimer.copy(
                event = TimerEvent.Pause
            )
            timerNotificationHelper.updateNotificationAndNotify(currentTimer)
        }
    }

    fun stopTimer() {
        timerJob?.cancel()
        stopForeground(STOP_FOREGROUND_REMOVE)
    }

    override fun onDestroy() {
        super.onDestroy()
        timerJob = null
        unregisterReceiver(timerReceiver)
    }

}