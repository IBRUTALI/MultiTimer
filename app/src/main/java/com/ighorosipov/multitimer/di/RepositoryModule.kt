package com.ighorosipov.multitimer.di

import android.media.RingtoneManager
import com.ighorosipov.multitimer.feature.ringtone.data.repository.RingtoneRepositoryImpl
import com.ighorosipov.multitimer.feature.ringtone.domain.repository.RingtoneRepository
import com.ighorosipov.multitimer.feature.timer.data.repository.TimerRepositoryImpl
import com.ighorosipov.multitimer.core.room.TimerDao
import com.ighorosipov.multitimer.feature.timer.domain.repository.TimerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule  {

    companion object {

        @Provides
        @Singleton
        fun provideTimerRepository(timerDao: TimerDao): TimerRepository {
            return TimerRepositoryImpl(timerDao)
        }

        @Provides
        @Singleton
        fun provideRingtoneRepository(ringtoneManager: RingtoneManager): RingtoneRepository {
            return RingtoneRepositoryImpl(ringtoneManager)
        }

    }

}