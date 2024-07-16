package com.ighorosipov.multitimer.feature.timer.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ighorosipov.multitimer.feature.timer.data.room.model.TimerEntity

@Dao
interface TimerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTimer(timerEntity: TimerEntity)

    @Query("SELECT * FROM timer WHERE id =:timerId")
    suspend fun getTimerById(timerId: String): TimerEntity

    @Query("SELECT * FROM timer")
    suspend fun getTimers(): List<TimerEntity>

    @Delete
    suspend fun deleteTimer(timerEntity: TimerEntity)


}