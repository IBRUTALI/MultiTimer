package com.ighorosipov.domain.timer.repository

import com.ighorosipov.domain.timer.model.TimerRuntimeState

interface TimerRuntimeRepository {
    suspend fun getActive(): List<TimerRuntimeState>
    suspend fun getById(id: String): TimerRuntimeState?
    suspend fun upsert(state: TimerRuntimeState)
    suspend fun delete(id: String)
    suspend fun clear()
}