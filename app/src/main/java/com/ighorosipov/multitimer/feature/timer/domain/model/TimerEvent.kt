package com.ighorosipov.multitimer.feature.timer.domain.model

sealed interface TimerEvent {
    data object Count: TimerEvent
    data object Pause: TimerEvent
    data object Overtime: TimerEvent
}