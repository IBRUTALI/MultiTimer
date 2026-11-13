package com.ighorosipov.domain.stopwatch.model

data class Stopwatch(
    val id: String,
    val name: String,
    val elapsedMs: Long,
    val colorHex: String,
    val status: StopwatchStatus,
    val laps: List<Lap>
) {
    val isRunning: Boolean get() = status == StopwatchStatus.RUNNING
    val isPaused: Boolean get() = status == StopwatchStatus.PAUSED
    val isIdle: Boolean get() = status == StopwatchStatus.IDLE
}
