package com.ighorosipov.timer.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ighorosipov.core_presentation.theme.LocalCustomColorsPalette
import com.ighorosipov.core_presentation.theme.MultiTimerTheme
import com.ighorosipov.utils.R
import kotlin.math.abs

@Composable
fun TimerWidget(
    modifier: Modifier = Modifier,
    initialTime: Long = 0L,
    limitItems: Int = 10,
    onTimeChange: (Long) -> Unit,
) {
    var initialHours by remember {
        mutableIntStateOf(
            (initialTime / 3_600_000).toInt()
        )
    }
    var initialMinutes by remember {
        mutableIntStateOf(
            ((initialTime / 60_000) % 60).toInt()
        )
    }
    var initialSeconds by remember {
        mutableIntStateOf(
            ((initialTime / 1000) % 60).toInt()
        )
    }

    var hours by remember { mutableIntStateOf(0) }
    var minutes by remember { mutableIntStateOf(0) }
    var seconds by remember { mutableIntStateOf(0) }

    LaunchedEffect(hours, minutes, seconds) {
        val currentTimeMs = hours * 3_600_000L + minutes * 60_000L + seconds * 1000L
        onTimeChange(currentTimeMs)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Box(contentAlignment = Alignment.Center) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TimerList(
                    numbers = (0..99).toList(),
                    limitItems = limitItems,
                    initialValue = initialHours,
                    onValueChange = {
                        hours = it
                    }
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                        .background(LocalCustomColorsPalette.current.onSurface)
                )
                TimerList(
                    numbers = (0..59).toList(),
                    limitItems = limitItems,
                    initialValue = initialMinutes,
                    onValueChange = {
                        minutes = it
                    }
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                        .background(LocalCustomColorsPalette.current.onSurface)
                )
                TimerList(
                    numbers = (0..59).toList(),
                    limitItems = limitItems,
                    initialValue = initialSeconds,
                    onValueChange = {
                        seconds = it
                    }
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .padding(horizontal = 5.dp)
                    .align(Alignment.Center)
                    .clip(CircleShape)
                    .background(LocalCustomColorsPalette.current.primary.copy(alpha = 0.5f))
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = stringResource(R.string.h),
                modifier = Modifier
                    .padding(top = 10.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = stringResource(R.string.m),
                modifier = Modifier
                    .padding(top = 10.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = stringResource(R.string.s),
                modifier = Modifier
                    .padding(top = 10.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TimerList(
    modifier: Modifier = Modifier,
    numbers: List<Int>,
    limitItems: Int,
    initialValue: Int,
    onValueChange: (Int) -> Unit,
) {
    val itemCount = numbers.size
    val repeatCount = 100
    val middle = (repeatCount / 2) * itemCount
    val startIndex = indexFor(
        value = initialValue,
        middle = middle,
        numbers = numbers
    )
    val listState = rememberLazyListState(startIndex)
    val snapFlingBehavior = rememberSnapFlingBehavior(listState)

    var itemHeightPx by remember { mutableIntStateOf(0) }
    LaunchedEffect(initialValue) {
        val target = indexFor(
            value = initialValue,
            middle = middle,
            numbers = numbers
        )
        if (listState.firstVisibleItemIndex != target) {
            listState.animateScrollToItem(target)
        }
    }

    LaunchedEffect(listState) {
        snapshotFlow {
            val layoutInfo = listState.layoutInfo
            val visible = layoutInfo.visibleItemsInfo
            if (visible.isEmpty()) return@snapshotFlow null

            val center = layoutInfo.viewportStartOffset +
                    (layoutInfo.viewportEndOffset - layoutInfo.viewportStartOffset) / 2

            visible.minByOrNull { item ->
                val itemCenter = item.offset + item.size / 2
                abs(itemCenter - center)
            }?.index
        }.collect { index ->
            if (index == null) return@collect
            if (listState.isScrollInProgress) return@collect

            val newValue = numbers[index % itemCount]
            if (newValue != initialValue) {
                onValueChange(newValue)
            }
        }
    }

    LazyColumn(
        modifier = modifier
            .height(pixelsToDp(pixels = itemHeightPx * limitItems))
            .width(90.dp),
        state = listState,
        flingBehavior = snapFlingBehavior
    ) {
        items(repeatCount * itemCount) { item ->
            val value = numbers[item % itemCount]
            TimerCard(
                title = value.toString().padStart(2, '0'),
                state = listState,
                index = item,
                rotation = 40f,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .onSizeChanged { size -> itemHeightPx = size.height }
                    .wrapContentHeight(align = Alignment.CenterVertically),
                getSelectedTimeIndex = {
                    onValueChange(numbers[it % itemCount])
                }
            )
        }
    }
}

@Composable
fun TimerCard(
    title: String,
    state: LazyListState,
    index: Int,
    rotation: Float,
    modifier: Modifier = Modifier,
    getSelectedTimeIndex: (Int) -> Unit,
) {
    val focusTextColor = LocalCustomColorsPalette.current.onSurface
    val noFocusTextColor = LocalCustomColorsPalette.current.onSecondaryContainer.copy(alpha = 0.5f)
    val itemState by remember {
        derivedStateOf {
            calculateItemChanges(
                state = state,
                index = index,
                rotation = rotation,
                focusColor = focusTextColor,
                noFocusColor = noFocusTextColor,
                getSelectedTimeIndex = {
                    getSelectedTimeIndex(it)
                }
            )
        }
    }

    Text(
        text = title,
        modifier = modifier
            .graphicsLayer {
                rotationX = itemState.rotation
            },
        textAlign = TextAlign.Center,
        color = itemState.textColor,
        style = MaterialTheme.typography.titleLarge
    )
}

@Composable
private fun pixelsToDp(pixels: Int) = with(LocalDensity.current) { pixels.toDp() }

private fun calculateItemChanges(
    state: LazyListState,
    index: Int,
    rotation: Float,
    focusColor: Color,
    noFocusColor: Color,
    getSelectedTimeIndex: (Int) -> Unit,
): TimerItemState {
    var textColor = noFocusColor
    var defaultRotation = 1f
    val layoutInfo = state.layoutInfo
    val visibleItemsInfo = layoutInfo.visibleItemsInfo
    val itemInfo = visibleItemsInfo.firstOrNull { it.index == index }

    itemInfo?.let {
        val delta = it.size / 2
        val center = state.layoutInfo.viewportEndOffset / 2
        val childCenter = it.offset + it.size / 2
        val target = childCenter - center
        if (target in -delta..delta) {
            textColor = focusColor
            defaultRotation = 1f
            getSelectedTimeIndex(index)
        } else {
            defaultRotation = rotation
        }
    }
    return TimerItemState(
        textColor = textColor,
        rotation = defaultRotation
    )
}

private fun indexFor(
    value: Int,
    middle: Int,
    numbers: List<Int>
): Int {
    val valueIndex = value.coerceIn(0, numbers.lastIndex)
    return (middle + valueIndex - 1).coerceAtLeast(0)
}

@Composable
@Preview
private fun TimerWidgetPreviewLight() {
    MultiTimerTheme(
        darkTheme = false
    ) {
        TimerWidget(
            modifier = Modifier.background(LocalCustomColorsPalette.current.surface),
            initialTime = 3666666,
            limitItems = 3,
            onTimeChange = {}
        )
    }
}

@Composable
@Preview
private fun TimerWidgetPreviewDark() {
    MultiTimerTheme(
        darkTheme = true
    ) {
        TimerWidget(
            modifier = Modifier.background(LocalCustomColorsPalette.current.surface),
            initialTime = 3666666,
            limitItems = 3,
            onTimeChange = {}
        )
    }
}
