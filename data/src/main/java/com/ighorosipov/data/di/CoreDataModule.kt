package com.ighorosipov.data.di

import com.ighorosipov.data.permission.AndroidPermissionGateway
import com.ighorosipov.domain.permission.PermissionGateway
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CoreDataModule {

    @Binds
    @Singleton
    abstract fun bindPermissionGateway(impl: AndroidPermissionGateway): PermissionGateway

}