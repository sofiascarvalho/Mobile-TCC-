package com.example.analyticai.ui.theme

import androidx.compose.ui.graphics.Color

val disciplinaColors = mapOf(
    "Filosofia" to Color(0xffA180AF),
    "Matemática" to Color(0xffBA8867),
    "Biologia" to Color(0xffC6CD9F),
    "História" to Color(0xffA180AF),
    "Geografia" to Color(0xffA180AF),
    "Português" to Color(0xffA180AF),
    "Inglês" to Color(0xffA180AF),
)

fun getDisciplinaColor(nome:String):Color{
    return disciplinaColors[nome] ?: Color(0xFFE0E0E0)
}