package com.ighorosipov.multitimer.feature.timer.presentation.services.timer

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.IBinder

class TimerServiceConnection(
    private val context: Context,
) : ServiceConnection {

    private var isBound = false

    private val intent = Intent(context, TimerService::class.java)

    private var service: TimerService? = null

    override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
        service = (binder as TimerService.LocalBinder).getService()
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        service = null
    }

    fun startTimer() {
        if (isBound) {
            unbind()
            stopService()
        }
        bind()
        startService()
    }

    fun resumeTimer(timerId: String) {
        service?.resumeTimer()
    }

    fun pauseTimer(timerId: String) {
        service?.pauseTimer()
    }

    fun stopTimer(timerId: String) {
        service?.stopTimer()
        unbind()
        stopService()
    }

    private fun startService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent)
        } else {
            context.startService(intent)
        }

    }

    private fun stopService() {
        service = null
        context.stopService(intent)
    }

    fun bind(flag: Int = 0) {
        if (!isBound) {
            context.bindService(intent, this, flag)
            isBound = true
        }
    }

    fun unbind() {
        if (isBound) {
            context.unbindService(this)
            isBound = false
        }
    }

}