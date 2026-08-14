package com.ighorosipov.core_presentation.components

data class IconParams(
    val icon: Int,
    val iconSize: Int = 24,
    val iconPosition: IconPosition = IconPosition.START
)

enum class IconPosition {
    START,
    END
}
