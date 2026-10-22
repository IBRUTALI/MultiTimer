package com.ighorosipov.data.timer.mapper

import com.ighorosipov.data.db.model.TimerEntity
import com.ighorosipov.domain.timer.model.Timer

fun TimerEntity.toDomain(): Timer {
    return Timer(
        id = id,
        name = name,
        durationMs = duration,
        colorHex = colorHex,
        ringtoneUri = ringtoneUri
    )
}

fun Timer.toEntity(): TimerEntity {
    return TimerEntity(
        id = id,
        duration = durationMs,
        name = name,
        colorHex = colorHex,
        ringtoneUri = ringtoneUri
    )
}
