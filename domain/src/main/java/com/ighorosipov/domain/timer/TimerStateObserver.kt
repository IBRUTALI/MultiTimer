package com.ighorosipov.domain.timer

import com.ighorosipov.domain.timer.model.TimerRuntimeState
import kotlinx.coroutines.flow.Flow

interface TimerStateObserver {
    fun states(): Flow<Map<String, TimerRuntimeState>>
    fun stateFor(id: String): Flow<TimerRuntimeState?>
    fun isRunning(id: String): Boolean
    fun snapshot(): List<TimerRuntimeState>
}