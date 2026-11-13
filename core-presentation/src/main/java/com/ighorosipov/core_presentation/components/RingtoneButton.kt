package com.ighorosipov.core_presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ighorosipov.core_presentation.theme.LocalCustomColorsPalette
import com.ighorosipov.core_presentation.theme.MultiTimerTheme
import com.ighorosipov.utils.R

@Composable
fun RingtoneButton(
    modifier: Modifier = Modifier,
    title: String?,
    isMuted: Boolean,
    onSwitchClick: () -> Unit,
    onClick: () -> Unit
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {

            NotificationIconButton(
                onIconClick = {},
                enabled = false,
                isMuted = isMuted,
                iconColor = LocalCustomColorsPalette.current.primary
            )

            Spacer(modifier = Modifier.width(10.dp))

            when {
                isMuted -> {
                    Text(
                        text = stringResource(R.string.off),
                        style = MaterialTheme.typography.headlineLarge,
                        color = LocalCustomColorsPalette.current.primary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                title != null -> {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineLarge,
                        color = LocalCustomColorsPalette.current.primary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                else -> {
                    Text(
                        text = stringResource(R.string.unknown),
                        style = MaterialTheme.typography.headlineLarge,
                        color = LocalCustomColorsPalette.current.primary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

            }

        }

        Spacer(modifier = Modifier.width(10.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            VerticalDivider(
                modifier = Modifier.height(25.dp),
                color = LocalCustomColorsPalette.current.primary
            )

            Spacer(modifier = Modifier.width(10.dp))

            BaseSwitch(
                checked = !isMuted,
                onCheckedChange = {
                    onSwitchClick()
                }
            )
        }

    }

}

@Preview
@Composable
fun RingtoneButtonPreviewLight() {
    MultiTimerTheme(darkTheme = false) {
        RingtoneButton(
            title = "Ringtone",
            isMuted = false,
            onSwitchClick = {},
            onClick = {}
        )
    }
}

@Preview
@Composable
fun RingtoneButtonPreviewDark() {
    MultiTimerTheme(darkTheme = true) {
        RingtoneButton(
            title = "Ringtone",
            isMuted = true,
            onSwitchClick = {},
            onClick = {}
        )
    }
}