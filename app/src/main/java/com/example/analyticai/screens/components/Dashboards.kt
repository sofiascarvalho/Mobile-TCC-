package com.example.analyticai.screens.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.BarChart

import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Download
import androidx.compose.runtime.*
import androidx.compose.material.icons.outlined.BarChart // <-- NOVO ÍCONE DE GRÁFICO IMPORTADO
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap

import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.analyticai.screens.PurplePrimary
import com.example.analyticai.screens.TextDark
import com.example.analyticai.screens.TextGray
import com.example.analyticai.viewmodel.FiltrosViewModel

// Importe as classes diretamente, não o pacote Dashboards
// Certifique-se de que esses caminhos estão corretos e as classes Materia e Semestre existem.
import com.example.analyticai.model.Dashboards.Materia
import com.example.analyticai.model.Dashboards.Semestre

// --- Cores dos gráficos ---


val LightPurple = PurplePrimary.copy(alpha = 0.4f)
val ChartBarColor = PurplePrimary
val ChartBackgroundColor = Color(0xFFF0E5FF)
val ChartTextGray = TextGray.copy(alpha = 0.9f)
val TextDark = Color(0xFF3C3C3C)


// --- Cards de KPI ---
@Composable
fun PerformanceKpiCard(
    score: Double,
    change: Double,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Desempenho",
                    tint = TextDark,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    "Desempenho na Matéria",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = TextDark
                )
            }
            Spacer(Modifier.height(12.dp))

            Column {
                Text(
                    text = String.format("%.1f", score),
                    fontWeight = FontWeight.Light,
                    fontSize = 42.sp,
                    color = TextDark
                )
                Spacer(Modifier.height(8.dp))

                val changeText = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = PurplePrimary,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    ) {
                        append("▲ ${String.format("%.1f", change)} no último semestre")
                    }
                }
                Text(changeText)
            }

            Spacer(Modifier.height(10.dp))

            Text(
                text = "Média do Semestre",
                color = TextGray,
                fontSize = 14.sp,
                textDecoration = TextDecoration.Underline
            )
        }
    }
}

@Composable
fun FrequencyKpiCard(
    presentPercentage: Float,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Schedule,
                    contentDescription = "Frequência",
                    tint = TextDark,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    "Frequência no Período",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = TextDark
                )
            }
            Spacer(Modifier.height(12.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PieChart(
                    percentage = presentPercentage,
                    modifier = Modifier.size(80.dp)
                )
                Spacer(Modifier.height(12.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "${presentPercentage.toInt()}%",
                        fontWeight = FontWeight.Bold,
                        fontSize = 42.sp,
                        color = TextDark
                    )
                    Text(
                        text = "Frequência",
                        fontSize = 16.sp,
                        color = TextGray
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                LegendItem(color = LightPurple, label = "Presença (${presentPercentage.toInt()}%)")
                LegendItem(color = PurplePrimary, label = "Faltas (${(100 - presentPercentage).toInt()}%)")
            }
        }
    }
}

@Composable
fun PieChart(percentage: Float, modifier: Modifier = Modifier) {
    val sweepAngle = percentage * 360f / 100f
    Canvas(modifier = modifier) {
        drawArc(
            color = LightPurple,
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = true,
            size = size
        )
        drawArc(
            color = PurplePrimary,
            startAngle = 270f,
            sweepAngle = -sweepAngle,
            useCenter = true,
            size = size
        )
    }
}

@Composable
fun LegendItem(color: Color, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .background(color, RoundedCornerShape(2.dp))
        )
        Spacer(Modifier.width(4.dp))
        Text(label, fontSize = 12.sp, color = TextGray)
    }
}

// --- Gráfico de Barras ---
data class BarData(val label: String, val value: Float)

@Composable
fun SubjectPerformanceChartCard(
    subject: String,
    data: List<BarData>
) {
    DashboardCard(title = "Desempenho em \"$subject\"", icon = Icons.Outlined.BarChart) {
        Spacer(Modifier.height(8.dp))
        BarChart(data = data, modifier = Modifier.fillMaxWidth().height(200.dp))
    }
}

@Composable
fun BarChart(data: List<BarData>, modifier: Modifier = Modifier) {
    val scaleMaxY = 10f
    Box(modifier = modifier.clip(RoundedCornerShape(12.dp))) {
        Canvas(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 8.dp)) {
            val barCount = data.size
            if (barCount == 0) return@Canvas
            val labelHeight = 30.dp.toPx()
            val chartHeight = size.height - labelHeight
            val totalWidth = size.width
            val spacing = 20.dp.toPx()
            val barWidth = (totalWidth - spacing * (barCount - 1)) / barCount

            data.forEachIndexed { index, item ->
                val barHeightRatio = (item.value / scaleMaxY) * chartHeight
                val barX = index * (barWidth + spacing)
                val barY = chartHeight - barHeightRatio
                drawRect(
                    color = ChartBarColor,
                    topLeft = Offset(barX, barY),
                    size = Size(barWidth, barHeightRatio)
                )
            }

            drawLine(
                color = ChartTextGray.copy(alpha = 0.5f),
                start = Offset(0f, chartHeight),
                end = Offset(totalWidth, chartHeight),
                strokeWidth = 2.dp.toPx(),
                cap = StrokeCap.Butt
            )
        }

        val chartHeightDp = 200.dp
        val labelHeightDp = 30.dp
        val actualChartAreaHeightDp = chartHeightDp - labelHeightDp

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(actualChartAreaHeightDp)
                .padding(horizontal = 16.dp)
                .align(Alignment.TopCenter),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            data.forEach { item ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f).padding(horizontal = 4.dp)
                ) {
                    val barHeightDp = (item.value / scaleMaxY) * actualChartAreaHeightDp.value
                    Spacer(Modifier.height(actualChartAreaHeightDp - barHeightDp.dp - 12.dp))
                    Text(
                        text = String.format("%.1f", item.value),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextDark
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(labelHeightDp)
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            data.forEach { item ->
                Text(
                    text = item.label,
                    fontSize = 12.sp,
                    color = ChartTextGray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

// --- Componentes Reutilizáveis ---
@Composable
fun FilterDropdown(
    label: String,
    selectedValue: String,
    options: List<String>,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier.wrapContentSize(Alignment.TopStart)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White)
                .border(1.dp, TextGray.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                .clickable { expanded = true }
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$label: ",
                color = TextDark,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = selectedValue,
                color = TextGray,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                maxLines = 1,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "Dropdown",
                tint = TextGray,
                modifier = Modifier.size(20.dp)
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(IntrinsicSize.Max)
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onSelect(option)
                        expanded = false
                    },
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
    }
}

@Composable
fun DownloadCardRefined(title: String, date: String, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.SemiBold, fontSize = 16.sp, color = TextDark)
                Spacer(Modifier.height(4.dp))
                Text("Última atualização: $date", fontSize = 12.sp, color = TextGray)
            }
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .background(PurplePrimary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Download,
                    contentDescription = "Download",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun DashboardCard(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector? = null, content: @Composable ColumnScope.() -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (icon != null) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = TextDark,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                }
                Text(title, fontWeight = FontWeight.SemiBold, fontSize = 16.sp, color = TextDark)
            }
            Spacer(Modifier.height(12.dp))
            content()
        }
    }
}

// --- Filtros Verticais Simplificados (Apenas Aluno) ---
@Composable
fun FiltrosVerticais(
    viewModel: FiltrosViewModel
) {
    // CORREÇÃO CRÍTICA (Linhas 437 e 438 no contexto original):
    // Trocar a delegação de propriedade 'by' por atribuição direta '='.
    // Isso garante que o tipo do valor 'materias' e 'semestres' seja corretamente
    // inferido como State<List<Materia>> e State<List<Semestre>>,
    // resolvendo o erro em cascata de inferência de tipo.
    val materias: State<List<Materia>> = viewModel.materias.collectAsState(initial = emptyList())
    val semestres: State<List<Semestre>> = viewModel.semestres.collectAsState(initial = emptyList())

    var selectedMateria by remember { mutableStateOf("") }
    var selectedSemestre by remember { mutableStateOf("") }

    // Usamos .value para acessar o valor dentro do State
    val materiaList = materias.value
    val semestreList = semestres.value

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Filtros fixos para Aluno
        Text("Matéria", color = Color.Black)
        FilterDropdown(
            label = "Matéria",
            selectedValue = selectedMateria.ifEmpty { "Selecione a Matéria" },
            // CORREÇÃO 2: Usando a lista corretamente tipada (materiaList) e mantendo a tipagem explícita na lambda para robustez.
            options = listOf("Selecione a Matéria") + materiaList.map { it.name },
            onSelect = { selected: String -> selectedMateria = selected }
        )

        Text("Período", color = Color.Black)
        FilterDropdown(
            label = "Período",
            selectedValue = selectedSemestre.ifEmpty { "Selecione o Período" },
            // CORREÇÃO 3: Usando a lista corretamente tipada (semestreList)
            options = listOf("Selecione o Período") + semestreList.map { it.name },
            onSelect = { selected: String -> selectedSemestre = selected }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* TODO: aplicar filtros */ },
            colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Aplicar", color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ComponentsPreview() {
    val disciplineOptions = listOf("Todas as Disciplinas", "Matemática", "Português", "História")
    val periodOptions = listOf("1º Semestre - 2025", "2º Semestre - 2024", "1º Semestre - 2023")

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            PerformanceKpiCard(9.8, 0.3, modifier = Modifier.weight(1f))
            FrequencyKpiCard(90f, modifier = Modifier.weight(1f))
        }

        val mockData = listOf(
            BarData("Atividade", 8.0f),
            BarData("Prova", 9.2f),
            BarData("Seminário", 7.3f),
            BarData("Trabalho", 10.0f)
        )
        SubjectPerformanceChartCard(subject = "Matemática", data = mockData)

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            FilterDropdown(
                label = "Disciplina",
                selectedValue = "Todas as Disciplinas",
                options = disciplineOptions,
                onSelect = { selected: String -> /* ação para o preview */ }
            )
            FilterDropdown(
                label = "Período",
                selectedValue = "1º Semestre - 2025",
                options = periodOptions,
                onSelect = { selected: String -> /* ação para o preview */ }
            )
        }

        DownloadCardRefined(title = "Relatório de Desempenho", date = "05/11/2025")
    }
}