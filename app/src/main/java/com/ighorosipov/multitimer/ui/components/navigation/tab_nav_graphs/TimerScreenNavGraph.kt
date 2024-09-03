package com.ighorosipov.multitimer.ui.components.navigation.tab_nav_graphs

import android.content.Intent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import com.ighorosipov.multitimer.R
import com.ighorosipov.multitimer.feature.timer.presentation.screens.add_timer.AddTimerScreen
import com.ighorosipov.multitimer.feature.timer.presentation.screens.timer.TimerScreen
import com.ighorosipov.multitimer.feature.timer.presentation.screens.timer_details.TimerDetailsScreen
import com.ighorosipov.multitimer.ui.components.navigation.Screen
import com.ighorosipov.multitimer.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerScreenNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val context = LocalContext.current
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    val title = when (currentDestination) {
                        Screen.TimerGraph.Timer().route -> {
                            context.resources.getString(
                                Screen.TimerGraph.Timer().labelStringId
                            )
                        }

                        Screen.TimerGraph.AddTimer().route -> {
                            context.resources.getString(
                                Screen.TimerGraph.AddTimer().labelStringId
                            )
                        }

                        Screen.TimerGraph.TimerDetails().route -> {
                            context.resources.getString(
                                Screen.TimerGraph.TimerDetails().labelStringId
                            )
                        }

                        else -> null
                    }
                    title?.let {
                        Text(
                            text = title,
                            style = Typography.titleLarge
                        )
                    }
                },
                actions = {
                    when (currentDestination) {
                        Screen.TimerGraph.AddTimer().route -> {
                            IconButton(onClick = {  }) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_apply),
                                    contentDescription = "Save"
                                )
                            }
                        }
                    }
        },
        navigationIcon = {
            if (navController.previousBackStackEntry != null) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = if (currentDestination == Screen.TimerGraph.AddTimer().route) {
                            ImageVector.vectorResource(id = R.drawable.ic_cancel)
                        } else {
                            Icons.AutoMirrored.Filled.ArrowBack
                        },
                        contentDescription = "Back"
                    )
                }
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.background)
    )
}
) {
    paddingValues ->
    NavHost(
        navController = navController,
        route = Screen.TimerGraph().route,
        startDestination = Screen.TimerGraph.Timer().route,
        enterTransition = {
            fadeIn()
        },
        exitTransition = {
            fadeOut()
        }
    ) {
        composable(route = Screen.TimerGraph.Timer().route) {
            TimerScreen(
                modifier = modifier.padding(paddingValues),
                navController = navController
            )
        }
        composable(route = Screen.TimerGraph.AddTimer().route) {
            AddTimerScreen(
                modifier = modifier.padding(paddingValues),
                navController = navController
            )
        }
        composable(
            route = Screen.TimerGraph.TimerDetails().route,
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = Screen.TimerGraph.TimerDetails().deeplink.toString()
                    action = Intent.ACTION_VIEW
                }
            )
        ) {
            TimerDetailsScreen(
                modifier = modifier.padding(paddingValues),
                navController = navController
            )
        }
    }
}
}