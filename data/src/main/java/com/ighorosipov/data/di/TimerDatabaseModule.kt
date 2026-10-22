package com.ighorosipov.data.di

import android.content.Context
import com.ighorosipov.data.core.room.TimerDao
import com.ighorosipov.data.core.room.TimerDatabase
import com.ighorosipov.data.core.room.TimerRuntimeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface TimerDatabaseModule {

    companion object {
        @Singleton
        @Provides
        fun provideTimerDatabase(context: Context): TimerDatabase {
            return TimerDatabase.getDB(context)
        }

        @Singleton
        @Provides
        fun provideTimerDao(timerDatabase: TimerDatabase): TimerDao {
            return timerDatabase.timerDao()
        }

        @Provides
        fun provideTimerRuntimeDao(db: TimerDatabase): TimerRuntimeDao = db.timerRuntimeDao()

    }


}