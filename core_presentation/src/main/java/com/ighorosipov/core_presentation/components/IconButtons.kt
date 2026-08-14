package com.ighorosipov.core_presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ighorosipov.core_presentation.theme.LocalCustomColorsPalette
import com.ighorosipov.utils.R

@Composable
fun AddIconButton(
    modifier: Modifier = Modifier,
    onIconClick: () -> Unit,
    iconSize: Int = 24,
    iconColor: Color = LocalCustomColorsPalette.current.onPrimaryContainer,
) {
    Icon(
        modifier = modifier
            .size(iconSize.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(bounded = false, radius = iconSize.dp)
            ) { onIconClick() },
        painter = painterResource(id = R.drawable.ic_outline_add),
        contentDescription = "add",
        tint = iconColor,
    )
}

@Composable
fun RetryIconButton(
    modifier: Modifier = Modifier,
    onIconClick: () -> Unit,
    iconSize: Int = 24,
    iconColor: Color = LocalCustomColorsPalette.current.onPrimaryContainer,
) {
    Icon(
        modifier = modifier
            .size(iconSize.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(bounded = false, radius = iconSize.dp)
            ) { onIconClick() },
        painter = painterResource(id = R.drawable.outline_repeat),
        contentDescription = "retry",
        tint = iconColor,
    )
}

@Composable
fun NotificationIconButton(
    modifier: Modifier = Modifier,
    onIconClick: () -> Unit,
    isMuted: Boolean,
    iconSize: Int = 24,
    iconColor: Color = LocalCustomColorsPalette.current.onPrimaryContainer,
) {
    Icon(
        modifier = modifier
            .size(iconSize.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(bounded = false, radius = iconSize.dp)
            ) { onIconClick() },
        painter = if (isMuted) {
            painterResource(id = R.drawable.outline_notifications_off)
        } else painterResource(id = R.drawable.outline_notifications_active),
        contentDescription = "notification",
        tint = iconColor,
    )
}