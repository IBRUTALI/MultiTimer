package com.ighorosipov.domain.repository

import com.ighorosipov.domain.model.TimerRuntimeState
import kotlinx.coroutines.flow.StateFlow


interface TimerEngine {

    val states: StateFlow<Map<String, TimerRuntimeState>>

    fun start(
        id: String,
        durationMs: Long,
        name: String,
        ringtoneUri: String?
    )

    fun pause(
        id: String
    )

    fun stop(
        id: String,
        durationMs: Long
    )

    fun stopAll()

    fun tick(
        deltaMs: Long
    ): List<TimerRuntimeState>

    fun hasRunning(): Boolean

    fun snapshot(): List<TimerRuntimeState>
}