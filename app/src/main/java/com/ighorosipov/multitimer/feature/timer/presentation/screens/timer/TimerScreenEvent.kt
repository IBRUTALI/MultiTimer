package com.ighorosipov.multitimer.feature.timer.presentation.screens.timer

sealed class TimerScreenEvent {
    data object AddTimer: TimerScreenEvent()
    data object DeleteTimer: TimerScreenEvent()
    data object StartTimer: TimerScreenEvent()
    data object PauseTimer: TimerScreenEvent()
    data class UpdateTimer(val time: Long) : TimerScreenEvent()

}