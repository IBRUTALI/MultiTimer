package com.ighorosipov.multitimer.ui.components.edit_field

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ighorosipov.multitimer.ui.theme.AppTextInputColors
import com.ighorosipov.multitimer.ui.theme.Typography

@Composable
fun BaseEditField(
    modifier: Modifier = Modifier,
    inputValue: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OutlinedTextField(
            value = inputValue ,
            onValueChange = { onValueChange(it) },
            modifier = Modifier
                .padding(all = 16.dp)
                .fillMaxWidth(),
            placeholder = {
                Text(
                    text = placeholder,
                    style = Typography.bodyMedium
                )
            },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = true,
                keyboardType = KeyboardType.Text
            ),
            textStyle = Typography.bodyMedium,
            singleLine = true,
            maxLines = 2,
            colors = AppTextInputColors
        )
    }

}