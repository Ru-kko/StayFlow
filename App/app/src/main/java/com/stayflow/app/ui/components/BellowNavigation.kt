package com.stayflow.app.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.stayflow.app.R
import com.stayflow.app.ui.navigation.NavController
import com.stayflow.app.ui.routes.HomeRoute
import com.stayflow.app.ui.routes.SelfReservationsRoute
import com.stayflow.app.ui.routes.UserInformationRoute
import com.stayflow.app.ui.theme.AppTheme

@Composable
fun BellowNavigation(modifier: Modifier = Modifier) {
    val nav = hiltViewModel<NavController>()

    NavigationBarBackground(modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
                .fillMaxWidth()
        ) {
            RoundedIconButton(
                onClick = { nav.navigate(UserInformationRoute::class.java) },
                painter = painterResource(R.drawable.user),
                contentDescription = "Go to Account",
                size = 50.dp,
                padding = 4.dp,
            )
            RoundedIconButton(
                onClick = { nav.navigate(HomeRoute::class.java) },
                painter = painterResource(R.drawable.home),
                contentDescription = "Go to Home",
                size = 80.dp,
                padding = 8.dp
            )
            RoundedIconButton(
                onClick = { nav.navigate(SelfReservationsRoute::class.java) },
                painter = painterResource(R.drawable.cart),
                contentDescription = "Go to Cart",
                size = 50.dp,
                padding = 4.dp,
            )
        }
    }
}

@Composable
private fun NavigationBarBackground(
    modifier: Modifier = Modifier,
    bg: Color = AppTheme.palette.Crust,
    content: @Composable () -> Unit) {
    val insets = WindowInsets.navigationBars.asPaddingValues()
    val bottomPadding = insets.calculateBottomPadding()

    Box(modifier = modifier.fillMaxWidth()) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp + bottomPadding)
                .align(Alignment.BottomCenter)
        ) {
            val width = size.width
            val height = size.height + bottomPadding.toPx()

            val path = Path().apply {
                moveTo(0f, height * 0.4f)
                cubicTo(
                    width * 0.25f, 0f,
                    width * 0.75f, 0f,
                    width, height * 0.4f
                )
                lineTo(width, height)
                lineTo(0f, height)
                close()
            }

            drawPath(
                path = path,
                color = bg
            )
        }

        content()
    }
}