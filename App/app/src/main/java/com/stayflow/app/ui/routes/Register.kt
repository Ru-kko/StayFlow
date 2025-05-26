package com.stayflow.app.ui.routes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.stayflow.app.presentation.screens.RegisterViewModel
import com.stayflow.app.ui.components.BulledItem
import com.stayflow.app.ui.components.FilledButton
import com.stayflow.app.ui.components.HeaderText
import com.stayflow.app.ui.components.StayFlowInputField
import com.stayflow.app.ui.navigation.NavController
import com.stayflow.app.ui.theme.AppTheme
import com.stayflow.app.ui.theme.Typography
import javax.inject.Inject

class RegisterRoute @Inject constructor() : ComposableRoute {
    override val height = mutableStateOf(70.dp)
    override val logoBackGround = true
    override val requireNav = false

    @Composable
    override fun HeaderContent(scope: BoxScope) {
        HeaderText("Register")
    }

    @Composable
    override fun BodyContent(scope: BoxScope) {
        val navController = hiltViewModel<NavController>()
        val registerViewModel = hiltViewModel<RegisterViewModel>()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        ) {
            StayFlowInputField(
                value = registerViewModel.firstName,
                onValueChange = { registerViewModel.firstName = it },
                placeholder = "First Name",
                style = Typography.headlineSmall,
                bellowHint = true
            )
            Spacer(modifier = Modifier.padding(10.dp))
            StayFlowInputField(
                value = registerViewModel.lastName,
                onValueChange = { registerViewModel.lastName = it },
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
                value = registerViewModel.email,
                onValueChange = { registerViewModel.email = it },
                placeholder = "Email",
                style = Typography.headlineSmall,
                bellowHint = true
            )
            Spacer(modifier = Modifier.padding(10.dp))
            StayFlowInputField(
                value = registerViewModel.password,
                onValueChange = { registerViewModel.password = it },
                password = true,
                placeholder = "Password",
                style = Typography.headlineSmall,
                bellowHint = true
            )
            Spacer(modifier = Modifier.padding(20.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                BulledItem(
                    value = registerViewModel.acceptedTerms,
                    onToggle = {
                        registerViewModel.acceptedTerms = !registerViewModel.acceptedTerms
                    },
                    modifier = Modifier.padding(end = 10.dp)
                )
                Text(
                    text = "Accept the",
                    color = AppTheme.palette.Text,
                    style = Typography.bodyMedium
                )
                Text(
                    text = "terms of Use",
                    color = AppTheme.palette.Blue,
                    style = Typography.bodyMedium,
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .clickable { navController.navigate(TermsRoute::class.java) },
                )
            }
            Spacer(modifier = Modifier.padding(20.dp))
            FilledButton(
                onClick = { registerViewModel.register() },
                text = "Sign In",
                backgroundColor = AppTheme.palette.Flamingo,
                textColor = AppTheme.palette.Overlay0,
                textStyle = Typography.headlineSmall,
                modifier = Modifier.padding(horizontal = 15.dp)
            )
        }
    }
}
