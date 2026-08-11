package com.ighorosipov.timer.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.ighorosipov.core_presentation.theme.LocalCustomColorsPalette

@Composable
fun getTimerColors(): List<Color> {
    return listOf(
        LocalCustomColorsPalette.current.red,
        LocalCustomColorsPalette.current.blue,
        LocalCustomColorsPalette.current.blue,
        LocalCustomColorsPalette.current.greenYellow,
        LocalCustomColorsPalette.current.teal,
        LocalCustomColorsPalette.current.orange,
        LocalCustomColorsPalette.current.yellow,
        LocalCustomColorsPalette.current.purple,
        LocalCustomColorsPalette.current.pink,
        LocalCustomColorsPalette.current.grey
    )
}
