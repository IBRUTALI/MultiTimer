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
import com.ighorosipov.multitimer.domain.use_case.AddTimerUseCase
import com.ighorosipov.multitimer.domain.use_case.DeleteTimerUseCase
import com.ighorosipov.multitimer.domain.use_case.PauseTimerUseCase
import com.ighorosipov.multitimer.domain.use_case.StartTimerUseCase
import com.ighorosipov.multitimer.presentation.services.timer.TimerNotificationHelper.Companion.NOTIFICATION_ID
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean
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

    private lateinit var timerNotificationHelper: TimerNotificationHelper

    private var alarmManager: AlarmManager? = null

    private val binder = LocalBinder()

    private var isTimerRunning = AtomicBoolean(true)

    private var timerJob: Job? = null

    private val timerReceiver: BroadcastReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                TimerAction.START.action -> {
                    pauseTimer()
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
        }
        return START_STICKY
    }

    fun startTimer(timer: Timer) {
        timerJob?.cancel()
        timerJob = CoroutineScope(dispatcher).launch {
            startTimerUseCase(timer)
                .transform { mTimer ->
                    if (mTimer.event is TimerEvent.Overtime) {
                        emit(mTimer)
                        delay(1000)
                    } else if (isTimerRunning()) {
                        emit(mTimer.copy(event = TimerEvent.Count))
                        delay(1000)
                    }
                    if (!isTimerRunning() && mTimer.event !is TimerEvent.Overtime) {
                        while (!isTimerRunning() && isActive) {
                            emit(mTimer.copy(event = TimerEvent.Pause))
                        }
                    }
                }
                .distinctUntilChanged()
                .collect { mTimer ->
                    ensureActive()
                    timerNotificationHelper.updateNotificationAndNotify(mTimer)
                }
        }
    }

    fun pauseTimer() {
        isTimerRunning.set(!isTimerRunning())
    }

    fun stopTimer() {
        timerJob?.cancel()
        stopSelf()
    }

    private fun isTimerRunning() = isTimerRunning.get()

    override fun onDestroy() {
        super.onDestroy()
        timerJob = null
        unregisterReceiver(timerReceiver)
    }

}