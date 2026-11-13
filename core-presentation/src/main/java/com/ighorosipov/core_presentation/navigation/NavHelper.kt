package com.ighorosipov.core_presentation.navigation

import android.os.Bundle
import android.view.ViewTreeObserver
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.ighorosipov.core_presentation.components.MainAppState
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

fun MainAppState.popUpUnsafe() {
    navController.popBackStack()
}

fun MainAppState.popUp() {
    if (navController.isSafeNavigate)
        navController.popBackStack()
}

fun MainAppState.navigateTo(route: Any) {
    if (navController.isSafeNavigate) {
        navController.navigate(route) {
            launchSingleTop = true
        }
    }
}

fun MainAppState.navigateAndPopUp(route: Any, popUp: Any) {
    navController.navigate(route) {
        launchSingleTop = true
        popUpTo(popUp) { inclusive = true }
    }
}

fun MainAppState.navigateAndPopUpSavable(route: Any, popUp: Any) {
    navController.navigate(route) {
        launchSingleTop = true
        popUpTo(popUp) {
            inclusive = false
        }
    }
}

fun MainAppState.navigateAndPopWithClear(route: Any, popUp: Any) {
    navController.navigate(route) {
        launchSingleTop = true
        popUpTo(popUp) {
            inclusive = false
            saveState = false
        }
    }
}

fun MainAppState.navigateSaved(route: Any, popUp: Any) {
    navController.navigate(route) {
        launchSingleTop = true
        restoreState = true
        popUpTo(popUp) { saveState = true }
    }
}

fun MainAppState.popBackStack(route: Any) {
    navController.popBackStack(route, inclusive = false)
}

fun MainAppState.popBackStackInclusive(route: Any) {
    navController.popBackStack(route, inclusive = true)
}

inline fun <reified T : Routes> MainAppState.clearAndNavigate(route: Any) {
    navController.clearBackStack<T>()
    navController.navigate(route) {
        launchSingleTop = true
        popUpTo(0) { inclusive = true }
    }
}

fun MainAppState.clearAndNavigateToAnotherGraph(route: Any) {
    navController.navigate(route = route) {
        popUpTo(route) {
            inclusive = false
        }
        launchSingleTop = true
    }
}

fun MainAppState.navigateToAnotherGraph(route: Any) {
    navController.navigate(route) {
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

fun NavHostController.navigateToTab(
    item: BottomNavItems,
    restoreTabStack: Boolean,
    currentNestedLevelDestination: Any?
) {
    if (
        currentNestedLevelDestination in (listOf(
            Routes.AlarmScreen,
            Routes.WorldTimeScreen,
            Routes.StopwatchScreen,
            Routes.TimerScreen
        ))
    ) {
        navigate(item.screenRoute) {
            popUpTo(graph.id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    } else {
        navigate(item.screenRoute) {
            popUpTo(graph.id) {
                saveState = restoreTabStack
            }
            launchSingleTop = true
            restoreState = restoreTabStack
        }
    }
}

internal fun <T : Any> createInstance(kClass: KClass<T>, bundle: Bundle?): T? {
    val constructor = kClass.primaryConstructor
    return constructor?.let {
        val args = it.parameters.associateWith { param ->
            bundle?.get(param.name)
        }
        it.callBy(args)
    } ?: kClass.objectInstance
}

@Composable
fun keyboardAsState(): State<Boolean> {
    val view = LocalView.current
    var isImeVisible by remember { mutableStateOf(false) }

    DisposableEffect(LocalWindowInfo.current) {
        val listener = ViewTreeObserver.OnPreDrawListener {
            isImeVisible = ViewCompat.getRootWindowInsets(view)
                ?.isVisible(WindowInsetsCompat.Type.ime()) == true
            true
        }
        view.viewTreeObserver.addOnPreDrawListener(listener)
        onDispose {
            view.viewTreeObserver.removeOnPreDrawListener(listener)
        }
    }
    return rememberUpdatedState(isImeVisible)
}

val NavHostController.isSafeNavigate: Boolean
    get() = this.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED