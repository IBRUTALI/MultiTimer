package com.ighorosipov.multitimer.di

import com.ighorosipov.domain.timer.notification.ActivityIntentProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindActivityIntentProvider(
        impl: ActivityIntentProviderImpl
    ): ActivityIntentProvider
}