package com.ighorosipov.core_presentation.misc

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
inline fun Modifier.conditional(condition: Boolean, crossinline block: @Composable Modifier.() -> Modifier): Modifier {
    return if (condition) {
        then(block(Modifier))
    } else {
        this
    }
}