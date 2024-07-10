package com.ighorosipov.multitimer.domain.model

import java.util.concurrent.TimeUnit

data class Timer(
    val time: Long = 0,
    val event: TimerEvent = TimerEvent.Count
) {
    val timeString: String
        get() {
            val hours = TimeUnit.MILLISECONDS.toHours(time)
            val minutes = TimeUnit.MILLISECONDS.toMinutes(time) % 60
            val seconds = TimeUnit.MILLISECONDS.toSeconds(time) % 60
            return String.format("%02d:%02d:%02d", hours, minutes, seconds)
        }
}