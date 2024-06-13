package com.ighorosipov.multitimer.domain.repository

interface TimerRepository {

    fun addTimer()

    fun deleteTimer()

    fun startTimer()

    fun pauseTimer()

}