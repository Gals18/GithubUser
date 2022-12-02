package com.galuhsaputri.favorite.di

import android.content.Context
import com.galuhsaputri.githubusers.di.FavoriteModuleDependencies
import com.galuhsaputri.favorite.ui.FavoriteUserActivity
import dagger.BindsInstance
import dagger.Component

/**
 * Created by Aditiya Prayogo.
 */
@Component(dependencies = [FavoriteModuleDependencies::class])
interface FavoriteComponent {

    fun inject(favoriteUserActivity: FavoriteUserActivity)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context) : Builder
        fun appDependencies(favoriteModuleDependencies: FavoriteModuleDependencies) : Builder
        fun build() : FavoriteComponent
    }

}