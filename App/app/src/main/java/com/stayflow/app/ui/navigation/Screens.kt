package com.stayflow.app.ui.navigation

import java.util.UUID

sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object Register : Screen("register")
    data object Home : Screen("home")
    data object UserInfo : Screen("user-info")
    data class RoomDetail(val roomId: UUID) : Screen("room/$roomId")
}