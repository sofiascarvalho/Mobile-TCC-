package com.example.analyticai.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.analyticai.data.SharedPreferencesManager
import com.example.analyticai.model.Login.Usuario
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

    // Estados
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

            // ---- FILTROS ----
            item {
                Spacer(modifier = Modifier.height(8.dp))

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

            // ---- CARD DO ALUNO ----
            item {
                StudentInfoCard()
                Spacer(modifier = Modifier.height(16.dp))
            }

            // ---- CARREGAMENTO ----
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

            // ---- PAINEL ----
            else if (dashboard != null) {

                // Conteúdo principal do painel
                item {
                    DashboardContent(dashboard = dashboard)
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Título dos Relatórios
                item {
                    Text(
                        "Relatórios de Download",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                // PEGAR NOME DA MATÉRIA DE FORMA SEGURA
                val materiaNome = dashboard.materia.materia


                // Lista de relatórios
                items(getMockRelatorios(materiaNome)) { rel ->
                    DownloadCardRefined(
                        title = rel.titulo,
                        date = rel.dataGeracao
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            // ---- ERRO ----
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

@Composable
fun StudentInfoCard() {
    val context = LocalContext.current
    val sharedPrefs = remember { SharedPreferencesManager(context) }
    val usuario: Usuario? = sharedPrefs.getUsuario()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(12.dp),
                clip = false
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(0.dp) // usa só a shadow()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Ícone do aluno",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "Informações do Aluno",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Dados
            if (usuario != null) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    InfoRow(label = "Nome:", value = usuario.nome)
                    InfoRow(label = "Matrícula:", value = usuario.matricula ?: "Não informada")
                    InfoRow(label = "Email:", value = usuario.email)
                    InfoRow(label = "Telefone:", value = usuario.telefone)
                    InfoRow(label = "Nível:", value = usuario.nivel_usuario)

                    usuario.turma?.let { turma ->
                        InfoRow(label = "Turma:", value = turma.turma)
                    }
                }
            } else {
                Text(
                    text = "Dados do aluno não disponíveis",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(0.3f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(0.7f)
        )
    }
}

// ---- MOCK DOS RELATÓRIOS ----
fun getMockRelatorios(materiaNome: String): List<RelatorioResponse> {
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
