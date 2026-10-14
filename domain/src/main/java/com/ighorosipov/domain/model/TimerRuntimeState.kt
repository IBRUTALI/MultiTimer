package com.ighorosipov.domain.model

data class TimerRuntimeState(
    val id: String,
    val name: String,
    val remainingMs: Long,
    val durationMs: Long,
    val ringtoneUri: String?,
    val status: TimerStatus
)
