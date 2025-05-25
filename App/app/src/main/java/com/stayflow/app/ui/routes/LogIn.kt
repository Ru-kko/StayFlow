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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.stayflow.app.R
import com.stayflow.app.presentation.screens.LoginViewModel
import com.stayflow.app.ui.components.FilledButton
import com.stayflow.app.ui.components.HeaderText
import com.stayflow.app.ui.components.StayFlowInputField
import com.stayflow.app.ui.navigation.NavController
import com.stayflow.app.ui.theme.AppTheme
import com.stayflow.app.ui.theme.Typography
import com.stayflow.app.util.FetchState
import javax.inject.Inject

class LoginRoute @Inject constructor() : ComposableRoute {
    override val height = mutableStateOf(100.dp)
    override val logoBackGround = true
    override val requireNav = false

    @Composable
    override fun HeaderContent(scope: BoxScope) = with(scope) {
        HeaderText("Sing In", modifier = Modifier.align(Alignment.CenterStart))
    }

    @Composable
    override fun BodyContent(scope: BoxScope) = with(scope) {
        val navController = hiltViewModel<NavController>()
        val loginViewModel = hiltViewModel<LoginViewModel>()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 5.dp)
        ) {
            StayFlowInputField(
                value = loginViewModel.email,
                onValueChange = { loginViewModel.onEmailChange(it) },
                placeholder = "Email",
                leadingIcon = painterResource(R.drawable.user)
            )
            Spacer(modifier = Modifier.padding(20.dp))
            StayFlowInputField(
                value = loginViewModel.password,
                onValueChange = { loginViewModel.onPasswordChange(it) },
                keyboardType = KeyboardType.Password,
                password = true,
                placeholder = "Password",
                leadingIcon = painterResource(R.drawable.key)
            )
            Spacer(modifier = Modifier.padding(20.dp))
            FilledButton(
                onClick = { loginViewModel.login() },
                text = "Sign In",
                backgroundColor = when (loginViewModel.state) {
                    FetchState.AWAITING -> AppTheme.palette.Rosewater
                    FetchState.LOADING -> AppTheme.palette.Overlay0
                    FetchState.ERROR -> AppTheme.palette.Red
                },
                enabled = loginViewModel.state == FetchState.AWAITING,
                textColor =  when(loginViewModel.state) {
                    FetchState.AWAITING, FetchState.LOADING -> AppTheme.palette.Overlay0
                    FetchState.ERROR -> AppTheme.palette.Text
                },
                textStyle = Typography.headlineSmall,
                modifier = Modifier.padding(horizontal = 15.dp)
            )
            Spacer(modifier = Modifier.padding(5.dp))
            Text(text = "or", style = Typography.bodySmall, color = AppTheme.palette.Text)
            Spacer(modifier = Modifier.padding(5.dp))
            TextButton(
                onClick = { navController.navigate(RegisterRoute::class.java) },
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
