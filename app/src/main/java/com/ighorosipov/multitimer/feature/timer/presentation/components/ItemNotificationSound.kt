package com.ighorosipov.multitimer.feature.timer.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ighorosipov.multitimer.R
import com.ighorosipov.multitimer.ui.theme.Typography

@Composable
fun ItemNotificationSound(
    modifier: Modifier = Modifier,
    title: String,
    checkedState: Boolean,
    borderColor: Color,
    onItemClick: () -> Unit
) {
    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .border(border = BorderStroke(2.dp, borderColor), shape = CircleShape)
                .padding(4.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.tertiary)
                .clickable {
                    onItemClick()
                }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_play),
                contentDescription = "play"
            )
        }
        Text(
            text = title,
            style = Typography.labelSmall
        )
        Checkbox(
            checked = checkedState,
            onCheckedChange = null
        )
    }
}