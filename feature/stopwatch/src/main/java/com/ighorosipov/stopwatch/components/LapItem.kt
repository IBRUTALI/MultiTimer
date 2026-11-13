package com.ighorosipov.stopwatch.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ighorosipov.core_presentation.theme.LocalCustomColorsPalette
import com.ighorosipov.core_presentation.theme.MultiTimerTheme
import com.ighorosipov.stopwatch.screen.LapUiModel
import com.ighorosipov.utils.formatTime

@Composable
fun LapItem(lap: LapUiModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Lap ${lap.index}",
            style = MaterialTheme.typography.bodyLarge,
            color = LocalCustomColorsPalette.current.onSurfaceVariant
        )
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = formatTime(lap.splitTimeMs),
                style = MaterialTheme.typography.bodyLarge,
                color = LocalCustomColorsPalette.current.onSurface
            )
            Text(
                text = formatTime(lap.totalTimeMs),
                style = MaterialTheme.typography.bodyLarge,
                color = LocalCustomColorsPalette.current.primary
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun LapItemPreviewLight() {
    MultiTimerTheme(darkTheme = false) {
        LapItem(
            lap = LapUiModel(
                index = 1,
                splitTimeMs = 45000L,
                totalTimeMs = 45000L
            )
        )
    }
}

@Composable
@Preview
fun LapItemPreviewDark() {
    MultiTimerTheme(darkTheme = true) {
        LapItem(
            lap = LapUiModel(
                index = 5,
                splitTimeMs = 12500L,
                totalTimeMs = 135500L
            )
        )
    }
}