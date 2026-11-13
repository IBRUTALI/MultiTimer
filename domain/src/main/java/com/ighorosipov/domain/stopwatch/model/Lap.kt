package com.ighorosipov.domain.stopwatch.model

data class Lap(
    val index: Int,
    val splitTimeMs: Long,
    val totalTimeMs: Long
)
