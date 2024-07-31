package com.ighorosipov.multitimer.feature.player.domain.repository

interface PlayerRepository {

    suspend fun play(uri: String)

    suspend fun pause()

    suspend fun stop()

    fun isPlaying(): Boolean
}