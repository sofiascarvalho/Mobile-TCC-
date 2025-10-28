package com.example.analyticai.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController

@Composable
fun DashboardScreen(navegacao: NavHostController?) {
    var disciplinaSelecionada by remember { mutableStateOf("Selecionar Diciplina") }
    var periodoSelecionado by remember { mutableStateOf("Selecionar Período") }

    Row()
    {

    }

}
