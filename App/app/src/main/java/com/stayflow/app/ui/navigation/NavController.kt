package com.stayflow.app.ui.navigation

import android.util.Log
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
    private val history = mutableListOf<HistoryEntry>(
        HistoryEntry.BasicEntry(LoginRoute())
    )
    var current = mutableStateOf(history.last().route)
        private set

    private val _headerHeight = mutableStateOf(current.value.height)
    private val _logoBackGround = mutableStateOf(current.value.logoBackGround)

    fun <T : ComposableRoute> navigate(clazz: Class<T>) {
        val route = routeMap[clazz]
        if (route == null) {
            Log.w("Router", "Please configure route ${clazz.simpleName} injection")
            return
        }

        history.add(HistoryEntry.BasicEntry(route))
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

        if (route is ConfigurableComposableRoute<*>) {
            (route as ConfigurableComposableRoute<T>).setProperties(props)
            history.add(
                HistoryEntry.ConfigurableEntry(
                    route,
                    route.propClas,
                    props
                )
            )
        }

        history.add(
            HistoryEntry.BasicEntry(route)
        )
        navigate(route)
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> applyConfigurable(entry: HistoryEntry.ConfigurableEntry<*>, clazz: Class<T>) {
        val route = entry.route as ConfigurableComposableRoute<T>
        val props = clazz.cast(entry.props)
        if (props != null) {
            route.setProperties(props)
        }
    }

    fun goBack() {
        if (history.isEmpty()) {
            return
        }

        val previousIndex = history.lastIndex - 1
        if (previousIndex < 0) return

        val previous = history[previousIndex]
        history.removeAt(history.lastIndex)

        if (previous is HistoryEntry.ConfigurableEntry<*> && previous.props != null) {
            applyConfigurable(previous, previous.clazz)
        }

        navigate(previous.route)
    }

    private fun navigate(route: ComposableRoute) {
        current.value = route
        _headerHeight.value = route.height
        _logoBackGround.value = route.logoBackGround
    }
}

private sealed class HistoryEntry(val route: ComposableRoute) {
    data class BasicEntry(val r: ComposableRoute) : HistoryEntry(r)
    data class ConfigurableEntry<T>(
        val r: ConfigurableComposableRoute<T>,
        val clazz: Class<T>,
        val props: T,
    ) : HistoryEntry(r)
}