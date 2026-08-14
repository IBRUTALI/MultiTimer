package com.ighorosipov.timer.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ighorosipov.core_presentation.components.NotificationIconButton
import com.ighorosipov.core_presentation.components.RetryIconButton
import com.ighorosipov.core_presentation.theme.LocalCustomColorsPalette
import com.ighorosipov.core_presentation.theme.MultiTimerTheme
import com.ighorosipov.utils.R
import com.ighorosipov.utils.TimeFormat
import com.ighorosipov.utils.formatTime

@Composable
fun TimerCard(
    timerName: String,
    time: Long,
    modifier: Modifier = Modifier,
    isPlaying: Boolean,
    onPlayPauseClick: () -> Unit,
    onStopClick: () -> Unit,
    onDeleteClick: () -> Boolean,
    onEditClick: () -> Unit
) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value == SwipeToDismissBoxValue.EndToStart) {
                onDeleteClick()
            } else {
                false
            }
        }
    )
    SwipeToDismissBox(
        modifier = modifier,
        state = dismissState,
        enableDismissFromStartToEnd = true,
        enableDismissFromEndToStart = false,
        backgroundContent = {
            val density = LocalDensity.current
            var offsetPx by remember { mutableFloatStateOf(0f) }

            LaunchedEffect(dismissState) {
                snapshotFlow {
                    runCatching { dismissState.requireOffset() }.getOrDefault(0f)
                }.collect { offsetPx = it }
            }

            val revealedWidth = with(density) {
                (offsetPx.toDp() - 5.dp).coerceAtLeast(0.dp)
            }
            Box(
                Modifier
                    .fillMaxHeight()
                    .width(revealedWidth)
                    .background(
                        color = LocalCustomColorsPalette.current.errorContainer,
                        shape = MaterialTheme.shapes.medium
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = LocalCustomColorsPalette.current.onErrorContainer
                )
            }
        }
    ) {
        ItemTimerContent(
            timerName = timerName,
            time = time,
            isPlaying = isPlaying,
            onPlayPauseClick = onPlayPauseClick,
            onStopClick = onStopClick,
            onEditClick = onEditClick
        )
    }
}

@Composable
fun ItemTimerContent(
    timerName: String,
    time: Long,
    isPlaying: Boolean,
    onPlayPauseClick: () -> Unit,
    onStopClick: () -> Unit,
    onEditClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = LocalCustomColorsPalette.current.primaryContainer,
        ),
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = timerName,
                    color = LocalCustomColorsPalette.current.onSurface,
                    style = MaterialTheme.typography.titleLarge
                )
                IconButton(onClick = onEditClick) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "edit",
                        tint = LocalCustomColorsPalette.current.onPrimaryContainer
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = formatTime(
                        timeMs = time,
                        format = TimeFormat.HH_MM_SS
                    ),
                    color = LocalCustomColorsPalette.current.onSurface,
                    style = MaterialTheme.typography.displayMedium,
                    modifier = Modifier.weight(1f)
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onPlayPauseClick) {
                        Icon(
                            painter = painterResource(
                                id = if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play
                            ),
                            contentDescription = if (isPlaying) "pause" else "play",
                            tint = LocalCustomColorsPalette.current.onPrimaryContainer
                        )
                    }
                }

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                RetryIconButton(
                    onIconClick = {
                        //todo
                    }
                )
                Spacer(modifier = Modifier.width(10.dp))
                NotificationIconButton(
                    isMuted = false, //todo
                    onIconClick = {
                        //todo
                    }
                )
            }
        }
    }
}

@Composable
@Preview
fun TimerCardPreviewLight() {
    MultiTimerTheme(darkTheme = false) {
        TimerCard(
            timerName = "Test",
            time = 100000L,
            isPlaying = true,
            onPlayPauseClick = {},
            onStopClick = {},
            onDeleteClick = { false },
            onEditClick = {}
        )
    }
}

@Composable
@Preview
fun TimerCardPreviewDark() {
    MultiTimerTheme(darkTheme = true) {
        TimerCard(
            timerName = "",
            time = 100000L,
            isPlaying = false,
            onPlayPauseClick = {},
            onStopClick = {},
            onDeleteClick = { false },
            onEditClick = {}
        )
    }
}