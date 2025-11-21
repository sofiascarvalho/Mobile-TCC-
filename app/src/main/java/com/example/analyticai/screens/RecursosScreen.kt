package com.example.analyticai.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.analyticai.model.Dashboards.Materia
import com.example.analyticai.model.Dashboards.Semestre
import com.example.analyticai.model.Recursos.Recurso
import com.example.analyticai.screens.components.ConfirmarDownload
import com.example.analyticai.screens.components.FilterDropdown
import com.example.analyticai.screens.components.RecursoCard
import com.example.analyticai.viewmodel.FiltrosViewModel
import com.example.analyticai.viewmodel.RecursosViewModel
import com.example.analyticai.viewmodel.RecursosViewModelFactory

@Composable
fun RecursosScreen(navegacao: NavHostController?) {
    val filtrosViewModel: FiltrosViewModel = hiltViewModel()
    val context = LocalContext.current
    val recursosViewModel: RecursosViewModel = viewModel(factory = RecursosViewModelFactory(context))

    val materias by filtrosViewModel.materias.collectAsState()
    val semestres by filtrosViewModel.semestres.collectAsState()
    val isFiltrosLoading by filtrosViewModel.isLoading.collectAsState()

    var selectedMateria by remember { mutableStateOf<Materia?>(null) }
    var selectedSemestre by remember { mutableStateOf<Semestre?>(null) }

    val recursos = recursosViewModel.recursos
    val isLoading = recursosViewModel.isLoading
    val errorMessage = recursosViewModel.errorMessage

    val showRecursos = selectedMateria != null && selectedSemestre != null

    var recursoSelecionado by remember { mutableStateOf<Recurso?>(null) }

    LaunchedEffect(selectedMateria?.id_materia, selectedSemestre?.id_semestre) {
        val materiaId = selectedMateria?.id_materia
        val semestreId = selectedSemestre?.id_semestre
        if (materiaId != null && semestreId != null) {
            recursosViewModel.loadRecursos(materiaId, semestreId)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Mural de recursos",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 22.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Divider(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .fillMaxWidth(),
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.0f),
                thickness = 1.dp
            )
            Text(
                text = "Visão geral de recursos disponibilizados pelos professores.",
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(20.dp))
        }

        // Filtros usando os mesmos componentes da Dashboard/Ranking
        if (isFiltrosLoading) {
            Text(
                text = "Carregando filtros...",
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val materiaLabel = selectedMateria?.materia
                ?: if (materias.isEmpty()) "Carregando..." else "Selecione"

            FilterDropdown(
                label = "Matéria",
                selectedValue = materiaLabel,
                options = materias.map { it.materia },
                onSelect = { selectedName ->
                    selectedMateria = materias.find { it.materia == selectedName }
                },
                modifier = Modifier.weight(1f)
            )

            val semestreLabel = selectedSemestre?.semestre
                ?: if (semestres.isEmpty()) "Carregando..." else "Selecione"

            FilterDropdown(
                label = "Semestre",
                selectedValue = semestreLabel,
                options = semestres.map { it.semestre },
                onSelect = { selectedName ->
                    selectedSemestre = semestres.find { it.semestre == selectedName }
                },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Conteúdo condicionado à seleção dos filtros
        if (!showRecursos) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Selecione uma matéria e um semestre para visualizar os recursos.",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
            }
        } else {
            when {
                isLoading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    }
                }

                recursos.isNotEmpty() -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 8.dp)
                    ) {
                        items(recursos) { recurso ->
                            RecursoCard(
                                recurso = recurso,
                                onBaixarClick = { recursoSelecionado = it }
                            )
                        }
                    }
                }

                else -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Não encontramos recursos para os filtros selecionados.",
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(horizontal = 24.dp)
                        )
                    }
                }
            }
        }
    }

    // Confirmação de download
    ConfirmarDownload(
        recurso = recursoSelecionado,
        onConfirmar = {
            recursoSelecionado = null
        },
        onCancelar = { recursoSelecionado = null }
    )
}

@Composable
fun AbaSelecionavel(titulo: String, selecionado: Boolean, onClick: () -> Unit) {
    TextButton(onClick = onClick) {
        Text(
            text = titulo,
            fontWeight = if (selecionado) FontWeight.Bold else FontWeight.Normal,
            color = if (selecionado) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 16.sp
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun RecursosScreenPreview() {
    RecursosScreen(null)
}
