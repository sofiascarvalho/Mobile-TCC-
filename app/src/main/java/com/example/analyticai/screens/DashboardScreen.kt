package com.example.analyticai.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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

val PurplePrimary = Color(0xFF673AB7)
val BackgroundColor = Color(0xFFF8F6FB)
val TextDark = Color(0xFF3C3C3C)
val TextGray = Color(0xFF6F6F6F)

@Composable
fun DashboardScreen(navegacao: NavHostController?) {
    val context = LocalContext.current
    val sharedPrefs = remember { SharedPreferencesManager(context) }
    val usuario: Usuario? = sharedPrefs.getUsuario()

    val userName = usuario?.nome ?: "Usuário"
    val userNivel = usuario?.nivel_usuario ?: "aluno"
    val userCredential = usuario?.credencial ?: "00000000"
    val turma = usuario?.turma?.turma ?: "—"
    val responsavel = if (userNivel.lowercase() == "aluno") "Nome do Responsável" else "—"
    val dataNascimento = usuario?.data_nascimento ?: "00/00/0000"
    val telefone = usuario?.telefone ?: "(00) 00000-0000"
    val email = usuario?.email ?: "exemplo@email.com"

    // ViewModel
    val viewModel: DesempenhoViewModel = viewModel()

    // Carrega dados do backend
    LaunchedEffect(Unit) {
        viewModel.loadPerformance()
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        // Header
        Text(
            text = "Dashboard de $userName",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark
        )

        if (viewModel.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = PurplePrimary)
            }
        } else {
            // **CORREÇÃO: Renomeado a variável temporária para ser consistente com a verificação de nulo.**
            val desempenho = viewModel.desempenho

            if (desempenho != null) { // Agora verifica-se o nome 'desempenho'

                // Valores para os cards
                val media = desempenho.media.toFloatOrNull() ?: 0f // Usando 'desempenho'
                val freqPercent = desempenho.frequencia.porcentagem_frequencia // Usando 'desempenho'
                    .replace("%", "")
                    .toFloatOrNull() ?: 0f
                val atividades = desempenho.atividades.map { BarData(it.atividade, it.nota.toFloat()) }

                // Card Informações Gerais
                DashboardCard(title = "Informações Gerais") {
                    InfoLine("Matrícula:", userCredential)
                    InfoLine("Nascimento:", dataNascimento)
                    if (userNivel.lowercase() == "aluno") {
                        InfoLine("Turma:", turma)
                        InfoLine("Responsável:", responsavel)
                    }
                    InfoLine("Contato:", telefone)
                    InfoLine("E-mail:", email)
                }

                // Card Desempenho
                PerformanceKpiCard(
                    score = media,
                    change = 0.0f, // Pode calcular variação se tiver histórico
                    modifier = Modifier.fillMaxWidth()
                )

                // Card Frequência
                FrequencyKpiCard(
                    presentPercentage = freqPercent,
                    modifier = Modifier.fillMaxWidth()
                )

                // Card Gráfico de Atividades
                SubjectPerformanceChartCard(
                    subject = desempenho.materia.materia,
                    data = atividades
                )

                // Insights
                Text(
                    text = "Relatórios e Insights por Matéria",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = TextDark,
                    modifier = Modifier.padding(top = 8.dp)
                )

                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    desempenho.atividades.forEach { atividade ->
                        InsightCard(
                            title = atividade.atividade,
                            date = "—",
                            description = atividade.descricao
                        )
                    }
                }

                // Relatórios para download
                Text(
                    text = "Relatórios para Download",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = TextDark,
                    modifier = Modifier.padding(top = 8.dp)
                )

                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    DownloadCardRefined("Relatório Completo de ${desempenho.materia.materia}", "18/05/2025")
                    DownloadCardRefined("Relatório de Frequência", "18/05/2025")
                    DownloadCardRefined("Relatório de Desempenho", "18/05/2025")
                    DownloadCardRefined("Observações e Análise de Desempenho", "18/05/2025")
                }

            } else {
                Text("Nenhum dado de desempenho encontrado.", color = TextGray)
            }
        }

        Spacer(Modifier.height(50.dp))
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

@Composable
fun InsightCard(title: String, date: String, description: String) {
    DashboardCard(title = title) {
        Text(text = date, fontSize = 12.sp, color = TextGray)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = description, fontSize = 14.sp, color = TextDark)
    }
}