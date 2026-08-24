package com.ighorosipov.core_presentation.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val BaseShapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(6.dp),
    large = RoundedCornerShape(8.dp),
    extraLarge = RoundedCornerShape(20.dp)
)

object ShapeFactory {

    fun create(scale: ScreenScale): Shapes {
        return when (scale) {
            ScreenScale.CompactSmall,
            ScreenScale.CompactMedium,
            ScreenScale.Compact -> BaseShapes

            ScreenScale.Medium -> BaseShapes.copy(
                medium = RoundedCornerShape(8.dp),
                large = RoundedCornerShape(12.dp)
            )

            ScreenScale.Expanded -> BaseShapes.copy(
                small = RoundedCornerShape(8.dp),
                medium = RoundedCornerShape(12.dp),
                large = RoundedCornerShape(16.dp),
                extraLarge = RoundedCornerShape(26.dp)
            )
        }
    }
}