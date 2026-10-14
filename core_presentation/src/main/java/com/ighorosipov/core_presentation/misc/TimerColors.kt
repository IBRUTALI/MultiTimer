package com.ighorosipov.core_presentation.misc

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance

object TimerColorPalette {
    private const val WHITE = "#FFFFFF"
    private const val BLUE = "#2196F3"
    private const val RED = "#FF7373"
    private const val GREEN = "#4CAF50"
    private const val GREEN_YELLOW = "#CDDC39"
    private const val TEAL = "#009688"
    private const val ORANGE = "#FF9800"
    private const val YELLOW = "#FFEB3B"
    private const val PURPLE = "#9C27B0"
    private const val PINK = "#E91E63"
    private const val GREY = "#9E9E9E"

    val colors: List<String> = listOf(
        WHITE,
        BLUE,
        RED,
        GREEN,
        GREEN_YELLOW,
        TEAL,
        ORANGE,
        YELLOW,
        PURPLE,
        PINK,
        GREY
    )
}

fun Color.toneForIcon(): Color {
    return if (luminance() > 0.5f) {
        this.blendWith(Color.Black, 0.45f)
    } else {
        this.blendWith(Color.White, 0.55f)
    }
}

fun Color.blendWith(other: Color, ratio: Float): Color {
    val t = ratio.coerceIn(0f, 1f)
    return Color(
        red = red + (other.red - red) * t,
        green = green + (other.green - green) * t,
        blue = blue + (other.blue - blue) * t,
        alpha = 1f
    )
}