package com.stayflow.app.ui.routes

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.stayflow.app.R
import com.stayflow.app.ui.components.FilledButton
import com.stayflow.app.ui.components.HeaderText
import com.stayflow.app.ui.components.StayFlowInputField
import com.stayflow.app.ui.navigation.LocalNavController
import com.stayflow.app.ui.navigation.Screen
import com.stayflow.app.ui.theme.AppTheme
import com.stayflow.app.ui.theme.Typography

class LoginRoute : ComposableRoute {
    override val height = mutableStateOf(100.dp)
    override val logoBackGround = true
    override val requireNav = false

    @Composable
    override fun HeaderContent(scope: BoxScope) = with(scope) {
        HeaderText("Sing In", modifier = Modifier.align(Alignment.CenterStart))
    }

    @Composable
    override fun BodyContent(scope: BoxScope) = with(scope) {
        val navController = LocalNavController.current

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 5.dp)
        ) {
            StayFlowInputField(
                value = "",
                onValueChange = {},
                placeholder = "Email",
                leadingIcon = painterResource(R.drawable.user)
            )
            Spacer(modifier = Modifier.padding(20.dp))
            StayFlowInputField(
                value = "",
                onValueChange = {},
                password = true,
                placeholder = "Password",
                leadingIcon = painterResource(R.drawable.key)
            )
            Spacer(modifier = Modifier.padding(20.dp))
            FilledButton(
                onClick = { navController.navigate(Screen.Home) },
                text = "Sign In",
                backgroundColor = AppTheme.palette.Rosewater,
                textColor = AppTheme.palette.Overlay0,
                textStyle = Typography.headlineSmall,
                modifier = Modifier.padding(horizontal = 15.dp)
                )
            Spacer(modifier = Modifier.padding(5.dp))
            Text(text = "or", style = Typography.bodySmall, color = AppTheme.palette.Text)
            Spacer(modifier = Modifier.padding(5.dp))
            TextButton(
                onClick = { navController.navigate(Screen.Register) },
                modifier = Modifier.padding(0.dp)
            ) {
                Text(
                    text = "Register",
                    style = Typography.bodyMedium,
                    color = AppTheme.palette.Sky
                )
            }
        }
    }
}
