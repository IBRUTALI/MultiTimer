package com.ighorosipov.domain.timer.repository

import com.ighorosipov.domain.timer.model.TimerRuntimeState
import kotlinx.coroutines.flow.StateFlow


interface TimerEngine {
    val states: StateFlow<Map<String, TimerRuntimeState>>
    fun snapshot(): List<TimerRuntimeState>
    fun hasRunning(): Boolean
    fun start(id: String, durationMs: Long, name: String, ringtoneUri: String?)
    fun pause(id: String)
    fun stop(id: String, durationMs: Long)
    fun stopAll()
    fun restore(states: List<TimerRuntimeState>)
    fun tick(deltaMs: Long): List<TimerRuntimeState>
}