package com.galuhsaputri.githubusers.di

import com.galuhsaputri.core.domain.usecase.UserUseCase
import com.galuhsaputri.core.domain.usecase.UserUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
//pada bagian ini digunakan untuk mengimport data atau libar
@Module
@InstallIn(ApplicationComponent::class)
abstract class AppModule {

    @Binds
    abstract fun provideUserUseCase(userUseCaseImpl: UserUseCaseImpl) : UserUseCase

}