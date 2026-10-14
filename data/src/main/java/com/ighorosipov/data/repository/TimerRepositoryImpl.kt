package com.ighorosipov.data.repository

import com.ighorosipov.data.core.room.TimerDao
import com.ighorosipov.data.mapper.toDomain
import com.ighorosipov.data.mapper.toEntity
import com.ighorosipov.domain.model.Timer
import com.ighorosipov.domain.repository.TimerRepository
import com.ighorosipov.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TimerRepositoryImpl @Inject constructor(
    private val dao: TimerDao
) : TimerRepository {

    override suspend fun insertTimer(timer: Timer): Resource<Unit> {
        return try {
            dao.insertTimer(timerEntity = timer.toEntity())
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "An unexpected error occurred", null)
        }
    }

    override suspend fun getTimerById(timerId: String): Resource<Timer> {
        return try {
            val timerEntity = dao.getTimerById(timerId = timerId)
            Resource.Success(data = timerEntity.toDomain())
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "An unexpected error occurred", null)
        }
    }

    override suspend fun getTimers(): Resource<List<Timer>> {
        return try {
            val listOfTimerEntity = dao.getTimers()
            Resource.Success(data = listOfTimerEntity.map {  timerEntity ->
                timerEntity.toDomain()
            })
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "An unexpected error occurred", null)
        }
    }

    override fun observeTimers(): Flow<List<Timer>> {
        return dao.observeTimers().map { list -> list.map { it.toDomain() } }
    }

    override suspend fun deleteTimer(timerId: String): Resource<Unit> {
        return try {
            dao.deleteTimer(timerId = timerId)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "An unexpected error occurred", null)
        }
    }

}