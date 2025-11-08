package com.example.analyticai.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.* // Importado para usar remember, mutableStateOf, getValue e setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.analyticai.screens.components.*

// 🎨 Cores baseadas no design
val PurplePrimary = Color(0xFF673AB7)
val BackgroundColor = Color(0xFFF8F6FB)
val TextDark = Color(0xFF3C3C3C)
val TextGray = Color(0xFF6F6F6F)

/**
 * Tela de Dashboard principal.
 * @param navegacao O controlador de navegação.
 */
@Composable
fun DashboardScreen(navegacao: NavHostController?) {
    // --- Dados Mockados para Gráficos e Cards (Simulando um ViewModel) ---
    val performanceScore = 9.8
    val scoreChange = 0.3
    val presencePercentage = 90f // 90%

    val mathPerformanceData = listOf(
        BarData("Atividade", 8.0f),
        BarData("Prova", 9.2f),
        BarData("Seminário", 7.3f),
        BarData("Prova", 10.0f)
    )
    // ---------------------------------------------------------------------

    // --- Dados e Estados para Filtros (Novo) ---
    val disciplinas = remember { listOf("Todas as Disciplinas", "Matemática", "Português", "Ciências", "História") }
    val periodos = remember { listOf("1º Semestre - 2025", "2º Semestre - 2025", "Ano Letivo", "Geral") }

    var selectedDisciplina by remember { mutableStateOf(disciplinas.first()) }
    var selectedPeriodo by remember { mutableStateOf(periodos.first()) }
    // ------------------------------------------

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // 🔹 Cabeçalho
        Text(
            text = "Dashboard de “Nome do Aluno”",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark
        )

        // 🔹 Filtros (Agora Dropdowns Funcionais)
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

        // 1. Card Informações Gerais
        DashboardCard(title = "Informações Gerais") {
            InfoLine("Matrícula:", "00000000")
            InfoLine("Nascimento:", "00/00/0000")
            InfoLine("Responsável:", "Nome do responsável")
            InfoLine("Contato:", "(11) 00000-0000")
            InfoLine("E-mail:", "exemplo@email.com")
        }

        // 2. Card Desempenho (Agora ocupa largura total)
        PerformanceKpiCard(
            score = performanceScore,
            change = scoreChange,
            modifier = Modifier.fillMaxWidth() // <-- Ocupa toda a largura
        )

        // 3. Card Frequência (Agora ocupa largura total)
        FrequencyKpiCard(
            presentPercentage = presencePercentage,
            modifier = Modifier.fillMaxWidth() // <-- Ocupa toda a largura
        )

        // O card "Média Turma" foi removido daqui para focar apenas nos dois cards principais.
        // Se precisar dele, pode ser reinserido aqui como KpiCard(title = "Média Turma", ...)

        // 4. Card Gráfico de Barras
        SubjectPerformanceChartCard(
            subject = selectedDisciplina, // Exemplo: usa a disciplina selecionada
            data = mathPerformanceData
        )

        // 5. Relatórios e Insights
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

        // 6. Relatórios para Download
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

// -------------------------------------------------------------------------------------
// Componentes AUXILIARES movidos para DashboardComponents.kt ou mantidos aqui se simples
// -------------------------------------------------------------------------------------

@Composable
fun KpiCard(title: String, kpiValue: String, subText: String, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier
            .height(IntrinsicSize.Min)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(title, fontWeight = FontWeight.SemiBold, fontSize = 16.sp, color = TextDark)
            Spacer(Modifier.height(10.dp))
            Text(
                text = kpiValue,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                color = PurplePrimary
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = subText,
                color = TextGray,
                fontSize = 12.sp,
                maxLines = 1
            )
        }
    }
}

// InfoLine e InsightCard permanecem aqui por simplicidade e uso direto.
@Composable
fun InfoLine(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = TextGray, fontSize = 14.sp)
        Text(value, fontWeight = FontWeight.Medium, color = TextDark, fontSize = 14.sp)
    }
    Spacer(Modifier.height(6.dp))
}

@Composable
fun InsightCard(title: String, date: String, description: String) {
    // Usamos o DashboardCard modificado de DashboardComponents.kt
    DashboardCard(title = title) {
        Text(date, fontSize = 12.sp, color = TextGray)
        Spacer(Modifier.height(4.dp))
        Text(description, fontSize = 14.sp, color = TextDark)
    }
}

@Preview(showBackground = true)
@Composable
private fun DashboardPreview() {
    DashboardScreen(null)
}