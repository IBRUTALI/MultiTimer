package com.ighorosipov.domain.stopwatch

interface StopwatchController {
    fun start(id: String, name: String, colorHex: String)
    fun pause(id: String)
    fun resume(id: String)
    fun stop(id: String)
    fun addLap(id: String)
    fun stopAll()
}