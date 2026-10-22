package com.ighorosipov.domain.timer.model

enum class TimerStatus {
    IDLE,
    RUNNING,
    PAUSED,
    FINISHED
}

fun TimerStatus.isIdle(): Boolean {
    return this == TimerStatus.IDLE
}

fun TimerStatus.isRunning(): Boolean {
    return this == TimerStatus.RUNNING
}

fun TimerStatus.isPaused(): Boolean {
    return this == TimerStatus.PAUSED
}

fun TimerStatus.isFinished(): Boolean {
    return this == TimerStatus.FINISHED
}