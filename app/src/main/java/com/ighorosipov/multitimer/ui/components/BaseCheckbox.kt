package com.ighorosipov.multitimer.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role

@Composable
fun BaseCheckBox(
    modifier: Modifier = Modifier,
    title: @Composable (() -> Unit)? = null,
    checkedState: Boolean,
    onStateChange: (Boolean) -> Unit
) {

    val imageVector = if (checkedState) Icons.Filled.CheckCircle else Icons.Outlined.Circle
    val background = if (checkedState) MaterialTheme.colorScheme.surface else Color.Transparent

    Column(modifier = modifier) {
        Row(
            Modifier
                .fillMaxWidth()
                .toggleable(
                    value = checkedState,
                    onValueChange = { onStateChange(!checkedState) },
                    role = Role.Checkbox
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { onStateChange(!checkedState) },
            ) {
                Icon(
                    imageVector = imageVector,
                    modifier = Modifier.background(background, shape = CircleShape),
                    contentDescription = "checkbox"
                )
            }
            if (title != null) {
                title()
            }
        }
    }
}