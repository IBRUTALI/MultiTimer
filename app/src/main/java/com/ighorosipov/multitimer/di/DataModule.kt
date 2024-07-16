package com.ighorosipov.multitimer.di

import android.content.Context
import com.ighorosipov.multitimer.feature.timer.data.room.MultiTimerDatabase
import com.ighorosipov.multitimer.feature.timer.data.room.TimerDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    companion object {
        @Singleton
        @Provides
        fun provideMultiTimerDatabase(context: Context): MultiTimerDatabase {
            return MultiTimerDatabase.getDB(context)
        }

        @Singleton
        @Provides
        fun provideTimerDao(multiTimerDatabase: MultiTimerDatabase): TimerDao {
            return multiTimerDatabase.timerDao
        }

    }


}