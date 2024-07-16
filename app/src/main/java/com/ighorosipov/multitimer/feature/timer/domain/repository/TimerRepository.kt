package com.ighorosipov.multitimer.feature.timer.domain.repository

import com.ighorosipov.multitimer.feature.timer.domain.model.Timer
import com.ighorosipov.multitimer.utils.Resource

interface TimerRepository {

    suspend fun insertTimer(timer: Timer): Resource<Unit>

    suspend fun getTimerById(timerId: String): Resource<Timer>

    suspend fun getTimers(): Resource<List<Timer>>

    suspend fun deleteTimer(timer: Timer): Resource<Unit>

}