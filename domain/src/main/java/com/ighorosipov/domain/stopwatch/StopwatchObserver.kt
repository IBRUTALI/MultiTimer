package com.ighorosipov.domain.stopwatch

import com.ighorosipov.domain.stopwatch.model.Stopwatch
import kotlinx.coroutines.flow.Flow

interface StopwatchStateObserver {
    fun states(): Flow<Map<String, Stopwatch>>
    fun snapshot(): List<Stopwatch>
    fun isRunning(id: String): Boolean
}