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

private val DarkColorScheme = darkColorScheme(
    background = Color(0xFF161312)
)

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
    val scrim: Color = Color.Unspecified,
    val background: Color = Color.Unspecified,
    val onBackground: Color = Color.Unspecified,
    val surfaceVariant: Color = Color.Unspecified,
    val surfaceTint: Color = Color.Unspecified,
    val primaryFixed: Color = Color.Unspecified,
    val primaryFixedDim: Color = Color.Unspecified,
    val onPrimaryFixed: Color = Color.Unspecified,
    val onPrimaryFixedVariant: Color = Color.Unspecified,
    val secondaryFixed: Color = Color.Unspecified,
    val secondaryFixedDim: Color = Color.Unspecified,
    val onSecondaryFixed: Color = Color.Unspecified,
    val onSecondaryFixedVariant: Color = Color.Unspecified,
    val tertiaryFixed: Color = Color.Unspecified,
    val tertiaryFixedDim: Color = Color.Unspecified,
    val onTertiaryFixed: Color = Color.Unspecified,
    val onTertiaryFixedVariant: Color = Color.Unspecified
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
    primary = PrimaryLight,
    onPrimary = OnPrimaryLight,
    primaryContainer = PrimaryContainerLight,
    onPrimaryContainer = OnPrimaryContainerLight,
    inversePrimary = InversePrimaryLight,
    secondary = SecondaryLight,
    onSecondary = OnSecondaryLight,
    secondaryContainer = SecondaryContainerLight,
    onSecondaryContainer = OnSecondaryContainerLight,
    tertiary = TertiaryLight,
    onTertiary = OnTertiaryLight,
    tertiaryContainer = TertiaryContainerLight,
    onTertiaryContainer = OnTertiaryContainerLight,
    background = BackgroundLight,
    onBackground = OnBackgroundLight,
    surface = SurfaceLight,
    onSurface = OnSurfaceLight,
    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = OnSurfaceVariantLight,
    surfaceTint = SurfaceTintLight,
    inverseSurface = InverseSurfaceLight,
    inverseOnSurface = InverseOnSurfaceLight,
    error = ErrorLight,
    onError = OnErrorLight,
    errorContainer = ErrorContainerLight,
    onErrorContainer = OnErrorContainerLight,
    outline = OutlineLight,
    outlineVariant = OutlineVariantLight,
    scrim = ScrimLight,
    surfaceBright = SurfaceBrightLight,
    surfaceContainer = SurfaceContainerLight,
    surfaceContainerHigh = SurfaceContainerHighLight,
    surfaceContainerHighest = SurfaceContainerHighestLight,
    surfaceContainerLow = SurfaceContainerLowLight,
    surfaceContainerLowest = SurfaceContainerLowestLight,
    surfaceDim = SurfaceDimLight,
    primaryFixed = PrimaryFixed,
    primaryFixedDim = PrimaryFixedDim,
    onPrimaryFixed = OnPrimaryFixed,
    onPrimaryFixedVariant = OnPrimaryFixedVariant,
    secondaryFixed = SecondaryFixed,
    secondaryFixedDim = SecondaryFixedDim,
    onSecondaryFixed = OnSecondaryFixed,
    onSecondaryFixedVariant = OnSecondaryFixedVariant,
    tertiaryFixed = TertiaryFixed,
    tertiaryFixedDim = TertiaryFixedDim,
    onTertiaryFixed = OnTertiaryFixed,
    onTertiaryFixedVariant = OnTertiaryFixedVariant
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
    primary = PrimaryDark,
    onPrimary = OnPrimaryDark,
    primaryContainer = PrimaryContainerDark,
    onPrimaryContainer = OnPrimaryContainerDark,
    inversePrimary = InversePrimaryDark,
    secondary = SecondaryDark,
    onSecondary = OnSecondaryDark,
    secondaryContainer = SecondaryContainerDark,
    onSecondaryContainer = OnSecondaryContainerDark,
    tertiary = TertiaryDark,
    onTertiary = OnTertiaryDark,
    tertiaryContainer = TertiaryContainerDark,
    onTertiaryContainer = OnTertiaryContainerDark,
    background = BackgroundDark,
    onBackground = OnBackgroundDark,
    surface = SurfaceDark,
    onSurface = OnSurfaceDark,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = OnSurfaceVariantDark,
    surfaceTint = SurfaceTintDark,
    inverseSurface = InverseSurfaceDark,
    inverseOnSurface = InverseOnSurfaceDark,
    error = ErrorDark,
    onError = OnErrorDark,
    errorContainer = ErrorContainerDark,
    onErrorContainer = OnErrorContainerDark,
    outline = OutlineDark,
    outlineVariant = OutlineVariantDark,
    scrim = ScrimDark,
    surfaceBright = SurfaceBrightDark,
    surfaceContainer = SurfaceContainerDark,
    surfaceContainerHigh = SurfaceContainerHighDark,
    surfaceContainerHighest = SurfaceContainerHighestDark,
    surfaceContainerLow = SurfaceContainerLowDark,
    surfaceContainerLowest = SurfaceContainerLowestDark,
    surfaceDim = SurfaceDimDark,
    primaryFixed = PrimaryFixed,
    primaryFixedDim = PrimaryFixedDim,
    onPrimaryFixed = OnPrimaryFixed,
    onPrimaryFixedVariant = OnPrimaryFixedVariant,
    secondaryFixed = SecondaryFixed,
    secondaryFixedDim = SecondaryFixedDim,
    onSecondaryFixed = OnSecondaryFixed,
    onSecondaryFixedVariant = OnSecondaryFixedVariant,
    tertiaryFixed = TertiaryFixed,
    tertiaryFixedDim = TertiaryFixedDim,
    onTertiaryFixed = OnTertiaryFixed,
    onTertiaryFixedVariant = OnTertiaryFixedVariant
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