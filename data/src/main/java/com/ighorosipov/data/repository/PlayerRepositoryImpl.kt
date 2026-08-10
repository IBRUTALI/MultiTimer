package com.ighorosipov.data.repository

import android.content.Context
import android.media.MediaPlayer
import androidx.core.net.toUri
import com.ighorosipov.domain.repository.PlayerRepository
import javax.inject.Inject

class PlayerRepositoryImpl @Inject constructor(
    private val context: Context,
    private val mediaPlayer: MediaPlayer
): PlayerRepository {

    override suspend fun play(uri: String) {
        mediaPlayer.reset()
        mediaPlayer.setDataSource(context, uri.toUri())
        mediaPlayer.prepare()
        mediaPlayer.start()
    }

    override suspend fun pause() {
        mediaPlayer.pause()
    }

    override suspend fun stop() {
        mediaPlayer.stop()
        mediaPlayer.reset()
    }

    override fun isPlaying(): Boolean {
        return mediaPlayer.isPlaying
    }


}