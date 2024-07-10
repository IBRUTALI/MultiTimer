package com.ighorosipov.multitimer.domain.model

sealed interface TimerEvent {
    data object Count: TimerEvent
    data object Pause: TimerEvent
    data object Overtime: TimerEvent
}