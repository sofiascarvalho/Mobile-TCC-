package com.example.analyticai.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.analyticai.model.Recurso
import com.example.analyticai.screens.components.ConfirmarDownload
import com.example.analyticai.screens.components.RecursoCard

@Composable
fun RankingScreen(navegacao: NavHostController?) {

    var recursoSelecionado by remember { mutableStateOf<Recurso?>(null) }
    var abaSelecionada by remember { mutableStateOf("Mural") }
    var disciplinaSelecionada by remember { mutableStateOf("Todas as disciplinas") }
    var periodoSelecionado by remember { mutableStateOf("1º Semestre") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFDFDFD))
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        // Tabs (Mural / Atividades)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            AbaSelecionavel(
                titulo = "Mural",
                selecionado = abaSelecionada == "Mural",
                onClick = { abaSelecionada = "Mural" }
            )
            AbaSelecionavel(
                titulo = "Atividades",
                selecionado = abaSelecionada == "Atividades",
                onClick = { abaSelecionada = "Atividades" }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Filtros


        Spacer(modifier = Modifier.height(12.dp))


    }

    // Confirmação de download
    ConfirmarDownload(
        recurso = recursoSelecionado,
        onConfirmar = { recursoSelecionado = null },
        onCancelar = { recursoSelecionado = null }
    )
}