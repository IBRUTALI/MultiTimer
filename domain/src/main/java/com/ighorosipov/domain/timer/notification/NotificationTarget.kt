package com.ighorosipov.domain.timer.notification

sealed interface NotificationTarget {
    data object Timers: NotificationTarget
    data class TimerDetail(val id: String) : NotificationTarget
}