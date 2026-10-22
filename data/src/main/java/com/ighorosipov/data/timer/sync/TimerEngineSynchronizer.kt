package com.ighorosipov.data.timer.sync

import com.ighorosipov.domain.timer.repository.TimerEngine
import com.ighorosipov.domain.timer.repository.TimerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TimerEngineSynchronizer @Inject constructor(
    private val engine: TimerEngine,
    private val timerRepository: TimerRepository
) {
    private var job: kotlinx.coroutines.Job? = null

    fun start(scope: CoroutineScope) {
        if (job?.isActive == true) return

        job = scope.launch {
            timerRepository.observeTimers()
                .map { timers -> timers.map { it.id }.toSet() }
                .distinctUntilChanged()
                .drop(1)
                .collect { existingIds ->
                    val orphans = engine.snapshot().filter { it.id !in existingIds }
                    orphans.forEach { state ->
                        engine.stop(state.id, state.durationMs)
                    }
                }
        }
    }

    fun stop() {
        job?.cancel()
        job = null
    }
}