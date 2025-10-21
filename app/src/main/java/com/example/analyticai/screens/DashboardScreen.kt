package com.example.analyticai.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.analyticai.viewmodel.DashboardViewModel
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

@Composable
fun DashboardScreen(viewModel: DashboardViewModel = viewModel()) {
    // Extrair o valor real com 'by' ou '.value'
    val desempenho by viewModel.desempenho.collectAsState(initial = emptyList())
    val materias by viewModel.materias.collectAsState(initial = emptyList())
    val selectedMateria by viewModel.selectedMateria.collectAsState(initial = "Todas")

    val idAluno = 1 // Pegue do login ou local storage
    LaunchedEffect(Unit) {
        viewModel.buscarDesempenho(idAluno)
    }

    // Agora 'selectedMateria' é String e 'desempenho' é List<Desempenho>
    val desempenhoFiltrado = if (selectedMateria == "Todas") {
        desempenho
    } else {
        desempenho.filter { it.materia == selectedMateria }
    }


    Column(modifier = Modifier.padding(16.dp)) {
        desempenhoFiltrado.firstOrNull()?.aluno?.let { aluno ->
            Text("Aluno: ${aluno.nome}", style = MaterialTheme.typography.titleLarge)
            Text("Matrícula: ${aluno.matricula}")
            Text("Telefone: ${aluno.telefone}")
            Text("Email: ${aluno.email}")
            Text("Turma: ${aluno.turma.turma}")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Filtro de Matéria
        DropdownMenuMaterias(
            materias = materias,
            selected = selectedMateria,
            onSelect = { viewModel.setSelectedMateria(it) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Frequência (PieChart)
        desempenhoFiltrado.firstOrNull()?.let { item ->
            Text("Frequência: ${item.frequencia.frequencia}%")
            PieChartCompose(
                presenca = item.frequencia.frequencia,
                falta = 100 - item.frequencia.frequencia
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Notas (BarChart)
        desempenhoFiltrado.firstOrNull()?.let { item ->
            Text("Notas por atividade:")
            BarChartCompose(atividades = item.atividades)
        }
    }
}






