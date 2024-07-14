package com.ighorosipov.multitimer.data.mapper

import com.ighorosipov.multitimer.data.room.model.TimerEntity
import com.ighorosipov.multitimer.domain.model.Timer
import com.ighorosipov.multitimer.domain.model.TimerEvent

class TimerMapper {

    fun timerEntityToTimer(timerEntity: TimerEntity): Timer {
        return Timer(
            id = timerEntity.id,
            time = timerEntity.time,
            event = TimerEvent.Pause
        )
    }

    fun timerToTimerEntity(timer: Timer): TimerEntity {
        return TimerEntity(
            id = timer.id,
            time = timer.time
        )
    }

}