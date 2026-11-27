package com.ighorosipov.data.repository.timer

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.ighorosipov.data.core.room.TimerDao
import com.ighorosipov.data.core.room.TimerDatabase
import com.ighorosipov.data.timer.repository.TimerRepositoryImpl
import com.ighorosipov.domain.timer.model.Timer
import com.ighorosipov.utils.Resource
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class TimerRepositoryImplTest {

    private lateinit var database: TimerDatabase
    private lateinit var dao: TimerDao
    private lateinit var repository: TimerRepositoryImpl

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            TimerDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.timerDao()
        repository = TimerRepositoryImpl(dao, Dispatchers.Unconfined)
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun `insertTimer saves timer to database`() = runTest {
        val timer = Timer(
            id = "1",
            name = "Test Timer",
            durationMs = 60000L,
            colorHex = "#FFFFFF",
            ringtoneUri = null
        )

        val result = repository.insertTimer(timer)

        TestCase.assertTrue(result is Resource.Success)
        TestCase.assertEquals(1, dao.getTimers().size)
    }

    @Test
    fun `getTimerById returns correct timer`() = runTest {
        val timer = Timer(
            id = "1",
            name = "Test Timer",
            durationMs = 60000L,
            colorHex = "#FFFFFF",
            ringtoneUri = null
        )
        repository.insertTimer(timer)

        val result = repository.getTimerById("1")

        TestCase.assertTrue(result is Resource.Success)
        TestCase.assertEquals("Test Timer", (result as Resource.Success).data?.name)
    }

    @Test
    fun `deleteTimer removes timer from database`() = runTest {
        val timer = Timer(
            id = "1",
            name = "Test",
            durationMs = 60000L,
            colorHex = "#FFFFFF",
            ringtoneUri = null
        )
        repository.insertTimer(timer)

        repository.deleteTimer("1")

        TestCase.assertEquals(0, dao.getTimers().size)
    }

    @Test
    fun `observeTimers emits updates when data changes`() = runTest {
        val timer = Timer(
            id = "1",
            name = "Test",
            durationMs = 60000L,
            colorHex = "#FFFFFF",
            ringtoneUri = null
        )
        repository.insertTimer(timer)

        val firstEmission = repository.observeTimers().first()
        TestCase.assertEquals(1, firstEmission.size)

        repository.insertTimer(timer.copy(id = "2"))
        val secondEmission = repository.observeTimers().first()
        TestCase.assertEquals(2, secondEmission.size)
    }
}