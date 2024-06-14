package com.ighorosipov.multitimer.presentation.services.timer

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TimerServiceConnection(
    private val context: Context
) : ServiceConnection {
    private val _isBound = MutableStateFlow(false)
    private val intent = Intent(context, TimerService::class.java)
    val isBound: StateFlow<Boolean> = _isBound
    var service: TimerService? = null
        private set

    override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
        service = (binder as TimerService.LocalBinder).getService()
        _isBound.value = true
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        _isBound.value = false
        service = null
    }

    fun startService() {
        context.startService(intent)
    }

    fun stopService() {
        context.stopService(intent)
    }

    fun bind() {
        context.bindService(intent, this, Context.BIND_AUTO_CREATE)
    }

    fun unbind() {
        if (_isBound.value) {
            context.unbindService(this)
            _isBound.value = false
        }
    }


}