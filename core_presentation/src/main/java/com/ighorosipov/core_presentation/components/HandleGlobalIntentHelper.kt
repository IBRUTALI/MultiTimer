package com.ighorosipov.core_presentation.components

import android.content.Context
import androidx.compose.material3.SnackbarDuration
import com.ighorosipov.core_presentation.misc.BaseUIIntent
import com.ighorosipov.core_presentation.navigation.navigateTo
import com.ighorosipov.core_presentation.navigation.popUp

fun handleGlobalIntent(
    appState: MainAppState,
    context: Context,
    showSnackbar: (
        String,
        SnackbarDuration,
        String?,
        actionPerformed: () -> Unit,
    ) -> Unit,
    intent: BaseUIIntent
) {
    when (intent) {
        is BaseUIIntent.Navigate -> appState.navigateTo(intent.route)
        is BaseUIIntent.NavigateBack -> appState.popUp()
        is BaseUIIntent.Snackbar -> {
            showSnackbar(
                intent.text.asString(context),
                SnackbarDuration.Short,
                null,
                {}
            )
        }
    }
}