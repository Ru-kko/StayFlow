package com.stayflow.app.ui.routes

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.unit.Dp

interface ComposableRoute {
    @Composable
    fun HeaderContent(scope: BoxScope)
    @Composable
    fun BodyContent(scope: BoxScope)

    val height: MutableState<Dp>
    val logoBackGround: Boolean
        get() = false
    val requireNav: Boolean
        get() = true
}

interface ConfigurableComposableRoute<T> : ComposableRoute {
    fun setProperties(props: T)
}