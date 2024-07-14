package com.ighorosipov.multitimer.data.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "timer")
data class TimerEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "time") val time: Long,
)
