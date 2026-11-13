package com.ighorosipov.core_presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ighorosipov.core_presentation.theme.LocalCustomColorsPalette
import com.ighorosipov.core_presentation.theme.MultiTimerTheme
import com.ighorosipov.utils.R

@Composable
fun BaseToolbar(
    modifier: Modifier = Modifier,
    title: String? = null,
    startIcons: List<ToolbarIcon> = emptyList(),
    endIcons: List<ToolbarIcon> = emptyList(),
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = LocalCustomColorsPalette.current.surface,
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            startIcons.forEachIndexed { index, item ->
                ToolbarIconView(item)
                if (index != startIcons.lastIndex) {
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }

            if (startIcons.isNotEmpty() && title != null) {
                Spacer(modifier = Modifier.width(16.dp))
            }

            if (title != null) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineLarge,
                    color = LocalCustomColorsPalette.current.onSurface,
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }

            if (endIcons.isNotEmpty()) {
                Spacer(modifier = Modifier.width(8.dp))
                endIcons.forEachIndexed { index, item ->
                    ToolbarIconView(item)
                    if (index != endIcons.lastIndex) {
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun ToolbarIconView(item: ToolbarIcon) {
    Icon(
        painter = painterResource(id = item.icon),
        contentDescription = item.contentDescription,
        tint = LocalCustomColorsPalette.current.onSurface,
        modifier = Modifier
            .size(item.iconSize.dp)
            .clickable(
                enabled = item.onClick != null,
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(bounded = false, radius = item.iconSize.dp)
            ) { item.onClick?.invoke() }
    )
}

@Composable
@Preview
fun BaseToolbarPreviewLight() {
    MultiTimerTheme(darkTheme = false) {
        BaseToolbar(
            startIcons = listOf(
                ToolbarIcon(
                    icon = R.drawable.ic_cancel,
                    iconSize = 24
                ),
                ToolbarIcon(
                    icon = R.drawable.ic_outline_add,
                    iconSize = 24
                )
            ),
            title = "Title"
        )
    }
}

@Composable
@Preview
fun BaseToolbarPreviewDark() {
    MultiTimerTheme(darkTheme = true) {
        BaseToolbar(
            endIcons = listOf(
                ToolbarIcon(
                    icon = R.drawable.ic_cancel,
                    iconSize = 24
                ),
                ToolbarIcon(
                    icon = R.drawable.ic_apply,
                    iconSize = 24
                )
            ),
            title = "Title"
        )
    }
}