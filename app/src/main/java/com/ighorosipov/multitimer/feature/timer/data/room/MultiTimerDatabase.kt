package com.ighorosipov.multitimer.feature.timer.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ighorosipov.multitimer.feature.timer.data.room.model.TimerEntity

@Database(
    entities = [TimerEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MultiTimerDatabase: RoomDatabase() {

    abstract val timerDao: TimerDao

    companion object {

        fun getDB(context: Context): MultiTimerDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                MultiTimerDatabase::class.java,
                DATABASE_NAME
            ).build()
        }

        private const val DATABASE_NAME = "multi_timer_db"

    }

}