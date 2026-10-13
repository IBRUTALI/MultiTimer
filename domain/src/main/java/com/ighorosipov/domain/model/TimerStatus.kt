package com.ighorosipov.domain.model

enum class TimerStatus {
    IDLE,
    RUNNING,
    PAUSED,
    FINISHED
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