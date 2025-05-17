package com.stayflow.app.ui.routes

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextAlign.Companion
import androidx.compose.ui.unit.dp
import com.stayflow.app.domain.Role
import com.stayflow.app.domain.User
import com.stayflow.app.ui.components.HeaderText
import com.stayflow.app.ui.theme.AppTheme
import com.stayflow.app.ui.theme.Typography
import kotlinx.coroutines.delay
import java.util.UUID

class UserInformationRoute() : ComposableRoute {
    override val height = mutableStateOf(100.dp)
    private var user by mutableStateOf<User?>(null)
    private var isLoading by mutableStateOf(true)

    private suspend fun getUser() {
        delay(200)
        user = User(
            userId = UUID.randomUUID(),
            role = Role.USER,
            firstName = "Jonh",
            lastName = "Doe",
            email = "test@test.com",
        )
    }

    @Composable
    private fun LoadUser() {
        LaunchedEffect(user) {
            getUser()
            isLoading = false
        }
    }


    @Composable
    override fun HeaderContent(scope: BoxScope) {
        HeaderText(text = "Account")
    }

    @Composable
    override fun BodyContent(scope: BoxScope) {
        LoadUser()
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .padding(horizontal = 10.dp)
        ) {
            if (user != null) {
                BuildUserView(user!!)
            }
        }
    }

    @Composable
    private fun BuildUserView(user: User) {
        val color = when (user.role) {
            is Role.USER -> AppTheme.palette.Green
            is Role.ADMIN -> AppTheme.palette.Yellow
            is Role.OWNER -> AppTheme.palette.Sapphire
        }

        Text(
            text = user.role.name,
            color = color,
            style = Typography.titleLarge,
            textAlign = TextAlign.Center ,
            modifier = Modifier
                .padding(horizontal = 50.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(50))
                .border(3.dp, color, RoundedCornerShape(50))
                .padding(horizontal = 10.dp, vertical = 5.dp)
        )
    }
}