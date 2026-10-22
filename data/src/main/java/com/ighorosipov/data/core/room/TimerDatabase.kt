package com.ighorosipov.data.core.room

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ighorosipov.data.db.TimerConverters
import com.ighorosipov.data.db.model.TimerEntity
import com.ighorosipov.data.db.model.TimerRuntimeEntity
import java.util.concurrent.Executors

@Database(
    entities = [
        TimerEntity::class,
        TimerRuntimeEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(TimerConverters::class)
abstract class TimerDatabase: RoomDatabase() {

    abstract fun timerDao(): TimerDao
    abstract fun timerRuntimeDao(): TimerRuntimeDao

    companion object {

        fun getDB(context: Context): TimerDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                TimerDatabase::class.java,
                DATABASE_NAME
            )
                .fallbackToDestructiveMigration(true) // todo add migration
                .setQueryCallback({ sql, args ->
                    Log.d(TIMER_DATABASE_LOG, "$sql args=$args")
                }, Executors.newSingleThreadExecutor())
                .build()
        }

        private const val DATABASE_NAME = "timer_db"
        private const val TIMER_DATABASE_LOG = "TIMER_DATABASE_LOG"

    }

}