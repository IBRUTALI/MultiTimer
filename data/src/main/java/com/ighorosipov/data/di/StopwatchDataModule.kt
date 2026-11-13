package com.ighorosipov.data.di

import com.ighorosipov.data.stopwatch.AndroidStopwatchController
import com.ighorosipov.data.stopwatch.StopwatchStateObserverImpl
import com.ighorosipov.data.stopwatch.repository.StopwatchEngineImpl
import com.ighorosipov.domain.stopwatch.StopwatchController
import com.ighorosipov.domain.stopwatch.StopwatchStateObserver
import com.ighorosipov.domain.stopwatch.repository.StopwatchEngine
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class StopwatchDataModule {

    @Binds
    @Singleton
    abstract fun bindEngine(impl: StopwatchEngineImpl): StopwatchEngine

    @Binds
    @Singleton
    abstract fun bindController(impl: AndroidStopwatchController): StopwatchController

    @Binds
    @Singleton
    abstract fun bindObserver(impl: StopwatchStateObserverImpl): StopwatchStateObserver
}