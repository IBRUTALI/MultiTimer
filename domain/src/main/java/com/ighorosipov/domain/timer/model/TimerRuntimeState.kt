package com.ighorosipov.domain.timer.model

data class TimerRuntimeState(
    val id: String,
    val name: String,
    val remainingMs: Long,
    val durationMs: Long,
    val ringtoneUri: String?,
    val colorHex: String,
    val status: TimerStatus
)
