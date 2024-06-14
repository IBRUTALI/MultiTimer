package com.ighorosipov.multitimer.domain.model

data class Timer(
    val startTime: Long = 0,
    val timeString: String = "00:00:00",
    val isRunning: Boolean = true
) {

}