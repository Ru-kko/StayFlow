package com.stayflow.app.ui.theme

import androidx.compose.ui.graphics.Color

val Mocha = CatppuccinPalette(
    Crust = Color(0xFF11111B),
    Mantle = Color(0xFF181825),
    Base = Color(0xFF1E1E2E),
    Surface0 = Color(0xFF313244),
    Surface1 = Color(0xFF45475A),
    Surface2 = Color(0xFF585B70),
    Overlay0 = Color(0xFF6C7086),
    Overlay1 = Color(0xFF7F849C),
    Overlay2 = Color(0xFF9399B2),
    Subtext0 = Color(0xFFA6ADC8),
    Subtext1 = Color(0xFFBAC2DE),
    Text = Color(0xFFCDD6F4),
    Lavender = Color(0xFFB4BEFE),
    Blue = Color(0xFF89B4FA),
    Sapphire = Color(0xFF74C7EC),
    Sky = Color(0xFF89DCEB),
    Teal = Color(0xFF94E2D5),
    Green = Color(0xFFA6E3A1),
    Yellow = Color(0xFFF9E2AF),
    Peach = Color(0xFFFAB387),
    Maroon = Color(0xFFEBA0AC),
    Red = Color(0xFFF38BA8),
    Mauve = Color(0xFFCBA6F7),
    Pink = Color(0xFFF5C2E7),
    Flamingo = Color(0xFFF2CDCD),
    Rosewater = Color(0xFFF5E0DC),
)

val MochaScheme = CatppuccinScheme(
    background = Mocha.Surface1,
    card = Mocha.Surface0,
    text = Mocha.Text,
    subtleText = Mocha.Subtext1,
    link = Mocha.Sky,
    navBar = Mocha.Crust,
    contrastText = Mocha.Overlay0
)