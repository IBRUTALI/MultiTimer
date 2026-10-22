package com.ighorosipov.domain.timer.repository

import com.ighorosipov.domain.timer.model.Timer
import com.ighorosipov.utils.Resource
import kotlinx.coroutines.flow.Flow

interface TimerRepository {

    suspend fun insertTimer(timer: Timer): Resource<Unit>

    suspend fun getTimerById(timerId: String): Resource<Timer>

    suspend fun getTimers(): Resource<List<Timer>>

    fun observeTimers(): Flow<List<Timer>>

    suspend fun deleteTimer(timerId: String): Resource<Unit>

}