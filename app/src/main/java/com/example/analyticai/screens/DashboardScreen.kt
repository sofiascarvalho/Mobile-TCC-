package com.example.analyticai.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.analyticai.data.SharedPreferencesManager
import com.example.analyticai.model.Dashboard.DesempenhoResponse
import com.example.analyticai.screens.components.*
import com.example.analyticai.viewmodel.DesempenhoViewModel

@Composable
fun DashboardScreen(
    navController: NavController? = null,
    viewModel: DesempenhoViewModel = viewModel()
) {
    val context = LocalContext.current
    val sharedPrefs = remember { SharedPreferencesManager(context) }
    val usuarioSalvo = sharedPrefs.getUsuario()

    // Filtros selecionados
    var disciplinaSelecionada by remember { mutableStateOf<String?>(null) }
    var periodoSelecionado by remember { mutableStateOf<String?>(null) }

    // Carregar desempenho do usuário
    LaunchedEffect(usuarioSalvo, disciplinaSelecionada, periodoSelecionado) {
        usuarioSalvo?.id_usuario?.let { idAluno ->
            // Mapear IDs a partir do desempenho atual (ou null se não houver filtro)
            val dashboardAtual = viewModel.desempenho
            val idMateria = dashboardAtual?.desempenho
                ?.firstOrNull { it.materia.materia == disciplinaSelecionada }
                ?.materia?.materia_id

            val idSemestre = periodoSelecionado?.let { selecionado ->
                dashboardAtual?.desempenho
                    ?.firstOrNull { it.semestre == selecionado }
                    ?.id_semestre
            }

            viewModel.loadPerformance(
                idAluno = idAluno,
                idMateria = idMateria,
                idSemestre = idSemestre
            )
        }
    }

    val dashboard = viewModel.desempenho
    val loading = viewModel.isLoading

    if (loading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    val nomeUsuario = usuarioSalvo?.nome ?: "Usuário"

    // Filtros dinâmicos
    val disciplinasDisponiveis = dashboard?.desempenho
        ?.map { it.materia.materia }
        ?.distinct()
        ?.toMutableList()
        ?: mutableListOf()
    disciplinasDisponiveis.add(0, "Todas as disciplinas")

    val periodosDisponiveis = dashboard?.desempenho
        ?.map { it.semestre }
        ?.distinct()
        ?.toMutableList()
        ?: mutableListOf()
    periodosDisponiveis.sort()

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
            text = "Dashboard de $nomeUsuario",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark
        )

        // Filtros
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DropdownFiltro(
                label = "Disciplina:",
                opcoes = disciplinasDisponiveis,
                selecionado = disciplinaSelecionada ?: "Todas as disciplinas",
                onSelecionar = { disciplinaSelecionada = it.takeIf { it != "Todas as disciplinas" } }
            )
            DropdownFiltro(
                label = "Período:",
                opcoes = periodosDisponiveis,
                selecionado = periodoSelecionado ?: periodosDisponiveis.firstOrNull().orEmpty(),
                onSelecionar = { periodoSelecionado = it }
            )
        }

        // Conteúdo
        dashboard?.desempenho
            ?.filter { item ->
                (disciplinaSelecionada == null || item.materia.materia == disciplinaSelecionada)
                        && (periodoSelecionado == null || item.semestre == periodoSelecionado)
            }
            ?.forEach { item ->

                // --- KPI DE DESEMPENHO ---
                val mediaScore = item.media.toFloatOrNull() ?: 0f
                PerformanceKpiCard(
                    score = mediaScore.toDouble(),
                    change = 0.0,
                    modifier = Modifier.fillMaxWidth()
                )

                // --- KPI DE FREQUÊNCIA ---
                val frequenciaFloat = item.frequencia.porcentagem_frequencia
                    .replace("%", "")
                    .toFloatOrNull() ?: 0f
                FrequencyKpiCard(
                    presentPercentage = frequenciaFloat,
                    modifier = Modifier.fillMaxWidth()
                )

                // --- GRÁFICO DE DESEMPENHO POR ATIVIDADE ---
                val barData = item.atividades.map {
                    BarData(label = it.categoria, value = it.nota)
                }
                SubjectPerformanceChartCard(
                    subject = item.materia.materia,
                    data = barData
                )

                // --- RELATÓRIOS ---
                Text(
                    text = "Relatórios para Download",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = TextDark,
                    modifier = Modifier.padding(top = 8.dp)
                )

//                // Se houver campo de relatórios no backend, mapear dinamicamente:
//                item.relatorios?.forEach { rel ->
//                    DownloadCardRefined(rel.titulo, rel.dataGeracao)
//                }
            } ?: Text("Nenhum dado encontrado.")
    }
}

@Composable
fun DropdownFiltro(
    label: String,
    opcoes: List<String>,
    selecionado: String,
    onSelecionar: (String) -> Unit
) {
    var expandido by remember { mutableStateOf(false) }

    Column {
        Text(
            text = label,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp
        )
        Box {
            OutlinedButton(onClick = { expandido = true }) {
                Text(selecionado)
            }
            DropdownMenu(expanded = expandido, onDismissRequest = { expandido = false }) {
                opcoes.forEach { opcao ->
                    DropdownMenuItem(
                        text = { Text(opcao) },
                        onClick = {
                            onSelecionar(opcao)
                            expandido = false
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    DashboardScreen(rememberNavController())
}
