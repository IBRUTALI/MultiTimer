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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ighorosipov.core_presentation.theme.LocalCustomColorsPalette
import com.ighorosipov.core_presentation.theme.MultiTimerTheme
import com.ighorosipov.utils.R

@Composable
fun BaseToolbar(
    modifier: Modifier = Modifier,
    iconParams: IconParams? = null,
    title: String? = null,
    onIconClick: (() -> Unit)? = null,
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
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (iconParams != null) {
                when(iconParams.iconPosition) {
                    IconPosition.START -> {
                        Icon(
                            painter = painterResource(id = iconParams.icon),
                            contentDescription = null,
                            tint = LocalCustomColorsPalette.current.onSurface,
                            modifier = Modifier
                                .size(iconParams.iconSize.dp)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = ripple(bounded = false, radius = iconParams.iconSize.dp)
                                ) { onIconClick?.invoke() },
                        )
                        title?.let {
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = title,
                                style = MaterialTheme.typography.headlineLarge,
                                color = LocalCustomColorsPalette.current.onSurface,
                            )
                        }
                    }
                    IconPosition.END -> {
                        title?.let {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.headlineLarge,
                                color = LocalCustomColorsPalette.current.onSurface,
                            )
                            Spacer(modifier = Modifier.weight(1f))
                        }
                        Icon(
                            painter = painterResource(id = iconParams.icon),
                            contentDescription = null,
                            tint = LocalCustomColorsPalette.current.onSurface,
                            modifier = Modifier
                                .size(iconParams.iconSize.dp)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = ripple(bounded = false, radius = iconParams.iconSize.dp)
                                ) { onIconClick?.invoke() },
                        )
                    }
                }
            } else {
                title?.let {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineLarge,
                        color = LocalCustomColorsPalette.current.onSurface,
                    )
                }
            }
        }

    }
}

@Composable
@Preview
fun BaseToolbarPreviewLight() {
    MultiTimerTheme(darkTheme = false) {
        BaseToolbar(
            iconParams = IconParams(
                icon = R.drawable.ic_cancel,
                iconSize = 24
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
            iconParams = IconParams(
                icon = R.drawable.ic_cancel,
                iconSize = 24,
                iconPosition = IconPosition.END
            ),
            title = "Title"
        )
    }
}