package com.ighorosipov.multitimer.feature.timer.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ighorosipov.multitimer.feature.ringtone.domain.model.Ringtone

@Entity(tableName = "timer")
data class TimerEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "color") val color: Int,
    @ColumnInfo(name = "ringtone") val ringtone: Ringtone,
    @ColumnInfo(name = "time") val time: Long,
)
