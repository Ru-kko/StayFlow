package com.stayflow.app.di

import com.stayflow.app.ui.navigation.NavController
import com.stayflow.app.ui.routes.ComposableRoute
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NavigationModule {
    @Provides
    @Singleton
    fun provideNavController(
        routeMap: Map<Class<out ComposableRoute>, @JvmSuppressWildcards ComposableRoute>
    ): NavController {
        return NavController(routeMap)
    }
}