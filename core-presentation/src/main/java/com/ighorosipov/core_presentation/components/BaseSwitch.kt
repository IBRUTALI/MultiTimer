package com.ighorosipov.core_presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.ighorosipov.core_presentation.misc.conditional
import com.ighorosipov.core_presentation.theme.LocalCustomColorsPalette
import com.ighorosipov.core_presentation.theme.MultiTimerTheme

@Composable
fun BaseSwitch(
    modifier: Modifier = Modifier,
    checked: Boolean,
    enabled: Boolean = true,
    onCheckedChange: (Boolean) -> Unit,
) {
    val switchWidth = 34.dp
    val switchHeight = 14.dp
    val thumbSize = 20.dp

    val offsetX by animateDpAsState(
        targetValue = if (checked) switchWidth - thumbSize + 1.dp else (-1).dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "offset"
    )

    val backgroundColor by animateColorAsState(
        targetValue = if (checked) LocalCustomColorsPalette.current.onSecondary else LocalCustomColorsPalette.current.secondary,
        animationSpec = tween(durationMillis = 300),
        label = "background"
    )

    val thumbColor by animateColorAsState(
        targetValue = if (checked) LocalCustomColorsPalette.current.secondary else LocalCustomColorsPalette.current.onSecondary,
        animationSpec = tween(durationMillis = 300),
        label = "thumb"
    )

    Box(
        modifier = modifier
            .padding(2.dp)
            .size(switchWidth, thumbSize)
            .clickable(
                enabled = enabled,
                onClick = { onCheckedChange(!checked) },
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            )
            .conditional(!enabled) {
                graphicsLayer { alpha = 0.6f }
            },
        contentAlignment = Alignment.CenterStart
    ) {

        Box(
            modifier = Modifier
                .size(switchWidth, switchHeight)
                .clip(RoundedCornerShape(switchHeight / 2))
                .background(backgroundColor)
        )

        Box(
            modifier = Modifier
                .offset { IntOffset(offsetX.roundToPx(), 0) }
                .size(thumbSize)
                .background(
                    color = thumbColor,
                    shape = CircleShape
                )
        )
    }
}

@Preview
@Composable
fun BaseSwitchPreviewOnLight() {
    MultiTimerTheme(darkTheme = false) {
        BaseSwitch(
            checked = true,
            onCheckedChange = {}
        )
    }
}

@Preview
@Composable
fun BaseSwitchPreviewOnDark() {
    MultiTimerTheme(darkTheme = true) {
        BaseSwitch(
            checked = true,
            onCheckedChange = {}
        )
    }
}

@Preview
@Composable
fun BaseSwitchPreviewOffLight() {
    MultiTimerTheme(darkTheme = false) {
        BaseSwitch(
            checked = false,
            onCheckedChange = {}
        )
    }
}

@Preview
@Composable
fun BaseSwitchPreviewOffDark() {
    MultiTimerTheme(darkTheme = true) {
        BaseSwitch(
            checked = false,
            onCheckedChange = {}
        )
    }
}