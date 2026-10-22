package com.ighorosipov.data.di

import android.content.Context
import android.media.RingtoneManager
import com.ighorosipov.data.ringtone.repository.RingtoneRepositoryImpl
import com.ighorosipov.domain.ringtone.repository.RingtoneRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RingtoneDataModule {

    companion object {

        @Provides
        @Singleton
        fun provideRingtoneManager(context: Context): RingtoneManager = RingtoneManager(context)

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

    }

}