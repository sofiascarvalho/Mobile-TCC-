package com.example.analyticai.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.analyticai.data.SharedPreferencesManager
import com.example.analyticai.model.Login.Usuario
import com.example.analyticai.screens.components.FilterDropdown
import com.example.analyticai.ui.theme.DarkGray

@Composable
fun RankingScreen(navegacao: NavHostController?) {
    val context = LocalContext.current
    val sharedPrefs = remember { SharedPreferencesManager(context) }
    val usuario: Usuario? = sharedPrefs.getUsuario()

    val userName = usuario?.nome ?: "Usuário"

    val disciplinas = remember { listOf("Todas as Disciplinas", "Matemática", "Português", "Ciências", "História") }
    val periodos = remember { listOf("1º Semestre - 2025", "2º Semestre - 2025", "Ano Letivo", "Geral") }

    var selectedDisciplina by remember { mutableStateOf(disciplinas.first()) }
    var selectedPeriodo by remember { mutableStateOf(periodos.first()) }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .verticalScroll(scrollState)
            .padding(vertical = 16.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "Ranking de $userName",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            FilterDropdown(
                label = "Disciplina",
                selectedValue = selectedDisciplina,
                options = disciplinas,
                onSelect = { selectedDisciplina = it },
                modifier = Modifier.weight(1f)
            )
            FilterDropdown(
                label = "Período",
                selectedValue = selectedPeriodo,
                options = periodos,
                onSelect = { selectedPeriodo = it },
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "RANKING",
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = DarkGray
            )
            Text(
                text = "MÉDIA",
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = DarkGray
            )
            Text(
                text = "NOME DO ALUNO",
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = DarkGray
            )
        }
    }
}

@Preview
@Composable
private fun RankingScreenPreview() {
    RankingScreen(null)
}
