package com.ighorosipov.core_presentation.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimens(
    val borderSize: Dp = 1.dp,
    val extraSmall1: Dp = 0.dp,
    val extraSmall2: Dp = 0.dp,
    val small1: Dp = 0.dp,
    val small2: Dp = 0.dp,
    val small3: Dp = 0.dp,
    val medium1: Dp = 0.dp,
    val medium2: Dp = 0.dp,
    val medium3: Dp = 0.dp,
    val medium4: Dp = 0.dp,
    val large: Dp = 0.dp,
    val cellWidth: Dp = 0.dp,
    val cellHeight: Dp = 0.dp,
    val iconSmall1: Dp = 0.dp,
)

val BaseDimens = Dimens(
    borderSize = 1.dp,
    extraSmall1 = 4.dp,
    extraSmall2 = 8.dp,
    small1 = 10.dp,
    small2 = 14.dp,
    small3 = 20.dp,
    medium1 = 30.dp,
    medium2 = 36.dp,
    medium3 = 48.dp,
    medium4 = 65.dp,
    large = 80.dp,
    iconSmall1 = 10.dp
)

object DimensFactory {

    fun create(scale: ScreenScale): Dimens {
        return when (scale) {
            ScreenScale.Compact ->
                BaseDimens

            ScreenScale.CompactSmall -> BaseDimens.copy(
                small1 = 6.dp,
                small2 = 5.dp,
                small3 = 8.dp,
                iconSmall1 = 8.dp,
                medium1 = 15.dp,
                medium2 = 26.dp,
                medium3 = 30.dp,
                medium4 = 45.dp,
                large = 45.dp
            )

            ScreenScale.CompactMedium -> BaseDimens.copy(
                small1 = 8.dp,
                small2 = 12.dp,
                small3 = 17.dp,
                iconSmall1 = 10.dp,
                medium1 = 25.dp,
                medium2 = 30.dp,
                medium3 = 35.dp,
                medium4 = 55.dp,
                large = 65.dp
            )

            ScreenScale.Medium -> BaseDimens.copy(
                borderSize = 2.dp,
                extraSmall1 = 6.dp,
                extraSmall2 = 10.dp,
                small1 = 12.dp,
                small2 = 16.dp,
                small3 = 22.dp,
                iconSmall1 = 12.dp,
                medium1 = 32.dp,
                medium2 = 38.dp,
                medium3 = 50.dp,
                medium4 = 75.dp,
                large = 110.dp
            )

            ScreenScale.Expanded -> BaseDimens.copy(
                borderSize = 2.dp,
                extraSmall1 = 8.dp,
                extraSmall2 = 12.dp,
                small1 = 15.dp,
                small2 = 20.dp,
                small3 = 25.dp,
                iconSmall1 = 14.dp,
                medium1 = 35.dp,
                medium2 = 30.dp,
                medium3 = 56.dp,
                medium4 = 90.dp,
                large = 130.dp
            )
        }
    }
}