package com.example.analyticai.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.analyticai.data.SharedPreferencesManager
import com.example.analyticai.model.Login.Usuario
import com.example.analyticai.screens.components.*
import com.example.analyticai.viewmodel.DesempenhoViewModel

@Composable
fun DashboardScreen(navegacao: NavHostController) {
    val context = LocalContext.current
    val sharedPrefs = remember { SharedPreferencesManager(context) }
    val usuario: Usuario? = sharedPrefs.getUsuario()
    val userName = usuario?.nome ?: "Usuário"

    val usuarioId = sharedPrefs.getUsuario()?.id_usuario ?: ""
    LaunchedEffect(usuarioId) {
        if (usuarioId.isNotEmpty()) {
            viewModel.loadPerformance(usuarioId)
        }
    }

    val viewModel: DesempenhoViewModel = viewModel()

    LaunchedEffect(Unit) {
        viewModel.loadPerformance(alunoId = )
    }

    val desempenho = viewModel.desempenho // <-- Aqui ficam os dados reais da API
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Text(
            text = "Dashboard de $userName",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark
        )

        desempenho?.let { dashboard ->

            // Mostra só a primeira matéria por enquanto (podemos depois iterar todas)
            val primeiro = dashboard.desempenho.firstOrNull()

            if (primeiro != null) {

                // --- Desempenho ---
                PerformanceKpiCard(
                    score = primeiro.atividades.map { it.nota }.average(),
                    change = 0.0, // TODO: se a API trouxer comparação, colocar aqui
                    modifier = Modifier.fillMaxWidth()
                )

                // --- Frequência ---
                FrequencyKpiCard(
                    presentPercentage = primeiro.frequencia.porcentagem_frequencia.toFloat(),
                    modifier = Modifier.fillMaxWidth()
                )

                // --- Gráfico de Barras das Atividades ---
                val barData = primeiro.atividades.map {
                    BarData(it.categoria, it.nota.toFloat())
                }
                SubjectPerformanceChartCard(
                    subject = primeiro.materia.materia,
                    data = barData
                )

                // --- Relatórios para Download (mantém estáticos por enquanto) ---
                Text(
                    text = "Relatórios para Download",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = TextDark,
                    modifier = Modifier.padding(top = 8.dp)
                )

                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    DownloadCardRefined("Relatório Completo de ${primeiro.materia.materia}", "18/05/2025")
                    DownloadCardRefined("Relatório de Frequência", "18/05/2025")
                    DownloadCardRefined("Relatório de Desempenho", "18/05/2025")
                    DownloadCardRefined("Observações e Análise de Desempenho", "18/05/2025")
                }
            } else {
                Text("Nenhum desempenho encontrado.")
            }

        } ?: Text("Carregando dados...")
    }
}
@Composable
fun InfoLine(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontSize = 14.sp, color = TextGray)
        Text(text = value, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = TextDark)
    }
    Spacer(modifier = Modifier.height(6.dp))
}