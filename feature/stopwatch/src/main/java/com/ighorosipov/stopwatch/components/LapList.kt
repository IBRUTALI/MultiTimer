package com.ighorosipov.stopwatch.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ighorosipov.core_presentation.theme.MultiTimerTheme
import com.ighorosipov.stopwatch.screen.LapUiModel
import com.ighorosipov.utils.R
import com.ighorosipov.utils.TimeFormat
import com.ighorosipov.utils.formatTime
import java.util.Locale

@Composable
fun LapsList(
    laps: List<LapUiModel>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        AnimatedVisibility(
            visible = laps.isNotEmpty(),
            enter = fadeIn(animationSpec = tween(300)) + expandVertically(animationSpec = tween(300)),
            exit = fadeOut(animationSpec = tween(300)) + shrinkVertically(animationSpec = tween(300))
        ) {
            Column {
                LapHeader()
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 4.dp),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.outlineVariant
                )
            }
        }

        LazyColumn {
            items(laps.reversed(), key = { it.index }) { lap ->
                LapItem(lap = lap)
            }
        }
    }
}

@Composable
private fun LapHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.stopwatch_lap),
            modifier = Modifier.weight(0.5f),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(R.string.stopwatch_lap_time),
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(R.string.stopwatch_total_time),
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun LapItem(lap: LapUiModel) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(durationMillis = 300),
        label = "lap_alpha"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(alpha)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = String.format(Locale.ENGLISH, "%02d", lap.index),
            modifier = Modifier.weight(0.5f),
            style = MaterialTheme.typography.bodyLarge.copy(
                fontFamily = FontFamily.Monospace
            ),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Text(
            text = formatTime(
                timeMs = lap.splitTimeMs,
                format = TimeFormat.MM_SS_MS
            ),
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyLarge.copy(
                fontFamily = FontFamily.Monospace
            ),
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )

        Text(
            text = formatTime(
                timeMs = lap.totalTimeMs,
                format = TimeFormat.MM_SS_MS
            ),
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyLarge.copy(
                fontFamily = FontFamily.Monospace
            ),
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
@Preview
fun LapsListEmptyPreviewLight() {
    MultiTimerTheme(darkTheme = false) {
        LapsList(
            laps = emptyList(),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
@Preview(showBackground = true)
fun LapsListPreviewLight() {
    MultiTimerTheme(darkTheme = false) {
        LapsList(
            laps = listOf(
                LapUiModel(index = 1, splitTimeMs = 45000L, totalTimeMs = 45000L),
                LapUiModel(index = 2, splitTimeMs = 50230L, totalTimeMs = 95230L),
                LapUiModel(index = 3, splitTimeMs = 38950L, totalTimeMs = 134180L),
                LapUiModel(index = 4, splitTimeMs = 41000L, totalTimeMs = 175180L),
                LapUiModel(index = 5, splitTimeMs = 39500L, totalTimeMs = 214680L)
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
@Preview
fun LapsListPreviewDark() {
    MultiTimerTheme(darkTheme = true) {
        LapsList(
            laps = listOf(
                LapUiModel(index = 1, splitTimeMs = 15000L, totalTimeMs = 15000L),
                LapUiModel(index = 2, splitTimeMs = 25050L, totalTimeMs = 40050L),
                LapUiModel(index = 3, splitTimeMs = 18950L, totalTimeMs = 59000L),
                LapUiModel(index = 4, splitTimeMs = 21000L, totalTimeMs = 80000L)
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}