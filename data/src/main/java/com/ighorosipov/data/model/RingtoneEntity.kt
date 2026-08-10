package com.ighorosipov.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "ringtone")
data class RingtoneEntity(
    @ColumnInfo("title")
    val title: String,
    @ColumnInfo("uri")
    val uri: String
)