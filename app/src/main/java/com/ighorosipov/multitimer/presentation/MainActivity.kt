package com.ighorosipov.multitimer.presentation

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.material3.Surface
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.app.ActivityCompat
import androidx.core.util.Consumer
import androidx.navigation.compose.rememberNavController
import com.ighorosipov.multitimer.presentation.screens.State
import com.ighorosipov.multitimer.presentation.ui.components.navigation.NavigationEvent
import com.ighorosipov.multitimer.presentation.ui.components.navigation.RootNavigationGraph
import com.ighorosipov.multitimer.presentation.ui.components.navigation.Screen
import com.ighorosipov.multitimer.presentation.ui.theme.MultiTimerTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.POST_NOTIFICATIONS,
                Manifest.permission.USE_FULL_SCREEN_INTENT
            ),
            0
        )
        setContent {
            val navController = rememberNavController()
            val state by viewModel.state.collectAsState()

            DisposableEffect(Unit) {
                val listener = Consumer<Intent> { intent ->
                    intent.data?.let { uri ->
                        when(uri) {
                            Screen.AlarmScreen.deeplink -> {
                                viewModel.onEvent(NavigationEvent.NavigateWithDeeplink(Screen.AlarmScreen))
                            }
                            Screen.WorldTimeScreen.deeplink -> {
                                viewModel.onEvent(NavigationEvent.NavigateWithDeeplink(Screen.WorldTimeScreen))
                            }
                            Screen.StopwatchScreen.deeplink -> {
                                viewModel.onEvent(NavigationEvent.NavigateWithDeeplink(Screen.StopwatchScreen))
                            }
                            Screen.TimerScreen.deeplink -> {
                                viewModel.onEvent(NavigationEvent.NavigateWithDeeplink(Screen.TimerScreen))
                            }
                        }
                    }
                }
                addOnNewIntentListener(listener)
                onDispose {
                    viewModel.onEvent(NavigationEvent.None)
                    removeOnNewIntentListener(listener)
                }
            }
            MultiTimerTheme {
                Surface {
                    RootNavigationGraph(
                        navController = navController,
                        state = state
                    )
                }
            }
        }
    }

}