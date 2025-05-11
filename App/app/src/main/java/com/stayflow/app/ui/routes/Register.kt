package com.stayflow.app.ui.routes

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.stayflow.app.ui.components.FilledButton
import com.stayflow.app.ui.components.HeaderText
import com.stayflow.app.ui.components.StayFlowInputField
import com.stayflow.app.ui.navigation.LocalNavController
import com.stayflow.app.ui.navigation.Screen
import com.stayflow.app.ui.theme.AppTheme
import com.stayflow.app.ui.theme.Typography

class RegisterRoute : ComposableRoute {
    override val height = mutableStateOf(70.dp)
    override val logoBackGround = true
    override val requireNav = false

    @Composable
    override fun HeaderContent(scope: BoxScope) {
        HeaderText("Register")
    }

    @Composable
    override fun BodyContent(scope: BoxScope) {
        val navController = LocalNavController.current

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        ) {
            StayFlowInputField(
                value = "",
                onValueChange = {},
                placeholder = "First Name",
                style = Typography.headlineSmall,
                bellowHint = true
            )
            Spacer(modifier = Modifier.padding(10.dp))
            StayFlowInputField(
                value = "",
                onValueChange = {},
                password = true,
                placeholder = "Last Name",
                style = Typography.headlineSmall,
                bellowHint = true
            )
            HorizontalDivider(
                color = AppTheme.palette.Crust,
                thickness = 2.dp,
                modifier = Modifier
                    .padding(vertical = 20.dp, horizontal = 20.dp)
            )
            StayFlowInputField(
                value = "",
                onValueChange = {},
                placeholder = "Email",
                style = Typography.headlineSmall,
                bellowHint = true
            )
            Spacer(modifier = Modifier.padding(10.dp))
            StayFlowInputField(
                value = "",
                onValueChange = {  },
                password = true,
                placeholder = "Password",
                style = Typography.headlineSmall,
                bellowHint = true
            )
            Spacer(modifier = Modifier.padding(20.dp))
            FilledButton(
                onClick = { navController.navigate(Screen.Home) },
                text = "Sign In",
                backgroundColor = AppTheme.palette.Flamingo,
                textColor = AppTheme.palette.Overlay0,
                textStyle = Typography.headlineSmall,
                modifier = Modifier.padding(horizontal = 15.dp)
            )
        }
    }
}
