package com.ighorosipov.stopwatch.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ighorosipov.core_presentation.theme.MultiTimerTheme

@Composable
fun CircleButton(
    text: String,
    enabled: Boolean,
    containerColor: Color,
    contentColor: Color,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = containerColor.copy(alpha = 0.38f),
            disabledContentColor = contentColor.copy(alpha = 0.38f)
        ),
        modifier = Modifier.size(96.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
@Preview
fun CircleButtonPreviewLight() {
    MultiTimerTheme(darkTheme = false) {
        CircleButton(
            text = "Start",
            enabled = true,
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            onClick = {}
        )
    }
}

@Composable
@Preview
fun CircleButtonPreviewDark() {
    MultiTimerTheme(darkTheme = true) {
        CircleButton(
            text = "Stop",
            enabled = true,
            containerColor = MaterialTheme.colorScheme.error,
            contentColor = MaterialTheme.colorScheme.onError,
            onClick = {}
        )
    }
}