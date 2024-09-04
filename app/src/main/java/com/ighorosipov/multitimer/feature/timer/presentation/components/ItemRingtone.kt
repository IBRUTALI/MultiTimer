package com.ighorosipov.multitimer.feature.timer.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ighorosipov.multitimer.R
import com.ighorosipov.multitimer.ui.components.BaseCheckBox
import com.ighorosipov.multitimer.ui.theme.Typography

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ItemRingtone(
    modifier: Modifier = Modifier,
    title: String,
    checkedState: Boolean,
    borderColor: Color,
    isPlaying: Boolean,
    onItemClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .clickable {
                onItemClick()
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .border(border = BorderStroke(2.dp, borderColor), shape = CircleShape)
                .padding(4.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            Icon(
                painter = if (isPlaying) {
                    painterResource(id = R.drawable.ic_pause)
                } else {
                    painterResource(id = R.drawable.ic_play)
                },
                modifier = Modifier.align(Alignment.Center),
                contentDescription = "play"
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = title,
            modifier = modifier
                .width(70.dp)
                .basicMarquee(delayMillis = 2000),
            textAlign = TextAlign.Center,
            maxLines = 1,
            style = Typography.bodyMedium
        )
        BaseCheckBox(
            checkedState = checkedState,
            onStateChange = { onItemClick() }
        )
    }
}