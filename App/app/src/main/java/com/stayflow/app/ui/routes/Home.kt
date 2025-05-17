package com.stayflow.app.ui.routes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.stayflow.app.R
import com.stayflow.app.ui.components.FilledButton
import com.stayflow.app.ui.components.HeaderText
import com.stayflow.app.ui.components.Option
import com.stayflow.app.ui.components.RoomCard
import com.stayflow.app.ui.components.StayflowDropdown
import com.stayflow.app.ui.navigation.LocalNavController
import com.stayflow.app.ui.navigation.Screen
import com.stayflow.app.ui.theme.AppTheme
import java.util.UUID

const val lorem = "Lorem ipsum dolor sit amet consectetur adipiscing elit dapibus, eleifend" +
        "praesent lobortis taciti porta proin quis, feugiat est volutpat curae vulputate" +
        "ridiculus maecenas. Fringilla cras malesuada metus aliquam vestibulum hac aliquet " +
        "natoque iaculis sed tortor pulvinar fermentum nostra imperdiet proin. Orci fames vivamus" +
        " integer est nibh dapibus luctus sapien vehicula, vulputate accumsan aliquet eu " +
        "pulvinar dignissim volutpat velit, penatibus montes sagittis eget class rutrum pretium ante."

class HomeRoute : ComposableRoute {
    override val height: MutableState<Dp> = mutableStateOf(120.dp)
    override val requireNav: Boolean = true
    override val logoBackGround: Boolean = false

    @Composable
    override fun HeaderContent(scope: BoxScope) = with(scope) {
        val nav = LocalNavController.current
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(vertical = 10.dp, horizontal = 10.dp)
                .fillMaxWidth()
        ) {
            HeaderText(
                text = "Hi, Jhon Doe",
            )
            Spacer(Modifier.padding(horizontal = 5.dp))
            FilledButton(
                onClick = { nav.navigate(Screen.AdminPanel) },
                text = "Admin",
                backgroundColor = AppTheme.palette.Rosewater,
                drawableLeft = painterResource(R.drawable.admin),
                textColor = AppTheme.palette.Surface0,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
            )
        }
    }

    @Composable
    override fun BodyContent(scope: BoxScope) {
        val nav = LocalNavController.current
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            StayflowDropdown(
                options = listOf(
                    Option("Option 1", "Option 1"),
                    Option("Option 2", "Option 2"),
                ),
                onItemSelected = { }
            )
            Spacer(
                modifier = Modifier
                    .padding(vertical = 10.dp)
            )
            RoomCard(
                onClick = { nav.navigate(Screen.RoomDetail(UUID.randomUUID())) },
                name = "Hello",
                description = lorem,
                location = "Bogota, Colombia"
            )
            RoomCard(
                onClick = { nav.navigate(Screen.RoomDetail(UUID.randomUUID())) },
                name = "Hello",
                description = lorem,
                location = "Bogota, Colombia"
            )
            RoomCard(
                onClick = { nav.navigate(Screen.RoomDetail(UUID.randomUUID())) },
                name = "Hello",
                description = lorem,
                location = "Bogota, Colombia",
                image = "https://images.photowall.com/products/52990/wise-lion.jpg?h=699&q=85"
            )
        }
    }

}