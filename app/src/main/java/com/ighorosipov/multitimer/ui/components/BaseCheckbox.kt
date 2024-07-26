package com.ighorosipov.multitimer.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

@Composable
fun BaseCheckBox(
    modifier: Modifier = Modifier,
    title: @Composable (() -> Unit)? = null,
    checkedState: Boolean,
    onStateChange: (Boolean) -> Unit
) {
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
            Checkbox(
                modifier = Modifier.padding(end = 4.dp),
                checked = checkedState,
                onCheckedChange = null
            )
            if (title != null) {
                title()
            }
        }
    }
}