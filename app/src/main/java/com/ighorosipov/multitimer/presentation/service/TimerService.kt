package com.ighorosipov.multitimer.presentation.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class TimerService: Service() {

    inner class TimerBinder : Binder() {
        fun getService(): TimerService = this@TimerService
    }

    private val binder = TimerBinder()

    // Start and end times in milliseconds
    private var startTime: Long = 0
    // Start and end times in milliseconds
    private var endTime: Long = 0

    // Is the service tracking time?
    private var isTimerRunning = false

    private var timerJob: Job? = null

    override fun onCreate() {
        startTime = 0
        endTime = 0
        isTimerRunning = false
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    fun startTimer() {
        timerJob = CoroutineScope(Dispatchers.Default).launch {
            while (isTimerRunning || startTime == endTime) {
                delay(1000)
                startTime++
            }
        }
    }

    fun stopTimer() {
        timerJob?.cancel()
    }

    fun isTimerRunning(): Boolean {
        return isTimerRunning
    }

    override fun onDestroy() {
        super.onDestroy()
        stopTimer()
    }

}