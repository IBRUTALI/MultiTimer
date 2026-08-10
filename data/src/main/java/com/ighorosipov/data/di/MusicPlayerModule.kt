package com.ighorosipov.data.di

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import com.ighorosipov.data.repository.PlayerRepositoryImpl
import com.ighorosipov.domain.repository.PlayerRepository
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
        fun providePlayer(context: Context, mediaPlayer: MediaPlayer): PlayerRepository {
            return PlayerRepositoryImpl(context, mediaPlayer)
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