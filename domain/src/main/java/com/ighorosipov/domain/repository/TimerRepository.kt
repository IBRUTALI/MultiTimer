package com.ighorosipov.domain.repository

import com.ighorosipov.domain.model.Timer
import com.ighorosipov.utils.Resource

interface TimerRepository {

    suspend fun insertTimer(timer: Timer): Resource<Unit>

    suspend fun getTimerById(timerId: String): Resource<Timer>

    suspend fun getTimers(): Resource<List<Timer>>

    suspend fun deleteTimer(timer: Timer): Resource<Unit>

}