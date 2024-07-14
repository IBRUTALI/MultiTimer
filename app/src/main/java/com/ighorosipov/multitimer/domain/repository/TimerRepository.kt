package com.ighorosipov.multitimer.domain.repository

import com.ighorosipov.multitimer.domain.model.Timer
import com.ighorosipov.multitimer.utils.Resource

interface TimerRepository {

    suspend fun insertTimer(timer: Timer): Resource<Unit>

    suspend fun getTimerById(timerId: String): Resource<Timer>

    suspend fun getTimers(): Resource<List<Timer>>

    suspend fun deleteTimer(timer: Timer): Resource<Unit>

}