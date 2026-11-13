package com.ighorosipov.domain.stopwatch.repository

import com.ighorosipov.domain.stopwatch.model.Stopwatch
import kotlinx.coroutines.flow.StateFlow

interface StopwatchEngine {

    val states: StateFlow<Map<String, Stopwatch>>
    fun snapshot(): List<Stopwatch>
    fun hasRunning(): Boolean
    fun start(id: String, name: String, colorHex: String)
    fun pause(id: String)
    fun resume(id: String)
    fun stop(id: String)
    fun addLap(id: String)
    fun stopAll()
    fun restore(states: List<Stopwatch>)
    fun tick(deltaMs: Long)
}