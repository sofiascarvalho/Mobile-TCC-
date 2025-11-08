package com.example.analyticai.screens.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Download
import androidx.compose.runtime.*
import androidx.compose.material.icons.outlined.LibraryBooks
import androidx.compose.material.icons.outlined.BarChart // <-- NOVO ÍCONE DE GRÁFICO IMPORTADO
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.foundation.clickable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
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

// Cores usadas especificamente nos gráficos
val LightPurple = PurplePrimary.copy(alpha = 0.4f)
val ChartBarColor = PurplePrimary
val ChartBackgroundColor = Color(0xFFF0E5FF)
val ChartTextGray = TextGray.copy(alpha = 0.9f)

// --- Cards de KPI Refinados (Conforme imagens) ---

/**
 * Componente para o Card de Desempenho (Média).
 * Usa ícones e tipografia da imagem de referência.
 */
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

            Row(verticalAlignment = Alignment.CenterVertically) {
                // Valor principal
                Text(
                    text = String.format("%.1f", score),
                    fontWeight = FontWeight.Light, // Mais leve conforme o design
                    fontSize = 42.sp,
                    color = TextDark
                )
                Spacer(Modifier.width(12.dp))

                // Variação
                val changeText = buildAnnotatedString {
                    withStyle(style = SpanStyle(
                        color = PurplePrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )) {
                        append("▲ ${String.format("%.1f", change)} no último semestre")
                    }
                }
                Text(changeText, modifier = Modifier.padding(top = 8.dp))
            }

            Spacer(Modifier.height(10.dp))

            // Subtexto
            Text(
                text = "Média do Semestre",
                color = TextGray,
                fontSize = 14.sp,
                textDecoration = TextDecoration.Underline // Sublinhado conforme design
            )
        }
    }
}

/**
 * Componente para o Card de Frequência (Gráfico de Pizza).
 */
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

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Gráfico de Pizza (Pie Chart)
                PieChart(
                    percentage = presentPercentage,
                    modifier = Modifier.size(80.dp)
                )

                // Texto do KPI
                Column(horizontalAlignment = Alignment.End) {
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

            // Legenda
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                LegendItem(color = LightPurple, label = "Presença (${presentPercentage.toInt()}%)")
                LegendItem(color = PurplePrimary, label = "Faltas (${(100 - presentPercentage).toInt()}%)")
            }
        }
    }
}

/**
 * Gráfico de Pizza (Pie Chart) estático.
 */
@Composable
fun PieChart(percentage: Float, modifier: Modifier = Modifier) {
    val sweepAngle = percentage * 360f / 100f

    Canvas(modifier = modifier) {
        // Cor das Faltas (100% - percentage) - Backgroud
        drawArc(
            color = LightPurple,
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = true,
            size = size
        )
        // Cor da Presença (percentage)
        drawArc(
            color = PurplePrimary,
            startAngle = 270f, // Começa do topo
            sweepAngle = -sweepAngle, // Desenha no sentido anti-horário
            useCenter = true,
            size = size
        )
        // Ocultar a área central para fazer parecer um donut se necessário, mas aqui é pizza cheia.
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


// --- Gráfico de Barras Estático ---

data class BarData(val label: String, val value: Float)

/**
 * Card com o Gráfico de Barras para Desempenho em Matéria.
 */
@Composable
fun SubjectPerformanceChartCard(
    subject: String,
    data: List<BarData>
) {
    // Alterado o ícone para BarChart, que está disponível.
    DashboardCard(title = "Desempenho em \"$subject\"", icon = Icons.Outlined.BarChart) {
        Spacer(Modifier.height(8.dp))
        BarChart(data = data, modifier = Modifier.fillMaxWidth().height(200.dp))
    }
}

/**
 * Componente de Gráfico de Barras estático (Bar Chart)
 */
@Composable
fun BarChart(data: List<BarData>, modifier: Modifier = Modifier) {
    // O valor máximo para normalização é 10.
    val scaleMaxY = 10f

    Box(modifier = modifier.clip(RoundedCornerShape(12.dp))) {

        Canvas(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 8.dp)) {
            val barCount = data.size
            if (barCount == 0) return@Canvas

            // Define o espaço para os rótulos X e para a linha de base
            val labelHeight = 30.dp.toPx()
            val chartHeight = size.height - labelHeight
            val totalWidth = size.width
            val spacing = 20.dp.toPx()
            val barWidth = (totalWidth - spacing * (barCount - 1)) / barCount

            // Desenha as barras
            data.forEachIndexed { index, item ->
                // Normaliza a altura em relação à altura total do gráfico e o valor máximo (10)
                val barHeightRatio = (item.value / scaleMaxY) * chartHeight
                val barX = index * (barWidth + spacing)
                val barY = chartHeight - barHeightRatio // Começa de baixo (base = chartHeight)

                // Desenha a barra
                drawRect(
                    color = ChartBarColor,
                    topLeft = Offset(barX, barY),
                    size = Size(barWidth, barHeightRatio)
                )
            }

            // Linha base (eixo X) - mais proeminente
            drawLine(
                color = ChartTextGray.copy(alpha = 0.5f),
                start = Offset(0f, chartHeight), // Alinha com a base das barras
                end = Offset(totalWidth, chartHeight),
                strokeWidth = 2.dp.toPx(), // Linha mais grossa
                cap = StrokeCap.Butt
            )
        }

        // Overlay de Textos para Rótulos (Valores acima e Eixo X)
        val chartHeightDp = 200.dp // Altura definida no SubjectPerformanceChartCard
        val labelHeightDp = 30.dp
        val actualChartAreaHeightDp = chartHeightDp - labelHeightDp

        // 1. Valores acima das barras
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(actualChartAreaHeightDp) // Altura da área do gráfico, exceto rótulos
                .padding(horizontal = 16.dp)
                .align(Alignment.TopCenter),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            data.forEach { item ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp)
                ) {
                    // Calcula a altura da barra em Dp e inverte para o Spacer
                    val barHeightDp = (item.value / scaleMaxY) * actualChartAreaHeightDp.value
                    Spacer(Modifier.height(actualChartAreaHeightDp - barHeightDp.dp - 12.dp)) // Ajuste para posicionar 12dp acima da barra
                    Text(
                        text = String.format("%.1f", item.value),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextDark
                    )
                }
            }
        }


        // 2. Rótulos do Eixo X - posicionado abaixo das barras
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(labelHeightDp) // Altura reservada para os rótulos
                .align(Alignment.BottomCenter), // Alinha os rótulos à parte inferior
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


// --- Componentes Reutilizáveis Refinados ---

/**
 * Dropdown de Filtro Refinado (com rótulo e ícone de seta)
 * Agora usa DropdownMenu e DropdownMenuItem para ser funcional.
 */
@Composable
fun FilterDropdown(
    label: String,
    selectedValue: String,
    options: List<String>,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    // Estado para controlar se o menu está expandido
    var expanded by remember { mutableStateOf(false) }

    // Box para ancorar o DropdownMenu e ser clicável
    Box(
        modifier = modifier.wrapContentSize(Alignment.TopStart) // Permite que o menu flutue
    ) {
        // Componente clicável que mostra o valor selecionado
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White)
                .border(1.dp, TextGray.copy(alpha = 0.3f), RoundedCornerShape(12.dp)) // Borda sutil
                .clickable(onClick = { expanded = true }) // Torna clicável e abre o menu
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
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f) // Ocupa o espaço restante
            ) {
                Text(
                    text = selectedValue,
                    color = TextGray,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    maxLines = 1 // Evita quebra de linha
                )
            }
            // Ícone de seta no final
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "Dropdown",
                tint = TextGray,
                modifier = Modifier.size(20.dp)
            )
        }

        // O Menu Dropdown em si
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            // Ajusta a largura para o tamanho do Box Pai (o Row clicável)
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

/**
 * Download Card Refinado (com ícone de download destacado).
 */
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

            // Botão/Ícone de Download - Modificado para ser menor e circular
            Box(
                modifier = Modifier
                    .size(32.dp) // DIMINUÍDO DE 40.dp para 32.dp
                    .clip(RoundedCornerShape(50.dp)) // ARREDONDADO PARA CÍRCULO
                    .background(PurplePrimary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Download,
                    contentDescription = "Download",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp) // DIMINUÍDO DE 24.dp para 20.dp
                )
            }
        }
    }
}

/**
 * Componente Card padrão com a capacidade de adicionar um ícone.
 */
@Composable
fun DashboardCard(title: String, icon: ImageVector? = null, content: @Composable ColumnScope.() -> Unit) {
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

@Preview(showBackground = true)
@Composable
private fun ComponentsPreview() {
    // MOCK DATA para Dropdowns (corrigido o erro de compilação)
    val disciplineOptions = listOf("Todas as Disciplinas", "Matemática", "Português", "História")
    val periodOptions = listOf("1º Semestre - 2025", "2º Semestre - 2024", "1º Semestre - 2023")

    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        // Preview dos novos cards KPI
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            PerformanceKpiCard(9.8, 0.3, modifier = Modifier.weight(1f))
            FrequencyKpiCard(90f, modifier = Modifier.weight(1f))
        }

        // Preview do Card de Gráfico de Barras
        val mockData = listOf(
            BarData("Atividade", 8.0f),
            BarData("Prova", 9.2f),
            BarData("Seminário", 7.3f),
            BarData("Prova", 10.0f)
        )
        SubjectPerformanceChartCard(subject = "Matemática", data = mockData)

        // Preview dos Dropdowns Refinados
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            FilterDropdown(
                label = "Disciplina",
                selectedValue = "Todas as Disciplinas",
                options = disciplineOptions, // <--- CORRIGIDO
                onSelect = {}, // <--- CORRIGIDO
                modifier = Modifier.weight(1f)
            )
            FilterDropdown(
                label = "Período",
                selectedValue = "1º Semestre - 2025",
                options = periodOptions, // <--- CORRIGIDO
                onSelect = {}, // <--- CORRIGIDO
                modifier = Modifier.weight(1f)
            )
        }

        // Preview do Card de Download Refinado
        DownloadCardRefined("Relatório Completo de Matemática", "18/05/2025")
    }
}