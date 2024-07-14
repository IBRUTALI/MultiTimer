package com.ighorosipov.multitimer.di

import com.ighorosipov.multitimer.domain.use_case.StartTimerUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
interface DispatchersModule {

    companion object {

        @DefaultDispatcher
        @Provides
        fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

        @IODispatcher
        @Provides
        fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

        @MainDispatcher
        @Provides
        fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

        @Provides
        fun provideStartTimerUseCase(): StartTimerUseCase = StartTimerUseCase()

    }

}