package com.stayflow.app.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class CatppuccinPalette(
    val Crust: Color,
    val Mantle: Color,
    val Base: Color,
    val Surface0: Color,
    val Surface1: Color,
    val Surface2: Color,
    val Overlay0: Color,
    val Overlay1: Color,
    val Overlay2: Color,
    val Subtext0: Color,
    val Subtext1: Color,
    val Text: Color,
    val Lavender: Color,
    val Blue: Color,
    val Sapphire: Color,
    val Sky: Color,
    val Teal: Color,
    val Green: Color,
    val Yellow: Color,
    val Peach: Color,
    val Maroon: Color,
    val Red: Color,
    val Mauve: Color,
    val Pink: Color,
    val Flamingo: Color,
    val Rosewater: Color
)

data class CatppuccinScheme(
    val background: Color,
    val card: Color,
    val text: Color,
    val subtleText: Color,
    val link: Color,
    val navBar: Color,
    val contrastText: Color
)

data class CatppuccinTheme(
    val palette: CatppuccinPalette,
    val scheme: CatppuccinScheme
)

val LocalCatppuccinColors = staticCompositionLocalOf<CatppuccinTheme> {
    error("No default value provided")
}

object AppTheme {
    val palette: CatppuccinPalette
        @Composable get() = LocalCatppuccinColors.current.palette

    val colors: CatppuccinScheme
        @Composable get() = LocalCatppuccinColors.current.scheme
}