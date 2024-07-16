package com.ighorosipov.multitimer.feature.timer.presentation.screens.timer

import com.ighorosipov.multitimer.feature.timer.domain.model.Timer

data class TimerState(
    var timers: List<Timer>
)