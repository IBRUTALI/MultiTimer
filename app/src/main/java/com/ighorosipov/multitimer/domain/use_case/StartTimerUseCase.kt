package com.ighorosipov.multitimer.domain.use_case

import com.ighorosipov.multitimer.domain.model.Timer
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class StartTimerUseCase @Inject constructor(

) {

    suspend operator fun invoke(timer: Timer) = flow {
        var startTime = timer.startTime
        while (startTime >= 0) {
            kotlinx.coroutines.delay(1000)
            emit(
                timer.copy(
                    startTime = startTime,
                    isRunning = true
                )
            )
            startTime -= 1000
        }
    }

}