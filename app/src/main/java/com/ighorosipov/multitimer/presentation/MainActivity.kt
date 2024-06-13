package com.ighorosipov.multitimer.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.ighorosipov.multitimer.presentation.ui.components.navigation.RootNavigationGraph
import com.ighorosipov.multitimer.presentation.ui.theme.MultiTimerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MultiTimerTheme {
                Surface {
                    RootNavigationGraph(navController = rememberNavController())
                }
            }
        }
    }
}