package com.ighorosipov.multitimer

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.ighorosipov.core_presentation.components.MainAppState
import com.ighorosipov.core_presentation.components.rememberAppState
import com.ighorosipov.core_presentation.navigation.BottomNavigation
import com.ighorosipov.core_presentation.theme.MultiTimerTheme
import com.ighorosipov.multitimer.navigation.NavigationGraph
import com.ighorosipov.multitimer.ui.MainActivityIntent
import com.ighorosipov.utils.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen().apply {
            setKeepOnScreenCondition(condition = { viewModel.state.value.splashCondition })
        }
        setContent {
            val state by viewModel.state.collectAsState()
            val appState = rememberAppState()
            val context = LocalContext.current
            val snackbarDefaultMessage = stringResource(R.string.ok)
            LaunchedEffect(Unit) {
                viewModel.intents.collect { intent ->
                    handleIntent(
                        appState = appState,
                        context = context,
                        intent = intent,
                        snackbarDefaultMessage = snackbarDefaultMessage
                    )
                }
            }
            MultiTimerTheme {
                Scaffold(
                    topBar = {},
                    modifier = Modifier
                        .fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.background,
                    bottomBar = {
                        BottomNavigation(
                            appState = appState
                        )
                    },
                    snackbarHost = {
                        SnackbarHost(
                            hostState = appState.snackbarState,
                            modifier = Modifier
                                .padding(WindowInsets.ime.asPaddingValues())
                                .windowInsetsPadding(WindowInsets.navigationBars)
                        ) { data ->
                            Snackbar(
                                snackbarData = data,
                                containerColor = Color.White,
                                contentColor = Color.Black,
                                actionColor = Color.Black,
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.padding(16.dp),
                            )
                        }
                    }
                ) { contentPadding ->
                    NavigationGraph(
                        paddingValues = contentPadding,
                        appState = appState,
                        showSnackbar = { message, duration, label, action ->
                            appState.showSnackbar(
                                message = message,
                                duration = duration,
                                actionLabel = label,
                                actionPerformed = action
                            )
                        },
                        startDestination = state.startDestination
                    )
                }
            }
        }
    }

        private fun handleIntent(
            appState: MainAppState,
            context: Context,
            intent: MainActivityIntent,
            snackbarDefaultMessage: String
        ) {
            //todo
        }

    }