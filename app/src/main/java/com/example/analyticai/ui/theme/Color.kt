package com.example.analyticai.ui.theme

import androidx.compose.ui.graphics.Color

// -----------------------------
// Paleta oficial - Tema Claro
// -----------------------------
val RoxoPrimarioLight = Color(0xFF7D53F3)
val RoxoSecundarioLight = Color(0xFF714ED3)
val RoxoTerciarioLight = Color(0xFFDED4FC)
val TextoBrancoLight = Color(0xFFFFFFFF)
val TextoEscuroLight = Color(0xFF181818)
val TextoPretoLight = Color(0xFF504F4F)
val TextoTransparenteLight = Color(0xFF9A9A9A)
val BackgroundClaroLight = Color(0xFFFFFFFF)
val BackgroundCinzaLight = Color(0xFFF3F3F3)
val BackgroundInputLight = Color(0xFFF9F9F9)
val BordaInputLight = Color(0xFFE1E1E1)
val TextoInputLight = Color(0xFF979797)
val ShadowBoxLight = Color(0xFFBEBEBE)
val TextoErroLight = Color(0xFFFF324E)
val TextoErroSecundarioLight = Color(0xFFD6253C)
val BackgroundSliderLight = Color(0xFFD4D4D4)

// -----------------------------
// Paleta oficial - Tema Escuro
// -----------------------------
val RoxoPrimarioDark = Color(0xFF7D53F3)
val RoxoSecundarioDark = Color(0xFF714ED3)
val RoxoTerciarioDark = Color(0xFFDED4FC)
val TextoBrancoDark = Color(0xFFF1F1F1)
val TextoEscuroDark = Color(0xFFFFFFFF)
val TextoPretoDark = Color(0xFFDADADA)
val TextoTransparenteDark = Color(0xD8DADADA)
val BackgroundClaroDark = Color(0xFF1D1D1D)
val BackgroundCinzaDark = Color(0xFF121212)
val BackgroundInputDark = Color(0xFF393939)
val BordaInputDark = Color(0xFF6C6C6C)
val TextoInputDark = Color(0xFFC2ACAF)
val ShadowBoxDark = Color(0x00000000)
val TextoErroDark = Color(0xFFD34258)
val TextoErroSecundarioDark = Color(0xFFD6253C)
val BackgroundSliderDark = Color(0xFF555555)

// -----------------------------
// Cores auxiliares organizadas para fácil acesso
// -----------------------------
object AnalyticAIExtendedColors {
    object Text {
        val White = TextoBrancoLight
        val Dark = TextoEscuroLight
        val Black = TextoPretoLight
        val Transparent = TextoTransparenteLight
        val Input = TextoInputLight
        val Error = TextoErroLight
        val ErrorSecondary = TextoErroSecundarioLight
        val WhiteDark = TextoBrancoDark
        val DarkDark = TextoEscuroDark
        val BlackDark = TextoPretoDark
        val TransparentDark = TextoTransparenteDark
        val InputDark = TextoInputDark
        val ErrorDark = TextoErroDark
        val ErrorSecondaryDark = TextoErroSecundarioDark
    }

    object Background {
        val Light = BackgroundClaroLight
        val Gray = BackgroundCinzaLight
        val Input = BackgroundInputLight
        val Slider = BackgroundSliderLight
        val LightDark = BackgroundClaroDark
        val GrayDark = BackgroundCinzaDark
        val InputDark = BackgroundInputDark
        val SliderDark = BackgroundSliderDark
    }

    object Border {
        val Input = BordaInputLight
        val InputDark = BordaInputDark
    }

    object Shadow {
        val Box = ShadowBoxLight
        val BoxDark = ShadowBoxDark
    }
}