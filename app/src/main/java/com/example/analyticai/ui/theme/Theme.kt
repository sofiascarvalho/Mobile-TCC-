package com.example.analyticai.ui.theme

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
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// ---------------------
// Light Theme Colors
// ---------------------
val LightPrimary = Color(0xFF7D53F3)
val LightSecondary = Color(0xFFC6B2FF)

val LightBackground = Color(0xFFFFFFFF)
val LightSurface = Color(0xFFF9F9F9)

val LightTextPrimary = Color(0xFF5B5B5B)
val LightTextSecondary = Color(0xFF363636)

// ---------------------
// Dark Theme Colors
// ---------------------
val DarkPrimary = Color(0xFF7D53F3)
val DarkSecondary = Color(0xFFC6B2FF)

val DarkBackground = Color(0xFFF5F5F5)
val DarkSurface = Color(0xFF363636)

val DarkTextPrimary = Color(0xFFFFFFFF)
val DarkTextSecondary = Color(0xFFF9F9F9)

// -----------------------------
// EXTRA COLORS (não entram no ColorScheme)
// -----------------------------
val PurplePrimary = Color(0xFF7D53F3)
val PurpleLight = Color(0xFFC6B2FF)
val PurplePrimary25 = Color(0x407D53F3)

val BackgroundLightPink = Color(0xFFFCF5FF)
val DarkGray = Color(0xFF5B5B5B)
val BlackLight = Color(0xFF363636)
val WhiteSoft = Color(0xFFF9F9F9)
val GrayMedium = Color(0xFF4E4E4E)
val GrayLight = Color(0xFF979797)
val GrayVeryLight = Color(0xFFD9D9D9)
val BackgroundGeneral = Color(0xFFF9FAFB)
val GrayBlueLight = Color(0xFFE1E4E7)
val GrayPink = Color(0xFFC2ACAF)
val GrayExtraLight = Color(0xFFF3F4F6)
val GrayDarkMedium = Color(0xFF686868)

// -----------------------------
// ColorSchemes
// -----------------------------
val LightColors = lightColorScheme(
    primary = LightPrimary,
    onPrimary = Color.White,

    secondary = LightSecondary,
    onSecondary = Color.White,

    background = LightBackground,
    onBackground = LightTextPrimary,

    surface = LightSurface,
    onSurface = LightTextPrimary
)

val DarkColors = darkColorScheme(
    primary = DarkPrimary,
    onPrimary = Color.White,

    secondary = DarkSecondary,
    onSecondary = Color.Black,

    background = DarkBackground,
    onBackground = DarkTextPrimary,

    surface = DarkSurface,
    onSurface = DarkTextPrimary
)

// -----------------------------
// Shapes
// -----------------------------
val AppShapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(16.dp)
)

// -----------------------------
// FINAL THEME FUNCTION
// -----------------------------
@Composable
fun AnalyticAITheme(
    darkTheme: Boolean = false, // Forçar tema claro
    dynamicColor: Boolean = false, // Desativar cores dinâmicas
    content: @Composable () -> Unit
) {

// tipografia do app (Material3)
    val AppTypography = Typography(
        bodyLarge = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        ),
        titleLarge = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp
        )
    )
    val context = LocalContext.current

    val colorScheme =
        if (dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (darkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        } else {
            if (darkTheme) DarkColors else LightColors
        }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        shapes = AppShapes,
        content = content
    )
}
