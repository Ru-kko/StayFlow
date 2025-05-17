package com.stayflow.app.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.stayflow.app.ui.routes.ComposableRoute
import com.stayflow.app.ui.theme.AppTheme

@Composable
fun NavHost(currentRoute: ComposableRoute) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.palette.Surface1)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Header(
                height = currentRoute.height.value,
                logoBackGround = currentRoute.logoBackGround
            ) {
                currentRoute.HeaderContent(this)
            }

            AnimatedContent(
                targetState = currentRoute,
                label = "RouteChange",
                transitionSpec = {
                    fadeIn(tween(200)) togetherWith fadeOut(tween(200))
                },
            ) { route ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                ) {
                    route.BodyContent(this)
                }
            }
        }

        if (currentRoute.requireNav) {
            if (currentRoute.height.value != 0.dp)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .align(Alignment.BottomCenter)
                        .background(
                            brush = Brush.verticalGradient(
                                .0f to Color.Transparent,
                                .4f to AppTheme.palette.Surface1,
                                1.0f to AppTheme.palette.Surface1,
                            )
                        )
                )
            BellowNavigation(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
            )
        }
    }
}