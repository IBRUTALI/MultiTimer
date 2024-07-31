package com.ighorosipov.multitimer.di

import android.content.Context
import android.media.RingtoneManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RingtoneModule {

    companion object {

        @Provides
        @Singleton
        fun provideRingtoneManager(context: Context): RingtoneManager = RingtoneManager(context)

    }

}