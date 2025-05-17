package com.stayflow.app.ui.routes

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.stayflow.app.R
import com.stayflow.app.ui.components.BigIconButton
import com.stayflow.app.ui.components.HeaderText
import com.stayflow.app.ui.theme.AppTheme

class AdminPanelRoute : ComposableRoute {
    override val height = mutableStateOf(80.dp)

    @Composable
    override fun HeaderContent(scope: BoxScope) {
        HeaderText(text = "Admin")
    }

    @Composable
    override fun BodyContent(scope: BoxScope) = with(scope) {
        BigIconButton(
            text = "Users",
            icon = painterResource(R.drawable.users),
            backgroundColor = AppTheme.palette.Red,
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.TopStart)
        )
        BigIconButton(
            text = "Add Home",
            backgroundColor = AppTheme.palette.Peach,
            icon = painterResource(R.drawable.home),
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.BottomEnd)
        )
    }
}