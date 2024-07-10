package com.ighorosipov.multitimer.presentation.services.timer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class TimerActionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val bundle = intent?.extras
        if (bundle != null) {
            when (val timerAction = intent.action) {
                TimerAction.START.action, TimerAction.PAUSE.action, TimerAction.STOP.action -> {
                    val mIntent = Intent(context, TimerService::class.java)
                    mIntent.action = timerAction
                    context?.sendBroadcast(mIntent)
                }
            }
        }
    }

    companion object {
        const val TIMER_ACTION = "com.ighorosipov.multitimer.TIMER_ACTION"
    }

}