package com.example.analyticai.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

// --- Light Mode ---
val LightPrimary = Color(0xFF7D53F3)
val LightSecondary = Color(0xFFC6B2FF)
val LightBackground = Color(0xFFF9F9F9)
val LightSurface = Color(0xFFFFFFFF)
val LightCardBackground = Color(0xFFFCF5FF)
val LightTextPrimary = Color(0xFF5B5B5B)
val LightTextSecondary = Color(0xFF363636)

// --- Dark Mode ---
val DarkPrimary = Color(0xFF7D53F3)
val DarkSecondary = Color(0xFFC6B2FF)
val DarkBackground = Color(0xFF000000)
val DarkSurface = Color(0xFF363636)
val DarkCardBackground = Color(0xFF5B5B5B)
val DarkTextPrimary = Color(0xFFFFFFFF)
val DarkTextSecondary = Color(0xFFF9F9F9)

// --- ColorSchemes ---
val LightColors = lightColorScheme(
    primary = LightPrimary,
    secondary = LightSecondary,
    background = LightBackground,
    surface = LightSurface,
    onPrimary = LightSurface,
    onSecondary = LightTextPrimary,
    onBackground = LightTextPrimary,
    onSurface = LightTextPrimary
)

val DarkColors = darkColorScheme(
    primary = DarkPrimary,
    secondary = DarkSecondary,
    background = DarkBackground,
    surface = DarkSurface,
    onPrimary = DarkSurface,
    onSecondary = DarkTextPrimary,
    onBackground = DarkTextPrimary,
    onSurface = DarkTextPrimary
)


val Shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(16.dp)
)

@Composable
fun AnalyticAITheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = Typography, // seu Typography existente
        shapes = Shapes,         // seu Shapes existente
        content = content
    )
}
