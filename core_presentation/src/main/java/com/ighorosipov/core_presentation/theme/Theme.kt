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
    val blue: Color = Color.Unspecified,
    val green: Color = Color.Unspecified,
    val greenYellow: Color = Color.Unspecified,
    val teal: Color = Color.Unspecified,
    val orange: Color = Color.Unspecified,
    val yellow: Color = Color.Unspecified,
    val purple: Color = Color.Unspecified,
    val pink: Color = Color.Unspecified,
    val grey: Color = Color.Unspecified,
    val primary: Color = Color.Unspecified,
    val onPrimary: Color = Color.Unspecified,
    val secondary: Color = Color.Unspecified,
    val onSecondary: Color = Color.Unspecified,
    val tertiary: Color = Color.Unspecified,
    val onTertiary: Color = Color.Unspecified,
    val error: Color = Color.Unspecified,
    val onError: Color = Color.Unspecified,
    val primaryContainer: Color = Color.Unspecified,
    val onPrimaryContainer: Color = Color.Unspecified,
    val secondaryContainer: Color = Color.Unspecified,
    val onSecondaryContainer: Color = Color.Unspecified,
    val tertiaryContainer: Color = Color.Unspecified,
    val onTertiaryContainer: Color = Color.Unspecified,
    val errorContainer: Color = Color.Unspecified,
    val onErrorContainer: Color = Color.Unspecified,
    val surfaceDim: Color = Color.Unspecified,
    val surface: Color = Color.Unspecified,
    val onSurface: Color = Color.Unspecified,
    val onSurfaceVariant: Color = Color.Unspecified,
    val surfaceBright: Color = Color.Unspecified,
    val inverseSurface: Color = Color.Unspecified,
    val inverseOnSurface: Color = Color.Unspecified,
    val surfaceContainerLowest: Color = Color.Unspecified,
    val surfaceContainerLow: Color = Color.Unspecified,
    val surfaceContainer: Color = Color.Unspecified,
    val surfaceContainerHigh: Color = Color.Unspecified,
    val surfaceContainerHighest: Color = Color.Unspecified,
    val outline: Color = Color.Unspecified,
    val outlineVariant: Color = Color.Unspecified,
    val inversePrimary: Color = Color.Unspecified,
    val scrim: Color = Color.Unspecified
)

val OnLightCustomColorsPalette = CustomColorsPalette(
    white = AppColors.White,
    red = AppColors.Red,
    blue = AppColors.Blue,
    green = AppColors.Green,
    greenYellow = AppColors.GreenYellow,
    teal = AppColors.Teal,
    orange = AppColors.Orange,
    yellow = AppColors.Yellow,
    purple = AppColors.Purple,
    pink = AppColors.Pink,
    grey = AppColors.Grey,
    primary = Color(0xFF715954),
    onPrimary = Color(0xFFFFFFFF),
    secondary = Color(0xFF6C5A58),
    onSecondary = Color(0xFFFFFFFF),
    tertiary = Color(0xFF775651),
    onTertiary = Color(0xFFFFFFFF),
    error = Color(0xFFBA1A1A),
    onError = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFFDDBD5),
    onPrimaryContainer = Color(0xFF58413E),
    secondaryContainer = Color(0xFFF5DDD9),
    onSecondaryContainer = Color(0xFF534341),
    tertiaryContainer = Color(0xFFFFDAD4),
    onTertiaryContainer = Color(0xFF5D3F3A),
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF93000A),
    surfaceDim = Color(0xFFE0D8D7),
    surface = Color(0xFFFFF8F6),
    onSurface = Color(0xFF1E1B1A),
    onSurfaceVariant = Color(0xFF4A4645),
    surfaceBright = Color(0xFFFFF8F6),
    inverseSurface = Color(0xFF332F2F),
    inverseOnSurface = Color(0xFFF7EFED),
    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceContainerLow = Color(0xFFFAF2F0),
    surfaceContainer = Color(0xFFF4ECEB),
    surfaceContainerHigh = Color(0xFFEFE6E5),
    surfaceContainerHighest = Color(0xFFE9E1DF),
    outline = Color(0xFF7C7675),
    outlineVariant = Color(0xFFCCC5C3),
    inversePrimary = Color(0xFFDFBFBA),
    scrim = Color(0xFF000000)
)

val OnDarkCustomColorsPalette = CustomColorsPalette(
    white = AppColors.White,
    red = AppColors.Red,
    blue = AppColors.Blue,
    green = AppColors.Green,
    greenYellow = AppColors.GreenYellow,
    teal = AppColors.Teal,
    orange = AppColors.Orange,
    yellow = AppColors.Yellow,
    purple = AppColors.Purple,
    pink = AppColors.Pink,
    grey = AppColors.Grey,
    primary = Color(0xFFDFBFBA),
    onPrimary = Color(0xFF402B28),
    secondary = Color(0xFFD8C2BE),
    onSecondary = Color(0xFF3B2D2B),
    tertiary = Color(0xFFE7BDB6),
    onTertiary = Color(0xFF442925),
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    primaryContainer = Color(0xFF58413E),
    onPrimaryContainer = Color(0xFFFDDBD5),
    secondaryContainer = Color(0xFF534341),
    onSecondaryContainer = Color(0xFFF5DDD9),
    tertiaryContainer = Color(0xFF5D3F3A),
    onTertiaryContainer = Color(0xFFFFDAD4),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),
    surfaceDim = Color(0xFF161312),
    surface = Color(0xFF161312),
    onSurface = Color(0xFFE9E1DF),
    onSurfaceVariant = Color(0xFFCCC5C3),
    surfaceBright = Color(0xFF3C3838),
    inverseSurface = Color(0xFFE9E1DF),
    inverseOnSurface = Color(0xFF332F2F),
    surfaceContainerLowest = Color(0xFF100D0D),
    surfaceContainerLow = Color(0xFF1E1B1A),
    surfaceContainer = Color(0xFF221F1E),
    surfaceContainerHigh = Color(0xFF2D2928),
    surfaceContainerHighest = Color(0xFF383433),
    outline = Color(0xFF968F8E),
    outlineVariant = Color(0xFF968F8E),
    inversePrimary = Color(0xFF715954),
    scrim = Color(0xFF000000)
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