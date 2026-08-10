package com.ighorosipov.timer.screen.timer

import com.ighorosipov.domain.model.Timer

data class TimerState(
    val timers: List<Timer> = emptyList()
)

sealed class TimerScreenIntent {
    // todo
}