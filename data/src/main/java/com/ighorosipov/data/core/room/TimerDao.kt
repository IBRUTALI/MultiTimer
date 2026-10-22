package com.ighorosipov.data.core.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ighorosipov.data.db.model.TimerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TimerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTimer(timerEntity: TimerEntity)

    @Query("SELECT * FROM timer WHERE id =:timerId")
    suspend fun getTimerById(timerId: String): TimerEntity

    @Query("SELECT * FROM timer")
    suspend fun getTimers(): List<TimerEntity>

    @Query("SELECT * FROM timer ORDER BY created_at DESC")
    fun observeTimers(): Flow<List<TimerEntity>>

    @Query("DELETE FROM timer WHERE id = :timerId")
    suspend fun deleteTimer(timerId: String)

}