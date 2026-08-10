package com.ighorosipov.core_presentation.components

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import com.ighorosipov.core_presentation.theme.LocalCustomColorsPalette

@Composable
fun BaseActionButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier
            .scale(1.2f),
        colors = IconButtonDefaults.iconButtonColors(containerColor = LocalCustomColorsPalette.current.red),
        onClick = {
            onClick()
        }
    ) {
        Icon(
            imageVector = imageVector,
            tint = LocalCustomColorsPalette.current.white,
            contentDescription = null
        )
    }
}