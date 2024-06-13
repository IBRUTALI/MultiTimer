package com.ighorosipov.multitimer.domain.use_case

import com.ighorosipov.multitimer.domain.repository.TimerRepository
import javax.inject.Inject

class StartTimerUseCase @Inject constructor(
    repository: TimerRepository
) {

    operator fun invoke() {

    }

}