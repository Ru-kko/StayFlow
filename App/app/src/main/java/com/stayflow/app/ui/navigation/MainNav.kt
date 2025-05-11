package com.stayflow.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import com.stayflow.app.ui.components.NavHost

@Composable
fun MainNavigation(initial: Screen) {
    val navController = remember { NavController(initial) }

    CompositionLocalProvider(LocalNavController provides navController) {
        NavHost(currentRoute = navController.current.value)
    }
}

