package com.ighorosipov.multitimer.di

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import com.ighorosipov.multitimer.feature.player.PlayerImpl
import com.ighorosipov.multitimer.feature.player.domain.Player
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface MusicPlayerModule {


    companion object {

        @Provides
        @Singleton
        fun provideMediaPlayer(audioAttributes: AudioAttributes): MediaPlayer {
            return MediaPlayer().apply {
                isLooping = false
                setAudioAttributes(audioAttributes)
            }
        }

        @Provides
        @Singleton
        fun providePlayer(context: Context, mediaPlayer: MediaPlayer): Player {
            return PlayerImpl(context, mediaPlayer)
        }

        @Provides
        @Singleton
        fun provideAudioAttributes(): AudioAttributes {
            return AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()
        }

    }

}