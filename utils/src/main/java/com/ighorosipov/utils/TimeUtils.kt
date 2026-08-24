package com.ighorosipov.utils

import java.util.concurrent.TimeUnit

enum class TimeFormat(val formatStr: String) {
    HH_MM_SS("%02d:%02d:%02d"),   // 00:00:00
    MM_SS("%02d:%02d"),           // 00:00
    HH_MM("%02d:%02d"),           // 00:00
    SS("%02d"),                   // 00
    MS("%d.%d"),                  // 0.0
}

fun formatTime(
    timeMs: Long,
    format: TimeFormat = TimeFormat.HH_MM_SS
): String {
    val totalSeconds = timeMs / 1000
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60
    val millis = (timeMs % 1000) / 100

    return when (format) {
        TimeFormat.HH_MM_SS -> format.formatStr.format(hours, minutes, seconds)
        TimeFormat.MM_SS -> format.formatStr.format(minutes + hours * 60, seconds)
        TimeFormat.HH_MM -> format.formatStr.format(hours, minutes)
        TimeFormat.SS ->format.formatStr.format(totalSeconds)
        TimeFormat.MS -> format.formatStr.format(totalSeconds, millis)
    }
}

fun getTimeLongFromString(
    value: String,
    timeUnit: TimeUnit
): Long? {
    val number = value.trim().toLongOrNull() ?: return null
    if (number < 0) return null

    return when (timeUnit) {
        TimeUnit.HOURS -> TimeUnit.HOURS.toMillis(number)
        TimeUnit.MINUTES -> TimeUnit.MINUTES.toMillis(number)
        TimeUnit.SECONDS -> TimeUnit.SECONDS.toMillis(number)
        TimeUnit.MILLISECONDS -> number
        else -> null
    }
}