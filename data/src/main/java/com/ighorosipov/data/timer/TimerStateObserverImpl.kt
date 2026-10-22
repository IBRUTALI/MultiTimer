package com.ighorosipov.data.timer

import com.ighorosipov.domain.timer.model.TimerRuntimeState
import com.ighorosipov.domain.timer.model.isRunning
import com.ighorosipov.domain.timer.repository.TimerEngine
import com.ighorosipov.domain.timer.TimerStateObserver
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TimerStateObserverImpl @Inject constructor(
    private val engine: TimerEngine
) : TimerStateObserver {

    override fun states(): Flow<Map<String, TimerRuntimeState>> = engine.states

    override fun stateFor(id: String): Flow<TimerRuntimeState?> =
        engine.states.map { it[id] }

    override fun isRunning(id: String): Boolean =
        engine.snapshot().firstOrNull { it.id == id }?.status?.isRunning() == true

    override fun snapshot(): List<TimerRuntimeState> = engine.snapshot()
}