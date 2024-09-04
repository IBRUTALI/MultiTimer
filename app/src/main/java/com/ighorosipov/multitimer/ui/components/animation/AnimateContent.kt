package com.ighorosipov.multitimer.ui.components.animation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AnimateContent(
    modifier: Modifier = Modifier,
    show: Boolean,
    content: @Composable (() -> Unit)? = null
) {
    Column(
        modifier = modifier.animateContentSize()
    ) {
        AnimatedVisibility(
            visible = show,
            enter = slideInHorizontally(
                initialOffsetX = { -1000 },
                animationSpec = tween(300)
            ),
            exit = slideOutHorizontally(
                targetOffsetX = { -1000 },
                animationSpec = tween(300)
            )
        ) {
            if (content != null) {
                content()
            }
        }
    }

}