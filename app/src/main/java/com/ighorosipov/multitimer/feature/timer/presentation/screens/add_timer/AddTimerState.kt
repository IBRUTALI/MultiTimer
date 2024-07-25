package com.ighorosipov.multitimer.feature.timer.presentation.screens.add_timer

data class AddTimerState(
    val timerName: String,
    val timerDuration: Long,
    val color: Int,
    val tone: String? = null
)
