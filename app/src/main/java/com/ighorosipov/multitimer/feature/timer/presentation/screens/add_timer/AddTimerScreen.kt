package com.ighorosipov.multitimer.feature.timer.presentation.screens.add_timer

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ighorosipov.multitimer.R
import com.ighorosipov.multitimer.feature.timer.presentation.components.ItemColor
import com.ighorosipov.multitimer.feature.timer.presentation.components.ItemRingtone
import com.ighorosipov.multitimer.feature.timer.presentation.components.TimerWidget
import com.ighorosipov.multitimer.ui.components.BaseCheckBox
import com.ighorosipov.multitimer.ui.components.edit_field.BaseEditField
import com.ighorosipov.multitimer.ui.components.navigation.Screen
import com.ighorosipov.multitimer.ui.theme.Blue
import com.ighorosipov.multitimer.ui.theme.Green
import com.ighorosipov.multitimer.ui.theme.GreenYellow
import com.ighorosipov.multitimer.ui.theme.Grey
import com.ighorosipov.multitimer.ui.theme.Orange
import com.ighorosipov.multitimer.ui.theme.Pink
import com.ighorosipov.multitimer.ui.theme.Purple
import com.ighorosipov.multitimer.ui.theme.Red
import com.ighorosipov.multitimer.ui.theme.Teal
import com.ighorosipov.multitimer.ui.theme.Typography
import com.ighorosipov.multitimer.ui.theme.Yellow
import com.ighorosipov.multitimer.utils.base.toTimeMinutesInMillis

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTimerScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: AddTimerViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()
    val colors = listOf(
        Red,
        Blue,
        Green,
        GreenYellow,
        Teal,
        Orange,
        Yellow,
        Purple,
        Pink,
        Grey
    )
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = context.resources.getString(
                            Screen.TimerGraph.AddTimer().labelStringId
                        ),
                        style = Typography.titleLarge
                    )
                },
                actions = {
                    IconButton(onClick = { viewModel.onEvent(AddTimerEvent.SaveTimer) }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_apply),
                            contentDescription = "Save"
                        )
                    }
                },
                navigationIcon = {
                    if (navController.previousBackStackEntry != null) {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector =
                                ImageVector.vectorResource(id = R.drawable.ic_cancel),
                                contentDescription = "Back"
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {
            BaseEditField(
                inputValue = state.timerName,
                modifier = Modifier.padding(16.dp),
                placeholder = stringResource(R.string.timer_name),
                keyboardType = KeyboardType.Text,
                onValueChange = {
                    viewModel.onEvent(event = AddTimerEvent.ChangeTimerName(it))
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
                        style = Typography.bodyLarge
                    )
                },
                checkedState = state.customDurationEnabled,
                onStateChange = {
                    viewModel.onEvent(event = AddTimerEvent.ChangeCustomDurationCheck(isChecked = it))
                }
            )
            if (state.customDurationEnabled) {
                BaseEditField(
                    inputValue = state.customDurationText,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                    placeholder = stringResource(R.string.time_in_minutes),
                    keyboardType = KeyboardType.Number,
                    onValueChange = {
                        viewModel.onEvent(
                            event = AddTimerEvent.ChangeCustomTimerText(
                                it
                            )
                        )
                        viewModel.onEvent(
                            event = AddTimerEvent.ChangeTimerDuration(
                                it.toTimeMinutesInMillis()
                            )
                        )
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
                style = Typography.bodyLarge
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
                        style = Typography.bodySmall
                    )
                },
                minutesText = {
                    Text(
                        text = stringResource(R.string.m),
                        modifier = Modifier
                            .padding(top = 10.dp),
                        textAlign = TextAlign.Center,
                        style = Typography.bodySmall
                    )
                },
                secondsText = {
                    Text(
                        text = stringResource(R.string.s),
                        modifier = Modifier
                            .padding(top = 10.dp),
                        textAlign = TextAlign.Center,
                        style = Typography.bodySmall
                    )
                },
                onTimeChange = {
                    viewModel.onEvent(AddTimerEvent.ChangeTimerDuration(it))
                }
            )
            HorizontalDivider(
                modifier = Modifier.padding(top = 15.dp, bottom = 5.dp),
                color = MaterialTheme.colorScheme.tertiary
            )
            Text(
                text = stringResource(R.string.color),
                modifier = Modifier.padding(vertical = 15.dp, horizontal = 18.dp),
                style = Typography.bodyLarge
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
                            MaterialTheme.colorScheme.onBackground
                        } else MaterialTheme.colorScheme.background,
                        onItemClick = {
                            viewModel.onEvent(AddTimerEvent.ChangeTimerColor(i))
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
                style = Typography.bodyLarge
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
                            viewModel.onEvent(
                                AddTimerEvent.RingtoneCheck(
                                    index = i,
                                    uri = item.uri
                                )
                            )
                        }
                    )
                }
            }
        }
    }

}

@Preview(
    showBackground = true
)
@Composable
fun AddTimerScreenPreview(modifier: Modifier = Modifier) {
    AddTimerScreen(navController = rememberNavController())
}