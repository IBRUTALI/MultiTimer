package com.ighorosipov.data.di

import android.media.RingtoneManager
import com.ighorosipov.data.core.room.TimerDao
import com.ighorosipov.data.repository.RingtoneRepositoryImpl
import com.ighorosipov.data.repository.TimerEngineImpl
import com.ighorosipov.data.repository.TimerRepositoryImpl
import com.ighorosipov.domain.repository.RingtoneRepository
import com.ighorosipov.domain.repository.TimerEngine
import com.ighorosipov.domain.repository.TimerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule  {

    companion object {

        @Provides
        @Singleton
        fun provideTimerRepository(
            timerDao: TimerDao,
            @IODispatcher dispatcher: CoroutineDispatcher
        ): TimerRepository {
            return TimerRepositoryImpl(
                dao = timerDao,
                dispatcher = dispatcher
            )
        }

        @Provides
        @Singleton
        fun provideRingtoneRepository(
            ringtoneManager: RingtoneManager,
            @IODispatcher dispatcher: CoroutineDispatcher
        ): RingtoneRepository {
            return RingtoneRepositoryImpl(
                ringtoneManager = ringtoneManager,
                dispatcher = dispatcher
            )
        }

        @Provides
        @Singleton
        fun provideTimerEngine(): TimerEngine {
            return TimerEngineImpl()
        }

    }

}