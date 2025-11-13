package com.example.analyticai.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.analyticai.screens.components.*

@Composable
fun DashboardScreen(navegacao: NavHostController) {
    val PurplePrimary = Color(0xFF673AB7)
    val BackgroundColor = Color(0xFFF8F6FB)
    val TextDark = Color(0xFF3C3C3C)
    val TextGray = Color(0xFF6F6F6F)

    // Dados estáticos
    val userName = "Usuário Estático"
    val userNivel = "aluno"
    val userCredential = "12345678"
    val turma = "Turma A"
    val responsavel = if (userNivel.lowercase() == "aluno") "Nome do Responsável" else "—"
    val dataNascimento = "01/01/2010"
    val telefone = "(11) 91234-5678"
    val email = "usuario@exemplo.com"

    // Dados estáticos de desempenho
    val media = 7.8f
    val freqPercent = 95.0f
    val atividades = listOf(
        BarData("Matemática", 8.5f),
        BarData("Português", 7.0f),
        BarData("Ciências", 8.0f),
    )
    val descricaoAtividades = listOf(
        Triple("Matemática", "—", "Bom desempenho nas últimas avaliações."),
        Triple("Português", "—", "Precisa melhorar a interpretação de texto."),
        Triple("Ciências", "—", "Ótimo entendimento dos conceitos."),
    )
    val materia = "Matemática"

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

        // Informações Gerais
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

        // Desempenho
        PerformanceKpiCard(
            score = 7.5,
            change = 0.0,
            modifier = Modifier.fillMaxWidth()
        )

        // Frequência
        FrequencyKpiCard(
            presentPercentage = freqPercent,
            modifier = Modifier.fillMaxWidth()
        )

        // Gráfico de Atividades
        SubjectPerformanceChartCard(
            subject = materia,
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
            descricaoAtividades.forEach { (title, date, description) ->
//                InsightCard(
//                    title = title,
//                    date = date,
//                    description = description
//                )
            }
        }

        // Relatórios para download (dados estáticos)
        Text(
            text = "Relatórios para Download",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = TextDark,
            modifier = Modifier.padding(top = 8.dp)
        )

        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            DownloadCardRefined("Relatório Completo de $materia", "18/05/2025")
            DownloadCardRefined("Relatório de Frequência", "18/05/2025")
            DownloadCardRefined("Relatório de Desempenho", "18/05/2025")
            DownloadCardRefined("Observações e Análise de Desempenho", "18/05/2025")
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