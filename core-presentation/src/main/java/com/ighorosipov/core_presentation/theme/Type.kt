package com.ighorosipov.core_presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.ighorosipov.utils.R

private val FontLight = FontFamily(
    Font(R.font.ttnorms_light)
)

private val FontMedium = FontFamily(
    Font(R.font.ttnorms_medium)
)

private val FontBold = FontFamily(
    Font(R.font.ttnorms_bold)
)

val BaseTypography = Typography(
    headlineLarge = TextStyle(
        fontFamily = FontMedium,
        fontSize = 24.sp,
        fontFeatureSettings = "liga 0"
    ),
    headlineMedium = TextStyle(
        fontFamily = FontBold,
        fontSize = 22.sp,
        fontFeatureSettings = "liga 0"
    ),
    headlineSmall = TextStyle(
        fontFamily = FontBold,
        fontSize = 16.sp,
        fontFeatureSettings = "liga 0"
    ),
    titleLarge = TextStyle(
        fontFamily = FontMedium,
        fontSize = 20.sp,
        fontFeatureSettings = "liga 0"
    ),
    titleMedium = TextStyle(
        fontFamily = FontLight,
        fontSize = 18.sp,
        fontFeatureSettings = "liga 0"
    ),
    titleSmall = TextStyle(
        fontFamily = FontLight,
        fontSize = 16.sp,
        fontFeatureSettings = "liga 0"
    ),
    bodyLarge = TextStyle(
        fontFamily = FontBold,
        fontSize = 15.sp,
        fontFeatureSettings = "liga 0"
    ),
    bodyMedium = TextStyle(
        fontFamily = FontBold,
        fontSize = 14.sp,
        fontFeatureSettings = "liga 0"
    ),
    bodySmall = TextStyle(
        fontFamily = FontLight,
        fontSize = 13.sp,
        fontFeatureSettings = "liga 0"
    ),
    labelLarge = TextStyle(
        fontFamily = FontLight,
        fontSize = 15.sp,
        fontFeatureSettings = "liga 0"
    ),
    labelMedium = TextStyle(
        fontFamily = FontLight,
        fontSize = 14.sp,
        fontFeatureSettings = "liga 0"
    ),
    labelSmall = TextStyle(
        fontFamily = FontLight,
        fontSize = 12.sp,
        fontFeatureSettings = "liga 0"
    )
)

object TypographyFactory {

    fun create(scale: ScreenScale): Typography {

        val base = BaseTypography

        return when (scale) {
            ScreenScale.CompactSmall -> base.copy(
                bodySmall = base.bodySmall.copy(
                    fontSize = 12.sp
                )
            )

            ScreenScale.CompactMedium -> base.copy(
                bodySmall = base.bodySmall.copy(
                    fontSize = 13.sp
                )
            )

            ScreenScale.Compact -> base

            ScreenScale.Medium -> base.copy(
                headlineLarge = base.headlineLarge.copy(
                    fontFamily = FontBold,
                    fontSize = 26.sp
                ),
                headlineSmall = base.headlineSmall.copy(
                    fontFamily = FontLight,
                    fontSize = 22.sp
                ),
                titleSmall = base.titleSmall.copy(
                    fontFamily = FontBold
                )
            )

            ScreenScale.Expanded -> base.copy(
                headlineLarge = base.headlineLarge.copy(
                    fontFamily = FontBold,
                    fontSize = 30.sp
                ),
                headlineMedium = base.headlineMedium.copy(
                    fontSize = 26.sp
                ),
                titleLarge = base.titleLarge.copy(
                    fontSize = 24.sp
                ),
                bodyMedium = base.bodyMedium.copy(
                    fontSize = 18.sp
                )
            )
        }
    }
}