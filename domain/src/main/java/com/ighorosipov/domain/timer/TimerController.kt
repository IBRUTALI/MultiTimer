package com.ighorosipov.domain.timer

interface TimerController {
    fun start(id: String, durationMs: Long, name: String, ringtoneUri: String?)
    fun pause(id: String)
    fun stop(id: String, durationMs: Long)
    fun stopAll()
}