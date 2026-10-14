package com.ighorosipov.timer.model

import com.ighorosipov.domain.model.Timer
import com.ighorosipov.domain.model.TimerRuntimeState
import com.ighorosipov.domain.model.TimerStatus

data class TimerUiModel(
    val id: String,
    val name: String,
    val durationMs: Long,
    val remainingMs: Long,
    val colorHex: String,
    val ringtoneUri: String?,
    val status: TimerStatus
)

fun Timer.toUiModel(runtime: TimerRuntimeState?) = TimerUiModel(
    id = id,
    name = name,
    durationMs = durationMs,
    remainingMs = runtime?.remainingMs ?: durationMs,
    colorHex = colorHex,
    ringtoneUri = ringtoneUri,
    status = runtime?.status ?: TimerStatus.IDLE
)