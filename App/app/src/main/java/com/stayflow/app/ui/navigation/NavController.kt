package com.stayflow.app.ui.navigation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.staticCompositionLocalOf
import com.stayflow.app.ui.routes.AdminPanelRoute
import com.stayflow.app.ui.routes.ComposableRoute
import com.stayflow.app.ui.routes.HomeRoute
import com.stayflow.app.ui.routes.LoginRoute
import com.stayflow.app.ui.routes.RegisterRoute
import com.stayflow.app.ui.routes.RoomDetail
import com.stayflow.app.ui.routes.UserInformationRoute

class NavController(initialRoute: Screen) {
    private val _current = mutableStateOf(getComposableRoute(initialRoute))
    private val screen = mutableStateOf(initialRoute)
    val current: State<ComposableRoute> = _current

    private val _headerHeight = mutableStateOf(getComposableRoute(initialRoute).height)
    private val _logoBackGround = mutableStateOf(getComposableRoute(initialRoute).logoBackGround)



    private fun getComposableRoute(screen: Screen): ComposableRoute {
        return when (screen) {
            is Screen.Login -> LoginRoute()
            is Screen.Register -> RegisterRoute()
            is Screen.Home -> HomeRoute()
            is Screen.UserInfo -> UserInformationRoute()
            is Screen.AdminPanel -> AdminPanelRoute()
            is Screen.RoomDetail -> RoomDetail(screen.roomId)
        }
    }


    fun navigate(screen: Screen) {
        if (screen.route == this.screen.value.route) return

        val screenDestination = getComposableRoute(screen)
        this.screen.value = screen
        _current.value = screenDestination
        _headerHeight.value = screenDestination.height
        _logoBackGround.value = screenDestination.logoBackGround
    }
}

val LocalNavController = staticCompositionLocalOf<NavController> {
    error("NavController not provided")
}