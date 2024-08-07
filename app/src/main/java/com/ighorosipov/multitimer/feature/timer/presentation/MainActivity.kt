package com.ighorosipov.multitimer.feature.timer.presentation

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
import com.ighorosipov.multitimer.ui.components.navigation.NavigationEvent
import com.ighorosipov.multitimer.ui.components.navigation.Screen
import com.ighorosipov.multitimer.ui.components.navigation.tab_nav_graphs.RootNavigationGraph
import com.ighorosipov.multitimer.ui.theme.MultiTimerTheme
import dagger.hilt.android.AndroidEntryPoint

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
            val state by viewModel.state.collectAsState()

            DisposableEffect(Unit) {
                val listener = Consumer<Intent> { intent ->
                    intent.data?.let { uri ->
                        when (uri) {
                            Screen.AlarmGraph.Alarm().deeplink -> {
                                viewModel.onEvent(NavigationEvent.NavigateWithDeeplink(Screen.AlarmGraph.Alarm()))
                            }

                            Screen.WorldTimeGraph.WorldTime().deeplink -> {
                                viewModel.onEvent(NavigationEvent.NavigateWithDeeplink(Screen.WorldTimeGraph.WorldTime()))
                            }

                            Screen.StopwatchGraph.Stopwatch().deeplink -> {
                                viewModel.onEvent(NavigationEvent.NavigateWithDeeplink(Screen.StopwatchGraph.Stopwatch()))
                            }

                            Screen.TimerGraph.TimerDetails().deeplink -> {
                                viewModel.onEvent(NavigationEvent.NavigateWithDeeplink(Screen.TimerGraph.TimerDetails()))
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
                        navController = rememberNavController(),
                        state = state
                    )
                }
            }
        }
    }

}