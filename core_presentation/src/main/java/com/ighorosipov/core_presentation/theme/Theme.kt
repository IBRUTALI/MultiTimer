package com.ighorosipov.core_presentation.theme

import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

private val DarkColorScheme = darkColorScheme()

private val LightColorScheme = lightColorScheme()

@Immutable
data class CustomColorsPalette(
    val white: Color = Color.Unspecified,
    val red: Color = Color.Unspecified,
)

val OnLightCustomColorsPalette = CustomColorsPalette(
    white = White,
    red = Red
)

val OnDarkCustomColorsPalette = CustomColorsPalette(
    white = White,
    red = Red
)

val LocalCustomColorsPalette = staticCompositionLocalOf { CustomColorsPalette() }

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun MultiTimerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    activity: Activity? = LocalActivity.current,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val customColorsPalette =
        if (darkTheme) OnDarkCustomColorsPalette
        else OnLightCustomColorsPalette

    val window = if (activity != null) {
        calculateWindowSizeClass(activity = activity)
    } else {
        WindowSizeClass.calculateFromSize(size = DpSize(360.dp, 640.dp))
    }

    val containerSize = LocalWindowInfo.current.containerSize

    val screenScale = calculateScreenScale(
        window = window,
        width = containerSize.width
    )

    val theme = remember(screenScale) {
        ThemeFactory.create(screenScale)
    }

    val typography = theme.typography
    val shape = theme.shapes
    val dimens = theme.dimens

    CompositionLocalProvider(
       LocalCustomColorsPalette provides customColorsPalette
    ) {
        AppUtils(appDimens = dimens) {
            MaterialTheme(
                colorScheme = colorScheme,
                shapes = shape,
                typography = typography,
                content = content
            )
        }
    }
}

private fun calculateScreenScale(
    window: WindowSizeClass,
    width: Int
): ScreenScale {

    return when (window.widthSizeClass) {

        WindowWidthSizeClass.Compact ->
            when {

                width <= 360 ->
                    ScreenScale.CompactSmall

                width < 599 ->
                    ScreenScale.CompactMedium

                else ->
                    ScreenScale.Compact
            }

        WindowWidthSizeClass.Medium ->
            ScreenScale.Medium

        else ->
            ScreenScale.Expanded
    }
}

val MaterialTheme.dimens
    @Composable
    get() = LocalAppDimens.current