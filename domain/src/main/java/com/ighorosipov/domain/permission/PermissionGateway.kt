package com.ighorosipov.domain.permission

interface PermissionGateway {
    fun hasNotificationPermission(): Boolean
}