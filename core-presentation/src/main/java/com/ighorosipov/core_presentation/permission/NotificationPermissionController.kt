package com.ighorosipov.core_presentation.permission

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun rememberNotificationPermissionController(
    onGranted: () -> Unit,
    onDenied: () -> Unit = {}
): NotificationPermissionController {

    var pendingAction by remember { mutableStateOf<(() -> Unit)?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            pendingAction?.invoke()
            onGranted()
        } else {
            pendingAction?.invoke()
            onDenied()
        }
        pendingAction = null
    }

    return remember(launcher) {
        NotificationPermissionController(
            requestPermission = { action ->
                pendingAction = action
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
                } else {
                    action.invoke()
                }
            }
        )
    }
}

class NotificationPermissionController(
    val requestPermission: (action: () -> Unit) -> Unit
)