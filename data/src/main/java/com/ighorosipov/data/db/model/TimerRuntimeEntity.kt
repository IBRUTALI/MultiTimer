package com.ighorosipov.data.db.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "timer_runtime",
    foreignKeys = [
        ForeignKey(
            entity = TimerEntity::class,
            parentColumns = ["id"],
            childColumns = ["id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [Index("id")]
)
data class TimerRuntimeEntity(
    @PrimaryKey val id: String,
    val name: String,
    val remainingMs: Long,
    val durationMs: Long,
    val ringtoneUri: String?,
    val status: String,
    val updatedAt: Long
)