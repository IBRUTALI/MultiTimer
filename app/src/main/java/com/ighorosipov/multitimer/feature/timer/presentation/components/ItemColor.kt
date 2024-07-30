package com.ighorosipov.multitimer.feature.timer.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ItemColor(
    modifier: Modifier = Modifier,
    color: Color,
    borderColor: Color,
    onItemClick: () -> Unit
) {
    Box(
        modifier = modifier
            .border(border = BorderStroke(2.dp, borderColor), shape = CircleShape)
            .padding(4.dp)
            .clip(CircleShape)
            .background(color)
            .clickable {
                onItemClick()
            }
    )
}