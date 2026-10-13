package com.ighorosipov.timer.screen.timer

import com.ighorosipov.timer.model.TimerUiModel

data class TimerState(
    val timers: List<TimerUiModel> = emptyList()
)

sealed class TimerScreenIntent {
    // todo
}