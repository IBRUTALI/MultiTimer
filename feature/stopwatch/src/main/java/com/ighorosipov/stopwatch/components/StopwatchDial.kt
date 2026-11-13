package com.ighorosipov.stopwatch.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ighorosipov.core_presentation.theme.LocalCustomColorsPalette
import com.ighorosipov.core_presentation.theme.MultiTimerTheme
import com.ighorosipov.utils.formatTime

@Composable
fun StopwatchDial(
    timeMs: Long,
    progress: Float,
    isRunning: Boolean,
    modifier: Modifier = Modifier
) {
    val trackColor = Color(0xFFE0E0E0)
    val progressColor =
        if (isRunning) LocalCustomColorsPalette.current.primary else Color(0xFF888888)

    Box(contentAlignment = Alignment.Center, modifier = modifier) {
        Canvas(modifier = Modifier.fillMaxSize()) {

            drawCircle(
                color = trackColor,
                style = Stroke(width = 10.dp.toPx())
            )

            drawArc(
                color = progressColor,
                startAngle = -90f,
                sweepAngle = 360f * progress,
                useCenter = false,
                style = Stroke(width = 10.dp.toPx())
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = formatTime(timeMs),
                fontSize = 56.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            if (timeMs >= 3_600_000L) {
                Text(
                    text = "elapsed",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun StopwatchDialPreviewLight() {
    MultiTimerTheme(darkTheme = false) {
        StopwatchDial(
            timeMs = 125500L,
            progress = 0.25f,
            isRunning = true,
            modifier = Modifier.size(280.dp)
        )
    }
}

@Composable
@Preview
fun StopwatchDialPreviewDark() {
    MultiTimerTheme(darkTheme = true) {
        StopwatchDial(
            timeMs = 35000L,
            progress = 0.58f,
            isRunning = false,
            modifier = Modifier.size(280.dp)
        )
    }
}