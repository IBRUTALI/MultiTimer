package com.ighorosipov.data.timer.repository

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import com.ighorosipov.data.core.room.TimerRuntimeDao
import com.ighorosipov.data.db.model.TimerRuntimeEntity
import com.ighorosipov.data.di.IODispatcher
import com.ighorosipov.data.timer.mapper.toDomain
import com.ighorosipov.domain.timer.model.TimerRuntimeState
import com.ighorosipov.domain.timer.repository.TimerRuntimeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TimerRuntimeRepositoryImpl @Inject constructor(
    private val dao: TimerRuntimeDao,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : TimerRuntimeRepository {

    override suspend fun getActive(): List<TimerRuntimeState> {
        return withContext(dispatcher) {
            dao.getActive().map { it.toDomain() }
        }
    }


    override suspend fun getById(id: String): TimerRuntimeState? {
        return withContext(dispatcher) {
            dao.getById(id)?.toDomain()
        }
    }


    override suspend fun upsert(state: TimerRuntimeState) {
        withContext(dispatcher) {
            try {
                dao.upsert(
                    TimerRuntimeEntity(
                        id = state.id,
                        name = state.name,
                        remainingMs = state.remainingMs,
                        durationMs = state.durationMs,
                        ringtoneUri = state.ringtoneUri,
                        status = state.status.name,
                        updatedAt = System.currentTimeMillis()
                    )
                )
            } catch (e: SQLiteConstraintException) {
                e.printStackTrace()
                Log.d("RuntimeRepo", "Skipped persist for orphan ${state.id}")
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("RuntimeRepo", "Persist failed", e)
            }
        }
    }

    override suspend fun delete(id: String) {
        return withContext(dispatcher) {
            dao.delete(id)
        }
    }

    override suspend fun clear() {
        return withContext(dispatcher) {
            dao.clear()
        }
    }
}