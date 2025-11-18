package com.example.analyticai.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.analyticai.viewmodel.FiltrosViewModel
import com.example.analyticai.viewmodel.DashboardViewModel
import com.example.analyticai.screens.components.DashboardHeader
import com.example.analyticai.screens.components.DownloadCardRefined
import com.example.analyticai.screens.components.FilterDropdown
import com.example.analyticai.screens.components.DashboardContent
import com.example.analyticai.model.Dashboard.RelatorioResponse

@Composable
fun LoadingIndicator() {
    CircularProgressIndicator(
        modifier = Modifier
            .size(50.dp)
            .padding(16.dp)
    )
}

@Composable
fun DashboardScreen() {
    val filtrosViewModel: FiltrosViewModel = viewModel()
    val dashboardViewModel: DashboardViewModel = viewModel()

    // 1. COLETAR ESTADOS
    val isFiltrosLoading by filtrosViewModel.isLoading.collectAsState()
    val materias by filtrosViewModel.materias.collectAsState()
    val semestres by filtrosViewModel.semestres.collectAsState()

    val dashboardState by dashboardViewModel.dashboardState.collectAsState()
    val dashboard = dashboardState.dashboard
    val isDashboardLoading = dashboardState.isLoading
    val erro = dashboardState.error

    val totalLoading = isFiltrosLoading || isDashboardLoading

    val selectedMateria by dashboardViewModel.selectedMateria.collectAsState()
    val selectedSemestre by dashboardViewModel.selectedSemestre.collectAsState()

    Scaffold(
        topBar = {
            DashboardHeader(
                title = "Dashboard Analítico",
                onActionClick = {
                    dashboardViewModel.carregarDashboard()
                    filtrosViewModel.carregarFiltros()
                }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            // Header com filtros
            item {
                Spacer(modifier = Modifier.height(8.dp))

                // Linha de Filtros
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FilterDropdown(
                        label = "Matéria",
                        selectedValue = selectedMateria?.name ?: "Selecione",
                        options = materias.map { it.name },
                        onSelect = { selectedName ->
                            val materia = materias.find { it.name == selectedName }
                            dashboardViewModel.setSelectedMateria(materia)
                        },
                        modifier = Modifier.weight(1f)
                    )

                    FilterDropdown(
                        label = "Semestre",
                        selectedValue = selectedSemestre?.name ?: "Selecione",
                        options = semestres.map { it.name },
                        onSelect = { selectedName ->
                            val semestre = semestres.find { it.name == selectedName }
                            dashboardViewModel.setSelectedSemestre(semestre)
                        },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            // 2. LÓGICA DE CARREGAMENTO E DADOS
            if (totalLoading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        LoadingIndicator()
                    }
                }
            }
            else if (dashboard != null) {
                // Conteúdo do dashboard
                item {
                    DashboardContent(dashboard = dashboard)
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Título dos relatórios
                item {
                    Text(
                        "Relatórios de Download",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                // Mock de relatórios de download
                items(getMockRelatorios(dashboard.materia.materia)) { item ->
                    DownloadCardRefined(
                        title = item.titulo,
                        date = item.dataGeracao
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
            else {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = erro ?: "Selecione os filtros ou ocorreu um erro ao carregar os dados.",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}

// Função mock para gerar relatórios de download
private fun getMockRelatorios(materiaNome: String): List<RelatorioResponse> {
    return listOf(
        RelatorioResponse(
            titulo = "Relatório Completo - $materiaNome",
            dataGeracao = "25/11/2025",
            link = "#"
        ),
        RelatorioResponse(
            titulo = "Resumo de Desempenho - $materiaNome",
            dataGeracao = "24/11/2025", 
            link = "#"
        ),
        RelatorioResponse(
            titulo = "Análise de Frequência - $materiaNome",
            dataGeracao = "23/11/2025",
            link = "#"
        )
    )
}