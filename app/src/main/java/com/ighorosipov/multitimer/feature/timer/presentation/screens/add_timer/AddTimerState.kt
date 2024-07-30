package com.ighorosipov.multitimer.feature.timer.presentation.screens.add_timer

data class AddTimerState(
    val timerName: String = "",
    val customDurationEnabled: Boolean = false,
    val customDurationText: String = "",
    val time: Long = 0,
    val selectedColorIndex: Int = 0,
    val color: Int,
    val tone: String? = null,
)
