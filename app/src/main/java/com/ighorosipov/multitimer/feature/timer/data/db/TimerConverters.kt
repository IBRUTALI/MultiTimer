package com.ighorosipov.multitimer.feature.timer.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.ighorosipov.multitimer.feature.ringtone.domain.model.Ringtone

class TimerConverters {

    @TypeConverter
    fun ringtoneToString(ringtone: Ringtone): String = Gson().toJson(ringtone)

    @TypeConverter
    fun stringToApp(string: String): Ringtone = Gson().fromJson(string, Ringtone::class.java)

}