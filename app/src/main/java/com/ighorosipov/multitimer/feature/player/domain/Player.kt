package com.ighorosipov.multitimer.feature.player.domain

interface Player {

    suspend fun play(uri: String)

    suspend fun pause()

    suspend fun stop()

    fun isPlaying(): Boolean
}