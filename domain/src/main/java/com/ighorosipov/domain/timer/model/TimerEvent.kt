package com.ighorosipov.domain.timer.model

sealed interface TimerEvent {
    data object Count: TimerEvent
    data object Pause: TimerEvent
    data object Overtime: TimerEvent
}