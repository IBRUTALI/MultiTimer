package com.ighorosipov.multitimer.feature.timer.presentation

import androidx.lifecycle.ViewModel
import com.ighorosipov.multitimer.di.DefaultDispatcher
import com.ighorosipov.multitimer.ui.State
import com.ighorosipov.multitimer.ui.components.navigation.NavigationEvent
import com.ighorosipov.multitimer.ui.components.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    @DefaultDispatcher var dispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _state = MutableStateFlow(
        State(navigationEvent = NavigationEvent.None)
    )
    val state = _state.asStateFlow()

    fun onEvent(event: NavigationEvent) {
        when (event) {
            is NavigationEvent.NavigateWithDeeplink -> {
                handleDeeplink(event.screen)
            }

            is NavigationEvent.None -> {
                consumeEvent()
            }
        }
    }

    private fun handleDeeplink(screen: Screen) {
        _state.update {
            state.value.copy(
                id = UUID.randomUUID().toString(),
                navigationEvent = NavigationEvent.NavigateWithDeeplink(screen)
            )
        }
    }

    private fun consumeEvent() {
       _state.update {
           state.value.copy(
               navigationEvent = NavigationEvent.None
           )
       }
    }

}