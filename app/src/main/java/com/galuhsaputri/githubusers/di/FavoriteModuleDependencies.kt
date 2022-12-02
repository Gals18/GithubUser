package com.galuhsaputri.githubusers.di

import com.galuhsaputri.core.domain.usecase.UserUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@EntryPoint
@InstallIn(ApplicationComponent::class)
interface FavoriteModuleDependencies {
    fun userUseCase() : UserUseCase
}