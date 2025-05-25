package com.stayflow.app.ui.navigation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.stayflow.app.ui.routes.ComposableRoute
import com.stayflow.app.ui.routes.ConfigurableComposableRoute
import com.stayflow.app.ui.routes.LoginRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NavController @Inject constructor(
    private val routeMap: Map<Class<out ComposableRoute>, ComposableRoute>
) : ViewModel() {
    private val _current = mutableStateOf(routeMap[LoginRoute::class.java]!!)
    val current: State<ComposableRoute> = _current

    private val _headerHeight = mutableStateOf(current.value.height)
    private val _logoBackGround = mutableStateOf(current.value.logoBackGround)

    fun <T : ComposableRoute> navigate(clazz: Class<T>) {
        val route = routeMap[clazz]
        if (route == null) {
            Log.w("Router", "Please configure route ${clazz.simpleName} injection")
            return
        }

        navigate(route)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T, R : ConfigurableComposableRoute<T>> navigate(
        clazz: Class<out R>,
        props: T
    ) {
        if  (routeMap[clazz] == current.value) return

        val route = routeMap[clazz]
        if (route == null) {
            Log.w("Router", "Please configure route ${clazz.simpleName} injection")
            return
        }

        if (route is ConfigurableComposableRoute<*>)
            (route as ConfigurableComposableRoute<T>).setProperties(props)

        navigate(route)
    }

    private fun navigate(route: ComposableRoute) {
        _current.value = route

        _headerHeight.value = route.height
        _logoBackGround.value = route.logoBackGround
    }
}
