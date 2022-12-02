package com.galuhsaputri.core.di.module

import com.galuhsaputri.core.data.UserRepositoryImpl
import com.galuhsaputri.core.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

/**
 * Created by Aditiya Prayogo.
 */
@Module
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

}