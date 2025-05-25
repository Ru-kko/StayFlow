package com.stayflow.app.di

import com.stayflow.app.ui.routes.AdminPanelRoute
import com.stayflow.app.ui.routes.ComposableRoute
import com.stayflow.app.ui.routes.HomeRoute
import com.stayflow.app.ui.routes.LoginRoute
import com.stayflow.app.ui.routes.RegisterRoute
import com.stayflow.app.ui.routes.RoomDetail
import com.stayflow.app.ui.routes.SelfReservationsRoute
import com.stayflow.app.ui.routes.TermsRoute
import com.stayflow.app.ui.routes.UserInformationRoute
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@Module
@InstallIn(SingletonComponent::class)
interface Routes {
    @Binds
    @IntoMap
    @RouteKey(LoginRoute::class)
    fun bindLoginRoute(route: LoginRoute): ComposableRoute

    @Binds
    @IntoMap
    @RouteKey(RegisterRoute::class)
    fun bindRegisterRoute(route: RegisterRoute): ComposableRoute

    @Binds
    @IntoMap
    @RouteKey(RoomDetail::class)
    fun bindRoomDetailRoute(route: RoomDetail): ComposableRoute

    @Binds
    @IntoMap
    @RouteKey(HomeRoute::class)
    fun bindHomeRoute(route: HomeRoute): ComposableRoute

    @Binds
    @IntoMap
    @RouteKey(UserInformationRoute::class)
    fun bindUserInfoRoute(route: UserInformationRoute): ComposableRoute

    @Binds
    @IntoMap
    @RouteKey(AdminPanelRoute::class)
    fun bindAdminPanelRoute(route: AdminPanelRoute): ComposableRoute

    @Binds
    @IntoMap
    @RouteKey(SelfReservationsRoute::class)
    fun bindSelfReservationsRoute(route: SelfReservationsRoute): ComposableRoute

    @Binds
    @IntoMap
    @RouteKey(TermsRoute::class)
    fun bindTermsRoute(route: TermsRoute): ComposableRoute

}

@MapKey
annotation class RouteKey(val value: KClass<out ComposableRoute>)