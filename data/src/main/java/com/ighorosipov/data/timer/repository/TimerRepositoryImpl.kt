package com.ighorosipov.data.timer.repository

import com.ighorosipov.data.core.room.TimerDao
import com.ighorosipov.data.di.IODispatcher
import com.ighorosipov.data.timer.mapper.toDomain
import com.ighorosipov.data.timer.mapper.toEntity
import com.ighorosipov.domain.timer.model.Timer
import com.ighorosipov.domain.timer.repository.TimerRepository
import com.ighorosipov.utils.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TimerRepositoryImpl @Inject constructor(
    private val dao: TimerDao,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : TimerRepository {

    override suspend fun insertTimer(timer: Timer): Resource<Unit> {
        return withContext(dispatcher) {
            try {
                dao.insertTimer(timerEntity = timer.toEntity())
                Resource.Success(Unit)
            } catch (e: Exception) {
                Resource.Error(e.localizedMessage ?: "An unexpected error occurred", null)
            }
        }
    }

    override suspend fun getTimerById(timerId: String): Resource<Timer> {
        return withContext(dispatcher) {
            try {
                val timerEntity = dao.getTimerById(timerId = timerId)
                Resource.Success(data = timerEntity.toDomain())
            } catch (e: Exception) {
                Resource.Error(e.localizedMessage ?: "An unexpected error occurred", null)
            }
        }

    }

    override suspend fun getTimers(): Resource<List<Timer>> {
        return withContext(dispatcher) {
            try {
                val listOfTimerEntity = dao.getTimers()
                Resource.Success(data = listOfTimerEntity.map { timerEntity ->
                    timerEntity.toDomain()
                })
            } catch (e: Exception) {
                Resource.Error(e.localizedMessage ?: "An unexpected error occurred", null)
            }
        }

    }

    override fun observeTimers(): Flow<List<Timer>> {
        return dao.observeTimers().map { list -> list.map { it.toDomain() } }
    }

    override suspend fun deleteTimer(timerId: String): Resource<Unit> {
        return withContext(dispatcher) {
            try {
                dao.deleteTimer(timerId = timerId)
                Resource.Success(Unit)
            } catch (e: Exception) {
                Resource.Error(e.localizedMessage ?: "An unexpected error occurred", null)
            }
        }
    }

}