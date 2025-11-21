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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

// -----------------------------
// ColorSchemes com paleta oficial
// -----------------------------
private val LightColors = lightColorScheme(
    primary = RoxoPrimarioLight,
    onPrimary = TextoBrancoLight,
    primaryContainer = RoxoTerciarioLight,
    onPrimaryContainer = TextoEscuroLight,

    secondary = RoxoSecundarioLight,
    onSecondary = TextoBrancoLight,
    secondaryContainer = RoxoTerciarioLight,
    onSecondaryContainer = TextoEscuroLight,

    tertiary = RoxoPrimarioLight,
    onTertiary = TextoBrancoLight,
    tertiaryContainer = RoxoTerciarioLight,
    onTertiaryContainer = TextoEscuroLight,

    background = BackgroundCinzaLight,
    onBackground = TextoTransparenteLight,

    surface = BackgroundClaroLight,
    onSurface = TextoPretoLight,
    surfaceVariant = BackgroundInputLight,
    onSurfaceVariant = TextoPretoLight,

    error = TextoErroLight,
    onError = TextoBrancoLight,
    errorContainer = TextoErroSecundarioLight,
    onErrorContainer = TextoBrancoLight,

    outline = BordaInputLight,
    outlineVariant = BackgroundSliderLight,

    surfaceTint = RoxoPrimarioLight,
)

private val DarkColors = darkColorScheme(
    primary = RoxoPrimarioDark,
    onPrimary = TextoBrancoDark,
    primaryContainer = RoxoTerciarioDark,
    onPrimaryContainer = TextoEscuroDark,

    secondary = RoxoSecundarioDark,
    onSecondary = TextoBrancoDark,
    secondaryContainer = RoxoTerciarioDark,
    onSecondaryContainer = TextoEscuroDark,

    tertiary = RoxoPrimarioDark,
    onTertiary = TextoPretoDark,
    tertiaryContainer = RoxoTerciarioDark,
    onTertiaryContainer = TextoEscuroDark,

    background = BackgroundCinzaDark,
    onBackground = TextoTransparenteDark,

    surface = BackgroundClaroDark,
    onSurface = TextoBrancoLight,
    surfaceVariant = BackgroundInputDark,
    onSurfaceVariant = TextoPretoDark,

    error = TextoErroDark,
    onError = TextoEscuroDark,
    errorContainer = TextoErroSecundarioDark,
    onErrorContainer = TextoEscuroDark,

    outline = BordaInputDark,
    outlineVariant = BackgroundSliderDark,

    surfaceTint = RoxoPrimarioDark,
)

// -----------------------------
// Shapes
// -----------------------------
val AppShapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(16.dp)
)

@Composable
fun AnalyticAITheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current

    val colorScheme = if (dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
    } else {
        if (darkTheme) DarkColors else LightColors
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = AppShapes,
        content = content
    )
}
