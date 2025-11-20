package com.example.analyticai.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.analyticai.data.SharedPreferencesManager
import com.example.analyticai.model.Login.Usuario
import com.example.analyticai.viewmodel.FiltrosViewModel
import com.example.analyticai.viewmodel.DashboardViewModel
import com.example.analyticai.screens.components.DashboardHeader
import com.example.analyticai.screens.components.DownloadCardRefined
import com.example.analyticai.screens.components.FilterDropdown
import com.example.analyticai.screens.components.DashboardContent
import com.example.analyticai.screens.components.InsightsSection

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
    val filtrosViewModel: FiltrosViewModel = hiltViewModel()
    val dashboardViewModel: DashboardViewModel = hiltViewModel()

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
    val relatoriosState by dashboardViewModel.relatoriosState.collectAsState()
    val insightState by dashboardViewModel.insightState.collectAsState()
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {

        // ---- CABEÇALHO ----
        item {
            DashboardHeader(
                title = "Dashboard",
                onActionClick = {
                    dashboardViewModel.carregarDashboard()
                    filtrosViewModel.carregarFiltros()
                }
            )
        }

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
                    selectedValue = selectedMateria?.materia ?: "Selecione",
                    options = materias.map { it.materia },
                    onSelect = { selectedName ->
                        val materia = materias.find { it.materia == selectedName }
                        dashboardViewModel.setSelectedMateria(materia)
                    },
                    modifier = Modifier.weight(1f)
                )

                FilterDropdown(
                    label = "Semestre",
                    selectedValue = selectedSemestre?.semestre ?: "Selecione",
                    options = semestres.map { it.semestre },
                    onSelect = { selectedName ->
                        val semestre = semestres.find { it.semestre == selectedName }
                        dashboardViewModel.setSelectedSemestre(semestre)
                    },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        if (erro != null) {
            item {
                Text(
                    text = erro,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
            }
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
                DashboardContent(
                    dashboard = dashboard,
                    isPlaceholder = dashboardState.isPlaceholder
                )
                Spacer(modifier = Modifier.height(48.dp))
            }

            // Seção de Insights (entre o card de desempenho e os relatórios)
            item {
                InsightsSection(
                    insightState = insightState,
                    hasFiltersSelected = selectedMateria != null && selectedSemestre != null
                )
                Spacer(modifier = Modifier.height(48.dp))
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
            val materiaNome = dashboard.materia.materia.takeIf { !dashboardState.isPlaceholder }

            items(relatoriosState) { rel ->
                DownloadCardRefined(
                    state = rel,
                    subject = materiaNome,
                    onDownloadClick = {
                        rel.link?.let { link ->
                            openExternalLink(context, link)
                        }
                    }
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
                        tint = Color.Black,
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
            modifier = Modifier.weight(0.3f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(0.7f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

private fun openExternalLink(context: Context, link: String) {
    runCatching {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        context.startActivity(intent)
    }
}
