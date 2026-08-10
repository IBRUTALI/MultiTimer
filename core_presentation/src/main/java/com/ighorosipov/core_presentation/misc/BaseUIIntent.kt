package com.ighorosipov.core_presentation.misc

import com.ighorosipov.core_presentation.navigation.Routes
import kotlin.reflect.KClass

interface BaseUIIntent {

    data class Navigate(
        val route: Routes
    ) : BaseUIIntent

    data class Snackbar(
        val text: UiText,
        val routesToShow: List<KClass<out Routes>>? = null
    ) : BaseUIIntent
}