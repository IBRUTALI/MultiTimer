package com.ighorosipov.data.core.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.ighorosipov.data.db.model.TimerRuntimeEntity

@Dao
interface TimerRuntimeDao {

    @Query("SELECT * FROM timer_runtime WHERE status IN ('RUNNING', 'PAUSED')")
    suspend fun getActive(): List<TimerRuntimeEntity>

    @Query("SELECT * FROM timer_runtime WHERE id = :id")
    suspend fun getById(id: String): TimerRuntimeEntity?

    @Upsert
    suspend fun upsert(entity: TimerRuntimeEntity)

    @Query("DELETE FROM timer_runtime WHERE id = :id")
    suspend fun delete(id: String)

    @Query("DELETE FROM timer_runtime")
    suspend fun clear()

    @Query("DELETE FROM timer_runtime WHERE status = 'FINISHED'")
    suspend fun clearFinished()
}