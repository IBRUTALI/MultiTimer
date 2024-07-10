package com.ighorosipov.multitimer.domain.use_case

import com.ighorosipov.multitimer.domain.model.Timer
import com.ighorosipov.multitimer.domain.model.TimerEvent
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class StartTimerUseCase @Inject constructor() {

    suspend operator fun invoke(timer: Timer) = flow {
        var startTime = timer.time
        while (startTime >= 0) {
            emit(
                timer.copy(
                    time = startTime
                )
            )
            startTime -= 1000
        }
        startTime = 0
        while(true) {
            startTime += 1000
            emit(
                timer.copy(
                    time = startTime,
                    event = TimerEvent.Overtime
                )
            )
        }
    }.cancellable()

}