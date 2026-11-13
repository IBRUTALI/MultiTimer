package com.ighorosipov.domain.stopwatch.use_case

import com.ighorosipov.domain.stopwatch.StopwatchController
import javax.inject.Inject

class StopStopwatchUseCase @Inject constructor(
    val controller: StopwatchController
) {
    operator fun invoke(id: String) = controller.stop(id)
}