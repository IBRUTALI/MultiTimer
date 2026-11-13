package com.ighorosipov.domain.stopwatch.repository

import com.ighorosipov.domain.stopwatch.model.Stopwatch

interface StopwatchRuntimeRepository {
    suspend fun getActive(): List<Stopwatch>
    suspend fun upsert(state: Stopwatch)
    suspend fun delete(id: String)
    suspend fun clear()
}