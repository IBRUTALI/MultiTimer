package com.ighorosipov.core_presentation.components

data class ToolbarIcon(
    val icon: Int,
    val iconSize: Int = 24,
    val contentDescription: String? = null,
    val onClick: (() -> Unit)? = null
)
