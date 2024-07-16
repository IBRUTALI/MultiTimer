package com.ighorosipov.multitimer.feature.timer.domain.use_case

import com.ighorosipov.multitimer.feature.timer.domain.model.Timer
import com.ighorosipov.multitimer.feature.timer.domain.model.TimerEvent
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class StartTimerUseCase @Inject constructor() {

    suspend operator fun invoke(timer: Timer) = flow {
        var startTime = timer.time
        while (startTime >= 0) {
            emit(
                timer.copy(
                    time = startTime,
                    event = TimerEvent.Count
                )
            )
            startTime -= 300
        }
        startTime = 0
        while (true) {
            startTime += 300
            emit(
                timer.copy(
                    time = startTime,
                    event = TimerEvent.Overtime
                )
            )
        }
    }

}