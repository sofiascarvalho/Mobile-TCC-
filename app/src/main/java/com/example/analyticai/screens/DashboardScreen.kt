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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.analyticai.data.SharedPreferencesManager
import com.example.analyticai.model.Login.Usuario
import com.example.analyticai.screens.components.*

val PurplePrimary = Color(0xFF673AB7)
val BackgroundColor = Color(0xFFF8F6FB)
val TextDark = Color(0xFF3C3C3C)
val TextGray = Color(0xFF6F6F6F)

@Composable
fun DashboardScreen(navegacao: NavHostController?) {
    val context = LocalContext.current
    val sharedPrefs = remember { SharedPreferencesManager(context) }

    // Recupera usuário salvo no SharedPreferences
    val usuario: Usuario? = sharedPrefs.getUsuario()

    val userName = usuario?.nome ?: "Usuário"
    val userNivel = usuario?.nivel_usuario ?: "aluno"
    val userCredential = usuario?.credencial ?: "00000000"
    val turma = usuario?.turma?.turma ?: "—"
    val responsavel = if (userNivel.lowercase() == "aluno") "Nome do Responsável" else "—"
    val dataNascimento = usuario?.data_nascimento ?: "00/00/0000"
    val telefone = usuario?.telefone ?: "(00) 00000-0000"
    val email = usuario?.email ?: "exemplo@email.com"


    // Dados mockados para outros cards
    val performanceScore = 9.8
    val scoreChange = 0.3
    val presencePercentage = 90f
    val mathPerformanceData = listOf(
        BarData("Atividade", 8.0f),
        BarData("Prova", 9.2f),
        BarData("Seminário", 7.3f),
        BarData("Prova", 10.0f)
    )

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
            .padding(horizontal = 16.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Header com nome do usuário
        Text(
            text = "Dashboard de $userName",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark
        )

        // Filtros
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

        // Card Informações Gerais com dados reais
        DashboardCard(title = "Informações Gerais") {
            InfoLine("Nome:", userName)
            InfoLine("Matrícula:", userCredential)
            InfoLine("Nascimento:", dataNascimento)
            if (userNivel.lowercase() == "aluno") {
                InfoLine("Turma:", turma)
                InfoLine("Responsável:", responsavel)
            }
            InfoLine("Contato:", telefone)
            InfoLine("E-mail:", email)
            InfoLine("Nível:", userNivel)
        }

        // Card Desempenho
        PerformanceKpiCard(
            score = performanceScore,
            change = scoreChange,
            modifier = Modifier.fillMaxWidth()
        )

        // Card Frequência
        FrequencyKpiCard(
            presentPercentage = presencePercentage,
            modifier = Modifier.fillMaxWidth()
        )

        // Card Gráfico
        SubjectPerformanceChartCard(
            subject = selectedDisciplina,
            data = mathPerformanceData
        )

        // Relatórios e Insights
        Text(
            text = "Relatórios e Insights por Matéria",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = TextDark,
            modifier = Modifier.padding(top = 8.dp)
        )

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            InsightCard(
                title = "Avaliação de Matemática",
                date = "01/06/2025",
                description = "Com excelente compreensão de álgebra e geometria. As notas de prova indicam grande domínio do conteúdo."
            )
            InsightCard(
                title = "Seminário de Matemática",
                date = "18/06/2025",
                description = "As notas de prova indicam grande domínio do conteúdo e bom entendimento sobre o conteúdo aprendido."
            )
        }

        // Relatórios para Download
        Text(
            text = "Relatórios para Download",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = TextDark,
            modifier = Modifier.padding(top = 8.dp)
        )

        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            DownloadCardRefined("Relatório Completo de Matemática", "18/05/2025")
            DownloadCardRefined("Relatório de Frequência", "18/05/2025")
            DownloadCardRefined("Relatório de Desempenho", "18/05/2025")
            DownloadCardRefined("Observações e Análise de Desempenho", "18/05/2025")
        }

        Spacer(Modifier.height(50.dp))
    }
}

// Componente para uma linha de informação
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

// Componente para os cards de insights
@Composable
fun InsightCard(title: String, date: String, description: String) {
    DashboardCard(title = title) {
        Text(text = date, fontSize = 12.sp, color = TextGray)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = description, fontSize = 14.sp, color = TextDark)
    }
}


@Preview(showBackground = true)
@Composable
private fun DashboardPreview() {
    DashboardScreen(null)
}
