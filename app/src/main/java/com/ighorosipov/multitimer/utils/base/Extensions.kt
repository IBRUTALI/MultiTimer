package com.ighorosipov.multitimer.utils.base

fun String.toTimeMinutesInMillis(): Long {
    val time = this.toDoubleOrNull() ?: return 0L
    return (time * 1000 * 60).toLong()
}