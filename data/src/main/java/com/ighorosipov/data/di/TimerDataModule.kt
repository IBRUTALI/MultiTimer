package com.ighorosipov.data.di

import com.ighorosipov.data.timer.AndroidTimerController
import com.ighorosipov.data.timer.TimerStateObserverImpl
import com.ighorosipov.data.timer.repository.TimerEngineImpl
import com.ighorosipov.data.timer.repository.TimerRepositoryImpl
import com.ighorosipov.data.timer.repository.TimerRuntimeRepositoryImpl
import com.ighorosipov.domain.timer.TimerController
import com.ighorosipov.domain.timer.TimerStateObserver
import com.ighorosipov.domain.timer.repository.TimerEngine
import com.ighorosipov.domain.timer.repository.TimerRepository
import com.ighorosipov.domain.timer.repository.TimerRuntimeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TimerDataModule {

    @Binds
    @Singleton
    abstract fun bindTimerRepository(
        impl: TimerRepositoryImpl
    ): TimerRepository

    @Binds
    @Singleton
    abstract fun bindTimerRuntimeRepository(
        impl: TimerRuntimeRepositoryImpl
    ): TimerRuntimeRepository

    @Binds
    @Singleton
    abstract fun bindTimerController(
        impl: AndroidTimerController
    ): TimerController

    @Binds
    @Singleton
    abstract fun bindTimerStateObserver(
        impl: TimerStateObserverImpl
    ): TimerStateObserver

    @Binds
    @Singleton
    abstract fun bindTimerEngine(
        impl: TimerEngineImpl
    ): TimerEngine
}