package com.example.analyticai.screens.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.analyticai.screens.PurplePrimary
import com.example.analyticai.screens.TextDark
import com.example.analyticai.screens.TextGray
import com.example.analyticai.viewmodel.FiltrosViewModel
import com.example.analyticai.model.Dashboards.Materia
import com.example.analyticai.model.Dashboards.Semestre
import com.example.analyticai.model.Dashboard.Insight
import com.example.analyticai.model.Dashboard.RelatorioCardState
import com.example.analyticai.model.Dashboard.RelatorioTipo
import com.example.analyticai.viewmodel.InsightState
import kotlin.math.max
import kotlin.math.min

val LightPurple = PurplePrimary.copy(alpha = 0.4f)
val ChartBarColor = PurplePrimary
val ChartTextGray = TextGray.copy(alpha = 0.9f)
val TextDark = Color(0xFF3C3C3C)


// --- Cards de KPI ---
@Composable
fun PerformanceKpiCard(
    score: Double,
    subjectName: String,
    modifier: Modifier = Modifier,
    isPlaceholder: Boolean = false
) {
    val titleColor = if (isPlaceholder) TextGray else TextDark
    val accentColor = if (isPlaceholder) TextGray.copy(alpha = 0.7f) else PurplePrimary
    val valueColor = if (isPlaceholder) TextGray else TextDark
    val displayScore = if (isPlaceholder) "--" else String.format("%.1f", score)
    val infoText = if (isPlaceholder) {
        "Selecione matéria e semestre para visualizar."
    } else {
        "Desempenho da matéria $subjectName"
    }

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
                    tint = titleColor,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    "Desempenho na Matéria",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = titleColor
                )
            }
            Spacer(Modifier.height(12.dp))

            Column {
                Text(
                    text = displayScore,
                    fontWeight = FontWeight.Light,
                    fontSize = 42.sp,
                    color = valueColor
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = infoText,
                    color = accentColor,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun FrequencyKpiCard(
    presentPercentage: Float,
    modifier: Modifier = Modifier,
    isPlaceholder: Boolean = false
) {
    val chartPercentage = if (isPlaceholder) 0f else presentPercentage.coerceIn(0f, 100f)
    val valueColor = if (isPlaceholder) TextGray else TextDark
    val placeholderColor = TextGray.copy(alpha = 0.4f)
    val primaryColor = if (isPlaceholder) placeholderColor else PurplePrimary
    val secondaryColor = if (isPlaceholder) placeholderColor.copy(alpha = 0.5f) else LightPurple

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
                    tint = valueColor,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    "Frequência no Período",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = valueColor
                )
            }
            Spacer(Modifier.height(24.dp))

            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                PieChart(
                    percentage = chartPercentage,
                    modifier = Modifier.size(80.dp),
                    primaryColor = primaryColor,
                    backgroundColor = secondaryColor
                )
                Spacer(Modifier.width(12.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = if (isPlaceholder) "--%" else "${chartPercentage.toInt()}%",
                        fontWeight = FontWeight.Bold,
                        fontSize = 42.sp,
                        color = valueColor
                    )
                    Text(
                        text = "Frequência",
                        fontSize = 16.sp,
                        color = if (isPlaceholder) TextGray else TextGray
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                LegendItem(
                    color = secondaryColor,
                    label = "Presença (${if (isPlaceholder) "--" else chartPercentage.toInt()}%)"
                )
                LegendItem(
                    color = primaryColor,
                    label = "Faltas (${if (isPlaceholder) "--" else (100 - chartPercentage).toInt()}%)"
                )
            }
        }
    }
}

@Composable
fun PieChart(
    percentage: Float,
    modifier: Modifier = Modifier,
    primaryColor: Color = PurplePrimary,
    backgroundColor: Color = LightPurple
) {
    val sweepAngle = percentage * 360f / 100f
    Canvas(modifier = modifier) {
        drawArc(
            color = backgroundColor,
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = true,
            size = size
        )
        drawArc(
            color = primaryColor,
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
    data: List<BarData>,
    isPlaceholder: Boolean = false
) {
    val hasData = data.isNotEmpty() && !isPlaceholder
    DashboardCard(title = "Notas em \"$subject\"", icon = Icons.Outlined.BarChart) {
        Spacer(Modifier.height(8.dp))
        if (hasData) {
            BarChart(
                data = data,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFF5F5F5)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (isPlaceholder) {
                        "Selecione os filtros para visualizar o desempenho."
                    } else {
                        "Nenhum dado disponível para os filtros selecionados."
                    },
                    color = TextGray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun BarChart(
    data: List<BarData>,
    modifier: Modifier = Modifier,
    barColor: Color = ChartBarColor,
    valueColor: Color = TextDark,
    gridColor: Color = ChartTextGray.copy(alpha = 0.5f)
) {
    val scaleMaxY = 10f
    Box(modifier = modifier.clip(RoundedCornerShape(12.dp))) {
        Canvas(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 8.dp)) {
            val barCount = data.size
            if (barCount == 0) return@Canvas
            val labelHeight = 30.dp.toPx()
            val chartHeight = size.height - labelHeight
            val totalWidth = size.width
            val spacing = 20.dp.toPx()
            val maxBarWidth = 60.dp.toPx()
            val minBarWidth = 24.dp.toPx()
            val availableWidth = totalWidth - spacing * max(0, barCount - 1)
            val baseBarWidth = if (barCount > 0) availableWidth / barCount else 0f
            val barWidth = baseBarWidth.coerceIn(minBarWidth, maxBarWidth)
            val totalBarsWidth = barWidth * barCount + spacing * max(0, barCount - 1)
            val startX = max(0f, (totalWidth - totalBarsWidth) / 2f)

            data.forEachIndexed { index, item ->
                val barHeightRatio = (item.value / scaleMaxY) * chartHeight
                val barX = startX + index * (barWidth + spacing)
                val barY = chartHeight - barHeightRatio
                drawRect(
                    color = barColor,
                    topLeft = Offset(barX, barY),
                    size = Size(barWidth, barHeightRatio)
                )
            }

            drawLine(
                color = gridColor,
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
                        color = valueColor
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
            containerColor = Color.White,
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
fun DownloadCardRefined(
    state: RelatorioCardState,
    subject: String?,
    onDownloadClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val title = if (!subject.isNullOrBlank()) {
        "${state.tipo.titulo} - $subject"
    } else {
        state.tipo.titulo
    }

    val infoText = state.lastUpdate?.let { "Última atualização: $it" } ?: state.statusMessage
    val infoColor = if (state.hasError) Color(0xFFD32F2F) else TextGray

    val buttonColor = when {
        state.isLoading -> PurplePrimary.copy(alpha = 0.4f)
        state.isEnabled -> PurplePrimary
        state.hasError -> Color(0xFFBDBDBD)
        else -> TextGray.copy(alpha = 0.2f)
    }

    val buttonModifier = Modifier
        .size(40.dp)
        .clip(RoundedCornerShape(50.dp))
        .background(buttonColor)
        .let { base ->
            if (state.isEnabled && !state.isLoading) {
                base.clickable { onDownloadClick() }
            } else {
                base
            }
        }

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
                Text(infoText, fontSize = 12.sp, color = infoColor)
            }
            Box(
                modifier = buttonModifier,
                contentAlignment = Alignment.Center
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(18.dp),
                        strokeWidth = 2.dp,
                        color = Color.White
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Download,
                        contentDescription = "Download",
                        tint = if (state.isEnabled) Color.White else Color.White.copy(alpha = 0.6f),
                        modifier = Modifier.size(20.dp)
                    )
                }
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

// --- Seção de Insights ---
@Composable
fun InsightsSection(
    insightState: InsightState,
    hasFiltersSelected: Boolean = false,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Título da seção
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.AutoAwesome,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(24.dp)
            )
            Spacer(Modifier.width(8.dp))
            Text(
                "Relatórios e Insights por Matéria",
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                color = TextDark
            )
        }

        // Conteúdo baseado no estado
        when {
            insightState.isLoading -> {
                // Estado de carregamento
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(40.dp),
                            color = PurplePrimary
                        )
                        Text(
                            "Gerando insights...",
                            color = TextGray,
                            fontSize = 14.sp
                        )
                    }
                }
            }
            insightState.insight != null -> {
                // Mostrar insight quando disponível
                InsightCard(insight = insightState.insight)
            }
            else -> {
                // Estado inicial - sem filtros selecionados ou sem dados
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFF5F5F5)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (hasFiltersSelected) {
                            "Nenhum insight disponível para os filtros selecionados."
                        } else {
                            "Selecione os filtros para visualizar os insights."
                        },
                        color = TextGray,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp),
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Composable
fun InsightCard(
    insight: Insight,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Título do insight
            Text(
                text = insight.titulo,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = TextDark
            )
            
            Spacer(Modifier.height(8.dp))
            
            // Data
            Text(
                text = insight.data,
                fontSize = 12.sp,
                color = TextGray
            )
            
            Spacer(Modifier.height(12.dp))
            
            // Conteúdo do insight
            Text(
                text = insight.conteudo,
                fontSize = 14.sp,
                color = TextDark,
                lineHeight = 20.sp
            )
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
            options = listOf("Selecione a Matéria") + materiaList.map { it.materia },
            onSelect = { selected: String -> selectedMateria = selected }
        )

        Text("Período", color = Color.Black)
        FilterDropdown(
            label = "Período",
            selectedValue = selectedSemestre.ifEmpty { "Selecione o Período" },
            // CORREÇÃO 3: Usando a lista corretamente tipada (semestreList)
            options = listOf("Selecione o Período") + semestreList.map { it.semestre },
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
        PerformanceKpiCard(score = 9.8, subjectName = "Matemática")
        FrequencyKpiCard(presentPercentage = 90f)

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

        DownloadCardRefined(
            state = RelatorioCardState(
                tipo = RelatorioTipo.DESEMPENHO,
                statusMessage = "Disponível para download",
                lastUpdate = "05/11/2025",
                isEnabled = true
            ),
            subject = "Matemática",
            onDownloadClick = {}
        )
    }
}