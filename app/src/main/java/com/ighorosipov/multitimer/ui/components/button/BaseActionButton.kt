package com.ighorosipov.multitimer.ui.components.button

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun BaseActionButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier
            .scale(1.2f),
        colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.secondary),
        onClick = {
            onClick()
        }
    ) {
        Icon(
            imageVector = imageVector,
            tint = MaterialTheme.colorScheme.onPrimary,
            contentDescription = null
        )
    }
}