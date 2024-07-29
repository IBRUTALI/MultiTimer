package com.ighorosipov.multitimer.feature.timer.presentation.components

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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ighorosipov.multitimer.ui.theme.Typography

@Composable
fun TimerWidget(
    modifier: Modifier = Modifier,
    limitItems: Int,
    hoursText: @Composable (() -> Unit)? = null,
    minutesText: @Composable (() -> Unit)? = null,
    secondsText: @Composable (() -> Unit)? = null,
) {
    Column {
        Box(contentAlignment = Alignment.Center) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TimerList(
                    numbers = (0..99).toList(),
                    limitItems = limitItems
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                        .background(MaterialTheme.colorScheme.tertiary)
                )
                TimerList(
                    numbers = (0..59).toList(),
                    limitItems = limitItems
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                        .background(MaterialTheme.colorScheme.tertiary)
                )
                TimerList(
                    numbers = (0..59).toList(),
                    limitItems = limitItems
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .padding(horizontal = 5.dp)
                    .align(Alignment.Center)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.tertiary.copy(alpha = 0.2f))
            )
        }
        Row(
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            if (hoursText != null) {
                hoursText()
            }
            if (minutesText != null) {
                minutesText()
            }
            if (secondsText != null) {
                secondsText()
            }
        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TimerList(
    modifier: Modifier = Modifier,
    numbers: List<Int>,
    limitItems: Int,
) {
    val listState =
        rememberLazyListState(Int.MAX_VALUE / 2 - (Int.MAX_VALUE / 2) % numbers.size - 1)
    val snapFlingBehavior = rememberSnapFlingBehavior(lazyListState = listState)
    var itemHeightPixels by remember { mutableIntStateOf(0) }
    val itemCount = numbers.size
    LazyColumn(
        modifier = modifier
            .height(pixelsToDp(pixels = itemHeightPixels * limitItems))
            .width(90.dp),
        state = listState,
        flingBehavior = snapFlingBehavior
    ) {
        items(Int.MAX_VALUE) { item ->
            val actualIndex = item % itemCount
            TimerItem(
                title = numbers[actualIndex].toString(),
                state = listState,
                index = item,
                rotation = 50f,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .onSizeChanged { size -> itemHeightPixels = size.height }
                    .wrapContentHeight(align = Alignment.CenterVertically)
            )
        }

    }
}

@Composable
fun TimerItem(
    title: String,
    state: LazyListState,
    index: Int,
    rotation: Float,
    modifier: Modifier = Modifier,
) {
    val focusTextColor = MaterialTheme.colorScheme.onBackground
    val noFocusTextColor = MaterialTheme.colorScheme.tertiary
    val itemState by remember {
        derivedStateOf {
            calculateItemChanges(
                state = state,
                index = index,
                rotation = rotation,
                focusColor = focusTextColor,
                noFocusColor = noFocusTextColor
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
        style = Typography.titleLarge
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
        } else {
            defaultRotation = rotation
        }
    }
    return TimerItemState(
        textColor = textColor,
        rotation = defaultRotation
    )
}
