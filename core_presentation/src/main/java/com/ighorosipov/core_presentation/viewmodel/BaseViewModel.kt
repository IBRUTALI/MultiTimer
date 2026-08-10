package com.ighorosipov.core_presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ighorosipov.core_presentation.misc.BaseUIIntent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<State, Intent>(
    initialState: State
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    private val _globalIntents = MutableSharedFlow<BaseUIIntent>()
    val globalIntents = _globalIntents.asSharedFlow()

    private val _intents = MutableSharedFlow<Intent>()
    val intents = _intents.asSharedFlow()

    protected fun updateState(
        reducer: State.() -> State
    ) {
        _state.update(reducer)
    }

    protected fun sendIntent(
        intent: BaseUIIntent
    ) {
        viewModelScope.launch {
            _globalIntents.emit(intent)
        }
    }

    protected fun sendIntent(
        intent: Intent
    ) {
        viewModelScope.launch {
            _intents.emit(intent)
        }
    }
}