package com.ighorosipov.domain.timer.model

data class Timer(
    val id: String,
    val name: String,
    val durationMs: Long,
    val colorHex: String,
    val ringtoneUri: String?
)