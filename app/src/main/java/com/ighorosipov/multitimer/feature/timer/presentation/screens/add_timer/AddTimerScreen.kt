package com.ighorosipov.multitimer.feature.timer.presentation.screens.add_timer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ighorosipov.multitimer.R
import com.ighorosipov.multitimer.ui.components.edit_field.BaseEditField
import com.ighorosipov.multitimer.ui.theme.Typography

@Composable
fun AddTimerScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: AddTimerViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    Column(modifier = modifier.fillMaxSize()) {
        BaseEditField(
            inputValue = state.timerName,
            placeholder = stringResource(R.string.timer_name),
            onValueChange = {
                viewModel.onEvent(event = AddTimerEvent.ChangeTimerName(it))
            }
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(MaterialTheme.colorScheme.tertiary)
                .padding(vertical = 10.dp)
        )
        Text(
            text = stringResource(R.string.timer_duration),
            style = Typography.bodyLarge
        )
    }
}