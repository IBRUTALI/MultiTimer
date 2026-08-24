package com.ighorosipov.core_presentation.misc

import com.ighorosipov.core_presentation.navigation.Routes

interface BaseUIIntent {

    data class Navigate(
        val route: Routes
    ) : BaseUIIntent

    data class Snackbar(
        val text: UiText
    ) : BaseUIIntent

    data object NavigateBack: BaseUIIntent
}