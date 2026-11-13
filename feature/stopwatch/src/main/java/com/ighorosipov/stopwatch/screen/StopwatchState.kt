package com.ighorosipov.stopwatch.screen

import com.ighorosipov.domain.stopwatch.model.StopwatchStatus

data class StopwatchState(
    val elapsedMs: Long = 0L,
    val status: StopwatchStatus = StopwatchStatus.IDLE,
    val laps: List<LapUiModel> = emptyList(),
    val progress: Float = 0f
) {
    val isIdle: Boolean get() = status == StopwatchStatus.IDLE
    val isRunning: Boolean get() = status == StopwatchStatus.RUNNING
    val isPaused: Boolean get() = status == StopwatchStatus.PAUSED
}

sealed class StopwatchScreenIntent {
    // todo
}