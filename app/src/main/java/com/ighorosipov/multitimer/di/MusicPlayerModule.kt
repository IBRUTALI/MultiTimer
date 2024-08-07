package com.ighorosipov.multitimer.di

import android.media.AudioAttributes
import android.media.MediaPlayer
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
        fun provideAudioAttributes(): AudioAttributes {
            return AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()
        }

    }

}