package com.ighorosipov.multitimer.domain.model

import java.util.concurrent.TimeUnit

data class Timer(
    val startTime: Long = 0,
    val isRunning: Boolean = true
) {
    val timeString: String
        get() {
            val hours = TimeUnit.MILLISECONDS.toHours(startTime)
            val minutes = TimeUnit.MILLISECONDS.toMinutes(startTime) % 60
            val seconds = TimeUnit.MILLISECONDS.toSeconds(startTime) % 60
            return String.format("%02d:%02d:%02d", hours, minutes, seconds)
        }
}