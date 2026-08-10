package com.ighorosipov.data.mapper

import com.ighorosipov.data.db.model.TimerEntity
import com.ighorosipov.domain.model.Timer
import com.ighorosipov.domain.model.TimerEvent

class TimerMapper {

    fun timerEntityToTimer(timerEntity: TimerEntity): Timer {
        return Timer(
            id = timerEntity.id,
            time = timerEntity.time,
            name = timerEntity.name,
            color = timerEntity.color,
            ringtone = timerEntity.ringtone,
            event = TimerEvent.Pause
        )
    }

    fun timerToTimerEntity(timer: Timer): TimerEntity {
        return TimerEntity(
            id = timer.id,
            time = timer.time,
            name = timer.name,
            color = timer.color,
            ringtone = timer.ringtone,
        )
    }

}