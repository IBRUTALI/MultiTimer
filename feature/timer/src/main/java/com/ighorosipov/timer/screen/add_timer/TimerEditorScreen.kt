package com.ighorosipov.timer.screen.add_timer

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ighorosipov.core_presentation.components.BaseContentCard
import com.ighorosipov.core_presentation.components.BaseEditField
import com.ighorosipov.core_presentation.components.BaseToolbar
import com.ighorosipov.core_presentation.components.IconParams
import com.ighorosipov.core_presentation.components.MainAppState
import com.ighorosipov.core_presentation.components.edgeFade
import com.ighorosipov.core_presentation.components.handleGlobalIntent
import com.ighorosipov.core_presentation.theme.LocalCustomColorsPalette
import com.ighorosipov.timer.components.ItemColor
import com.ighorosipov.timer.components.ItemRingtone
import com.ighorosipov.timer.components.TimerWidget
import com.ighorosipov.timer.components.getTimerColors
import com.ighorosipov.timer.viewmodel.TimerEditorViewModel
import com.ighorosipov.utils.R
import com.ighorosipov.utils.TimerEditorScreenType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerEditorScreen(
    appState: MainAppState,
    screenType: TimerEditorScreenType,
    viewModel: TimerEditorViewModel = hiltViewModel<TimerEditorViewModel, TimerEditorViewModel.Factory>(
        creationCallback = { factory ->
            factory.create(screenType)
        }
    ),
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

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
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
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        BaseToolbar(
            iconParams = IconParams(
                icon = R.drawable.outline_arrow_back,
                iconSize = 24
            ),
            title = stringResource(R.string.timer),
            onIconClick = viewModel::onBackClick
        )

        BaseContentCard(
            content = {
                item {
                    BaseEditField(
                        inputValue = state.timerName,
                        modifier = Modifier.padding(16.dp),
                        placeholder = stringResource(R.string.timer_name),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.None,
                            autoCorrectEnabled = false,
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                keyboardController?.hide()
                                focusManager.clearFocus()
                            }
                        ),
                        focusManager = focusManager,
                        onValueChange = viewModel::onTimerNameChange
                    )
                }
            }
        )

        BaseContentCard(
            title = stringResource(R.string.timer_duration),
            content = {
                item {
                    TimerWidget(
                        limitItems = 3,
                        initialTime = state.initialTime,
                        onTimeChange = viewModel::onTimeChange
                    )
                }
            }
        )

        val colorListState = rememberLazyListState()
        BaseContentCard(
            title = stringResource(R.string.color),
            content = {
                item {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 5.dp)
                            .edgeFade(
                                listState = colorListState,
                                fadeWidth = 24.dp
                            ),
                        state = colorListState,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        itemsIndexed(colors) { i, item ->
                            ItemColor(
                                modifier = Modifier
                                    .size(40.dp),
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
                }
            }
        )

        BaseContentCard(
            title = stringResource(R.string.notification_sound),
            content = {
                item {
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
        )
    }
}

private fun handleIntent(
    appState: MainAppState,
    context: Context,
    intent: TimerEditorScreenIntent
) {
    //todo
}