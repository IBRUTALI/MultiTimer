package com.ighorosipov.data.stopwatch

import com.ighorosipov.domain.stopwatch.StopwatchStateObserver
import com.ighorosipov.domain.stopwatch.model.Stopwatch
import com.ighorosipov.domain.stopwatch.repository.StopwatchEngine
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StopwatchStateObserverImpl @Inject constructor(
    private val engine: StopwatchEngine
) : StopwatchStateObserver {

    override fun states(): Flow<Map<String, Stopwatch>> = engine.states

    override fun snapshot(): List<Stopwatch> = engine.snapshot()

    override fun isRunning(id: String): Boolean =
        engine.snapshot().firstOrNull { it.id == id }?.isRunning == true
}