package com.ighorosipov.core_presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ighorosipov.core_presentation.theme.LocalCustomColorsPalette
import com.ighorosipov.core_presentation.theme.MultiTimerTheme
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun ClockLoaderScreen(
    modifier: Modifier = Modifier,
    isLoading: Boolean
) {
    AnimatedVisibility(
        visible = isLoading,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        ClockLoader(modifier = modifier)
    }
}

@Composable
fun ClockLoader(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ClockLoaderIcon(
            size = 100.dp
        )
    }
}

@Composable
fun ClockLoaderIcon(
    modifier: Modifier = Modifier,
    size: Dp = 100.dp,
    clockColor: Color = LocalCustomColorsPalette.current.primary,
    trackColor: Color = LocalCustomColorsPalette.current.surface,
    handColor: Color = LocalCustomColorsPalette.current.surfaceVariant
) {
    val transition = rememberInfiniteTransition(label = "clock")

    val angle by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 3000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "clock_angle"
    )

    Canvas(
        modifier = modifier.size(size)
    ) {
        val center = Offset(
            x = this.size.width / 2f,
            y = this.size.height / 2f
        )

        val radius = this.size.minDimension * 0.42f
        val strokeWidth = this.size.minDimension * 0.045f

        // Внешнее кольцо
        drawCircle(
            color = trackColor,
            radius = radius,
            center = center,
            style = Stroke(width = strokeWidth)
        )

        // Акцентная дуга
        drawArc(
            color = clockColor,
            startAngle = -90f,
            sweepAngle = 100f,
            useCenter = false,
            topLeft = Offset(
                center.x - radius,
                center.y - radius
            ),
            size = Size(radius * 2, radius * 2),
            style = Stroke(
                width = strokeWidth,
                cap = StrokeCap.Round
            )
        )

        // Часовые деления
        repeat(12) { index ->
            val tickAngle = Math.toRadians(
                index * 30.0 - 90.0
            )

            val outerRadius = radius * 0.83f
            val innerRadius = radius * 0.72f

            val start = Offset(
                center.x + cos(tickAngle).toFloat() * innerRadius,
                center.y + sin(tickAngle).toFloat() * innerRadius
            )

            val end = Offset(
                center.x + cos(tickAngle).toFloat() * outerRadius,
                center.y + sin(tickAngle).toFloat() * outerRadius
            )

            drawLine(
                color = clockColor.copy(alpha = 0.75f),
                start = start,
                end = end,
                strokeWidth = strokeWidth * 0.55f,
                cap = StrokeCap.Round
            )
        }

        // Длина стрелок
        val hourHandLength = radius * 0.48f
        val minuteHandLength = radius * 0.72f

        // Часовая стрелка движется медленнее
        drawClockHand(
            center = center,
            angle = angle * 0.5f - 90f,
            length = hourHandLength,
            strokeWidth = strokeWidth * 1.25f,
            color = handColor
        )

        // Минутная стрелка делает полный оборот за 3 секунды
        drawClockHand(
            center = center,
            angle = angle - 90f,
            length = minuteHandLength,
            strokeWidth = strokeWidth * 0.7f,
            color = clockColor
        )

        // Центральная ось
        drawCircle(
            color = handColor,
            radius = strokeWidth * 1.2f,
            center = center
        )
    }
}

private fun DrawScope.drawClockHand(
    center: Offset,
    angle: Float,
    length: Float,
    strokeWidth: Float,
    color: Color
) {
    val radians = Math.toRadians(angle.toDouble())

    val end = Offset(
        x = center.x + cos(radians).toFloat() * length,
        y = center.y + sin(radians).toFloat() * length
    )

    drawLine(
        color = color,
        start = center,
        end = end,
        strokeWidth = strokeWidth,
        cap = StrokeCap.Round
    )
}

@Preview
@Composable
fun ClockLoaderPreviewLight() {
    MultiTimerTheme(darkTheme = false) {
        ClockLoader()
    }
}

@Preview
@Composable
fun ClockLoaderPreviewDark() {
    MultiTimerTheme(darkTheme = true) {
        ClockLoader()
    }
}