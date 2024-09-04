package com.ighorosipov.multitimer.feature.timer.data.repository

import com.ighorosipov.multitimer.core.room.TimerDao
import com.ighorosipov.multitimer.feature.timer.data.mapper.TimerMapper
import com.ighorosipov.multitimer.feature.timer.domain.model.Timer
import com.ighorosipov.multitimer.feature.timer.domain.repository.TimerRepository
import com.ighorosipov.multitimer.utils.Resource
import javax.inject.Inject

class TimerRepositoryImpl @Inject constructor(
    private val dao: TimerDao
) : TimerRepository {

    override suspend fun insertTimer(timer: Timer): Resource<Unit> {
        return try {
            dao.insertTimer(timerEntity = TimerMapper().timerToTimerEntity(timer))
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "An unexpected error occurred", null)
        }
    }

    override suspend fun getTimerById(timerId: String): Resource<Timer> {
        return try {
            val timerEntity = dao.getTimerById(timerId = timerId)
            Resource.Success(data = TimerMapper().timerEntityToTimer(timerEntity))
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "An unexpected error occurred", null)
        }
    }

    override suspend fun getTimers(): Resource<List<Timer>> {
        return try {
            val listOfTimerEntity = dao.getTimers()
            Resource.Success(data = listOfTimerEntity.map {  timerEntity ->
                TimerMapper().timerEntityToTimer(timerEntity)
            })
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "An unexpected error occurred", null)
        }
    }

    override suspend fun deleteTimer(timer: Timer): Resource<Unit> {
        return try {
            dao.deleteTimer(timerEntity = TimerMapper().timerToTimerEntity(timer))
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "An unexpected error occurred", null)
        }
    }

}