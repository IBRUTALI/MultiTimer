package com.ighorosipov.multitimer.domain.use_case

import com.ighorosipov.multitimer.domain.model.Timer
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class StartTimerUseCase @Inject constructor(

) {

    suspend operator fun invoke(timer: Timer) = flow {
        var startTime = timer.startTime
        val format = SimpleDateFormat("HH:mm:ss", Locale.ENGLISH)
        while (startTime >= 0) {
            kotlinx.coroutines.delay(1000)
            val date = java.util.Date(startTime)
            emit(
                timer.copy(
                    startTime = startTime,
                    timeString = format.format(date),
                    isRunning = true
                )
            )
            startTime -= 1000
        }
    }

}