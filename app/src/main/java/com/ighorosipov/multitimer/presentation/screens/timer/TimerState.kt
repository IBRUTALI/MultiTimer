package com.ighorosipov.multitimer.presentation.screens.timer

import com.ighorosipov.multitimer.domain.model.Timer

data class TimerState(
    val timer: Timer = Timer()
)