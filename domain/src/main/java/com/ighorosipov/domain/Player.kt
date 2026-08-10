package com.ighorosipov.domain

interface Player {

    suspend fun play(uri: String)

    suspend fun pause()

    suspend fun stop()

    fun isPlaying(): Boolean
}