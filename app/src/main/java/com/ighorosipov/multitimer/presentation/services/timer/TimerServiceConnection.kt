package com.ighorosipov.multitimer.presentation.services.timer

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.IBinder
import com.ighorosipov.multitimer.domain.model.Timer

class TimerServiceConnection(
    private val context: Context,
) : ServiceConnection {

    private var isBound = false

    private val intent = Intent(context, TimerService::class.java)

    private var service: TimerService? = null

    override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
        service = (binder as TimerService.LocalBinder).getService()
        service?.startTimer(Timer(startTime = 10000))
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        service = null
    }

    fun startTimer(timer: Timer) {
        startService()
        bind()
    }

    fun pauseTimer() {
        service?.pauseTimer()
    }

    fun stopTimer() {
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