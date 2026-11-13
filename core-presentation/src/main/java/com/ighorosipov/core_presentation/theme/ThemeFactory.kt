package com.ighorosipov.core_presentation.theme

object ThemeFactory {

    fun create(scale: ScreenScale): ThemeConfiguration {
        return ThemeConfiguration(
            typography = TypographyFactory.create(scale),
            shapes = ShapeFactory.create(scale),
            dimens = DimensFactory.create(scale)
        )
    }
}