package com.ighorosipov.core_presentation.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.AnimationState
import androidx.compose.animation.core.animateTo
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ighorosipov.core_presentation.components.MainAppState
import com.ighorosipov.core_presentation.theme.LocalCustomColorsPalette

@Composable
fun BottomNavigation(
    modifier: Modifier = Modifier,
    appState: MainAppState
) {

    val backStackState by appState.navController.currentBackStackEntryAsState()
    val currentDestination = backStackState?.destination

    val isKeyboardOpen by keyboardAsState()

    val currentRoute = remember(backStackState) {
        Routes.fromRoute(
            route = backStackState?.destination?.route ?: "",
            args = backStackState?.arguments
        )
    }

    val bottomNavigationItems = remember {
        listOf(
            BottomNavItems.AlarmScreens,
            BottomNavItems.WorldTimeScreens,
            BottomNavItems.StopwatchScreens,
            BottomNavItems.TimerScreens
        )
    }

    val isBottomBarVisible = remember(backStackState) {
        when (currentRoute) {
            is Routes.TimerEditorScreen.Add -> false
            is Routes.TimerEditorScreen.Edit -> false
            null -> false

            else -> true
        }
    }

    AnimatedContent(
        targetState = isBottomBarVisible && !isKeyboardOpen,
        transitionSpec = {
            slideInVertically { height -> height } togetherWith
                    slideOutVertically { height -> height }
        },
        label = ""
    ) { isVisible ->
        if (isVisible) {
            NavigationBar(
                modifier = modifier,
                containerColor = LocalCustomColorsPalette.current.surfaceContainerLow,
                tonalElevation = 0.dp
            ) {

                bottomNavigationItems.forEach { item ->

                    val isSelected = currentDestination?.hierarchy?.any {
                        it.hasRoute(item.screenRoute::class)
                    } == true

                    val scaleState = remember { AnimationState(initialValue = 1f) }

                    LaunchedEffect(isSelected) {
                        if (isSelected) {
                            scaleState.animateTo(1.2f, tween(50))
                            scaleState.animateTo(1f, tween(50))
                        } else {
                            scaleState.animateTo(1f, tween(50))
                        }
                    }

                    NavigationBarItem(
                        modifier = Modifier.scale(scaleState.value),
                        icon = {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = item.icon),
                                contentDescription = null,
                                modifier = Modifier.size(28.dp)
                            )
                        },
                        selected = isSelected,
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = LocalCustomColorsPalette.current.inversePrimary,
                            selectedIconColor = LocalCustomColorsPalette.current.onPrimaryContainer,
                            unselectedIconColor = LocalCustomColorsPalette.current.onSurface
                        ),
                        onClick = {
                            appState.navController.navigateToTab(
                                item = item,
                                restoreTabStack = !isSelected,
                                currentNestedLevelDestination = currentRoute
                            )
                        }
                    )
                }
            }
        }
    }
}