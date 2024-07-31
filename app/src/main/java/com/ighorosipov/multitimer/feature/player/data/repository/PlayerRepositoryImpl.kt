package com.ighorosipov.multitimer.feature.player.data.repository

import android.media.MediaPlayer
import androidx.core.net.toUri
import com.ighorosipov.multitimer.feature.player.domain.repository.PlayerRepository
import javax.inject.Inject

class PlayerRepositoryImpl @Inject constructor(
    private val mediaPlayer: MediaPlayer
): PlayerRepository {

    override suspend fun play(uri: String) {
        mediaPlayer.reset()
        mediaPlayer.setDataSource(uri.toUri().path)
        mediaPlayer.prepare()
        mediaPlayer.start()
    }

    override suspend fun pause() {
        mediaPlayer.pause()
    }

    override suspend fun stop() {
        mediaPlayer.stop()
    }

    override fun isPlaying(): Boolean {
        return mediaPlayer.isPlaying
    }


}