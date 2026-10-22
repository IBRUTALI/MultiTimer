package com.ighorosipov.data.timer.mapper

import com.ighorosipov.data.db.model.TimerRuntimeEntity
import com.ighorosipov.domain.timer.model.TimerRuntimeState
import com.ighorosipov.domain.timer.model.TimerStatus

fun TimerRuntimeEntity.toDomain() = TimerRuntimeState(
    id = id,
    name = name,
    remainingMs = remainingMs,
    durationMs = durationMs,
    ringtoneUri = ringtoneUri,
    status = TimerStatus.valueOf(status)
)

fun TimerRuntimeState.toEntity() = TimerRuntimeEntity(
    id = id,
    name = name,
    remainingMs = remainingMs,
    durationMs = durationMs,
    ringtoneUri = ringtoneUri,
    status = status.name,
    updatedAt = System.currentTimeMillis()
)