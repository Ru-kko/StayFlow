package com.stayflow.app.ui.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.stayflow.app.ui.components.CatppucinProvider
import com.stayflow.app.ui.components.NavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Startup: ComponentActivity() {
    private val navController: NavController by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition {
            false
        }
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color.Transparent.toArgb()),
            navigationBarStyle = SystemBarStyle.dark(Color.Transparent.toArgb())
        )

        setContent {
            CatppucinProvider {
                NavHost(navController.current.value)
            }
        }
    }
}