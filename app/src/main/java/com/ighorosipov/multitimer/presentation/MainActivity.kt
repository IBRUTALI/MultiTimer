package com.ighorosipov.multitimer.presentation

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.Surface
import androidx.core.app.ActivityCompat
import androidx.navigation.compose.rememberNavController
import com.ighorosipov.multitimer.presentation.ui.components.navigation.RootNavigationGraph
import com.ighorosipov.multitimer.presentation.ui.theme.MultiTimerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
            0
        )
        setContent {
            MultiTimerTheme {
                Surface {
                    RootNavigationGraph(
                        navController = rememberNavController()
                    )
                }
            }
        }
    }
}