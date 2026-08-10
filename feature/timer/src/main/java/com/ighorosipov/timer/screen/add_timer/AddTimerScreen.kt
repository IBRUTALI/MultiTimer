package com.ighorosipov.timer.screen.add_timer

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ighorosipov.core_presentation.components.BaseCheckBox
import com.ighorosipov.core_presentation.components.BaseEditField
import com.ighorosipov.core_presentation.components.MainAppState
import com.ighorosipov.core_presentation.components.handleGlobalIntent
import com.ighorosipov.core_presentation.theme.LocalCustomColorsPalette
import com.ighorosipov.timer.components.ItemColor
import com.ighorosipov.timer.components.ItemRingtone
import com.ighorosipov.timer.components.TimerWidget
import com.ighorosipov.timer.components.getTimerColors
import com.ighorosipov.timer.viewmodel.AddTimerViewModel
import com.ighorosipov.utils.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTimerScreen(
    appState: MainAppState,
    viewModel: AddTimerViewModel = hiltViewModel(),
    showSnackbar: (
        String,
        SnackbarDuration,
        String?,
        actionPerformed: () -> Unit,
    ) -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    val colors = getTimerColors()

    LaunchedEffect(Unit) {
        viewModel.globalIntents.collect { intent ->
            handleGlobalIntent(
                appState = appState,
                context = context,
                showSnackbar = showSnackbar,
                intent = intent
            )
        }
    }

    LaunchedEffect(Unit) {
        viewModel.intents.collect { intent ->
            handleIntent(
                appState = appState,
                context = context,
                intent = intent
            )
        }
    }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
    ) {
        BaseEditField(
            inputValue = state.timerName,
            modifier = Modifier.padding(16.dp),
            placeholder = stringResource(R.string.timer_name),
            keyboardType = KeyboardType.Text,
            onValueChange = {
               //todo
            }
        )
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 5.dp),
            color = MaterialTheme.colorScheme.tertiary
        )
        BaseCheckBox(
            title = {
                Text(
                    text = stringResource(R.string.set_custom_duration),
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            checkedState = state.customDurationEnabled,
            onStateChange = {
                // todo
            }
        )
        AnimatedVisibility(visible = state.customDurationEnabled) {
            BaseEditField(
                inputValue = state.customDurationText,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 5.dp),
                placeholder = stringResource(R.string.time_in_minutes),
                keyboardType = KeyboardType.Number,
                onValueChange = {
                    //todo
                }
            )
        }
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 5.dp),
            color = MaterialTheme.colorScheme.tertiary
        )
        Text(
            text = stringResource(R.string.timer_duration),
            modifier = Modifier.padding(vertical = 15.dp, horizontal = 18.dp),
            style = MaterialTheme.typography.bodyLarge
        )
        TimerWidget(
            modifier = Modifier.padding(horizontal = 10.dp),
            limitItems = 3,
            hoursText = {
                Text(
                    text = stringResource(R.string.h),
                    modifier = Modifier
                        .padding(top = 10.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall
                )
            },
            minutesText = {
                Text(
                    text = stringResource(R.string.m),
                    modifier = Modifier
                        .padding(top = 10.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall
                )
            },
            secondsText = {
                Text(
                    text = stringResource(R.string.s),
                    modifier = Modifier
                        .padding(top = 10.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall
                )
            },
            onTimeChange = {
                //todo
            }
        )
        HorizontalDivider(
            modifier = Modifier.padding(top = 15.dp, bottom = 5.dp),
            color = MaterialTheme.colorScheme.tertiary
        )
        Text(
            text = stringResource(R.string.color),
            modifier = Modifier.padding(vertical = 15.dp, horizontal = 18.dp),
            style = MaterialTheme.typography.bodyLarge
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(colors) { i, item ->
                ItemColor(
                    modifier = Modifier
                        .padding(start = 15.dp)
                        .size(50.dp),
                    color = item,
                    borderColor = if (state.selectedColorIndex == i) {
                        LocalCustomColorsPalette.current.white
                    } else LocalCustomColorsPalette.current.white,
                    onItemClick = {
                        // todo
                    }
                )
            }
        }
        HorizontalDivider(
            modifier = Modifier.padding(top = 15.dp, bottom = 5.dp),
            color = MaterialTheme.colorScheme.tertiary
        )
        Text(
            text = stringResource(R.string.notification_sound),
            modifier = Modifier.padding(vertical = 15.dp, horizontal = 18.dp),
            style = MaterialTheme.typography.bodyLarge
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(state.ringtones) { i, item ->
                ItemRingtone(
                    title = item.title,
                    modifier = Modifier,
                    checkedState = state.selectedRingtoneIndex == i,
                    borderColor = if (state.selectedRingtoneIndex == i) {
                        MaterialTheme.colorScheme.onBackground
                    } else MaterialTheme.colorScheme.background,
                    isPlaying = if (state.selectedRingtoneIndex == i) {
                        state.playingRingtone.second
                    } else {
                        false
                    },
                    onItemClick = {
                        // todo
                    }
                )
            }
        }
    }
}

private fun handleIntent(
    appState: MainAppState,
    context: Context,
    intent: AddTimerScreenIntent
) {
    //todo
}