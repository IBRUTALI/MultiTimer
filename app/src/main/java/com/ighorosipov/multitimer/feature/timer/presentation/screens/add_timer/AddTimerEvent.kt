package com.ighorosipov.multitimer.feature.timer.presentation.screens.add_timer

sealed class AddTimerEvent {

    data class ChangeTimerName(val timerName: String): AddTimerEvent()

    data class ChangeCustomDurationCheck(val isChecked: Boolean): AddTimerEvent()

    data class ChangeCustomTimerText(val text: String): AddTimerEvent()

    data class ChangeTimerDuration(val value: Long): AddTimerEvent()

    data class ChangeTimerColor(val index: Int): AddTimerEvent()

    data class RingtoneCheck(val index: Int, val uri: String): AddTimerEvent()

}