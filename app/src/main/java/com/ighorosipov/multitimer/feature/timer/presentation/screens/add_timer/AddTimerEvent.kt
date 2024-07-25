package com.ighorosipov.multitimer.feature.timer.presentation.screens.add_timer

sealed class AddTimerEvent {

    data class ChangeTimerName(val timerName: String): AddTimerEvent()

}