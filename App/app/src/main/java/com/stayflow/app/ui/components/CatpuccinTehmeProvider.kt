package com.stayflow.app.ui.components

import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.stayflow.app.ui.theme.CatppuccinTheme
import com.stayflow.app.ui.theme.LocalCatppuccinColors
import com.stayflow.app.ui.theme.Mocha
import com.stayflow.app.ui.theme.MochaScheme

@Composable
fun CatppucinProvider(content: @Composable () -> Unit) {
    val palette = Mocha
    val scheme = MochaScheme

    CompositionLocalProvider(
        LocalCatppuccinColors provides CatppuccinTheme(palette, scheme),
        LocalContentColor provides scheme.text,
        content = content
    )
}