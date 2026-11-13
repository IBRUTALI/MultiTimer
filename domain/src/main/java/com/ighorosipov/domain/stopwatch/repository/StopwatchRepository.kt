package com.ighorosipov.domain.stopwatch.repository

import com.ighorosipov.domain.stopwatch.model.Stopwatch
import kotlinx.coroutines.flow.Flow

interface StopwatchRepository {
    fun observeActive(): Flow<List<Stopwatch>>
    suspend fun getActive(): List<Stopwatch>
    suspend fun upsert(state: Stopwatch)
    suspend fun delete(id: String)
    suspend fun clear()
}