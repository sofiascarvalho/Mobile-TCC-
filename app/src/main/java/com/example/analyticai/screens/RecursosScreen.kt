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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.analyticai.model.Recursos.Recurso
import com.example.analyticai.screens.components.ConfirmarDownload
import com.example.analyticai.screens.components.RecursoCard

@Composable
fun RecursosScreen(navegacao: NavHostController?) {
    var recursoSelecionado by remember { mutableStateOf<Recurso?>(null) }
    var abaSelecionada by remember { mutableStateOf("Mural") }
    var disciplinaSelecionada by remember { mutableStateOf("Todas as disciplinas") }
    var periodoSelecionado by remember { mutableStateOf("1º Semestre") }

    val disciplinas = listOf("Todas as disciplinas", "Filosofia", "Matemática", "Biologia")
    val periodos = listOf("1º Semestre", "2º Semestre")

//    val recursosFiltrados = recursos.filter { recurso ->
//        (disciplinaSelecionada == "Todas as disciplinas" || recurso.disciplina == disciplinaSelecionada) &&
//                recurso.periodo == periodoSelecionado
//    }

    val recursos = remember {
        listOf(
            Recurso(
                titulo = "Material Complementar: Filosofia",
                descricao = "Material para estudo complementar da aula de segunda-feira",
                disciplina = "Filosofia",
                periodo = "1º Semestre",
                arquivo = "material-filosofia.pdf"
            ),
            Recurso(
                titulo = "Material Complementar: Matemática",
                descricao = "Video aula no YouTube para complementar a aula de segunda-feira",
                disciplina = "Matemática",
                periodo = "1º Semestre",
                arquivo = "funcao-2grau.pdf"
            ),
            Recurso(
                titulo = "Material Complementar: Biologia",
                descricao = "Material para estudo complementar da aula de segunda-feira",
                disciplina = "Biologia",
                periodo = "2º Semestre",
                arquivo = "material-bio.pdf"
            )
        )
    }

    val recursosFiltrados = recursos.filter { recurso ->
        (disciplinaSelecionada == "Todas as disciplinas" || recurso.disciplina == disciplinaSelecionada) &&
                recurso.periodo == periodoSelecionado
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFDFDFD))
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        // Tabs (Mural / Atividades)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            AbaSelecionavel(
                titulo = "Mural",
                selecionado = abaSelecionada == "Mural",
                onClick = { abaSelecionada = "Mural" }
            )
            AbaSelecionavel(
                titulo = "Atividades",
                selecionado = abaSelecionada == "Atividades",
                onClick = { abaSelecionada = "Atividades" }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Filtros
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DropdownFiltro(
                label = "Disciplina:",
                opcoes = disciplinas,
                selecionado = disciplinaSelecionada,
                onSelecionar = { disciplinaSelecionada = it },
            )

            DropdownFiltro(
                label = "Período:",
                opcoes = periodos,
                selecionado = periodoSelecionado,
                onSelecionar = { periodoSelecionado = it }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Lista de recursos
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 8.dp)
        ) {
            items(recursosFiltrados.size) { index ->
                RecursoCard(
                    recurso = recursosFiltrados[index],
                    onBaixarClick = { recursoSelecionado = it }
                )
            }
        }
    }

    // Confirmação de download
    ConfirmarDownload(
        recurso = recursoSelecionado,
        onConfirmar = { recursoSelecionado = null },
        onCancelar = { recursoSelecionado = null }
    )
}

@Composable
fun AbaSelecionavel(titulo: String, selecionado: Boolean, onClick: () -> Unit) {
    TextButton(onClick = onClick) {
        Text(
            text = titulo,
            fontWeight = if (selecionado) FontWeight.Bold else FontWeight.Normal,
            color = if (selecionado) Color(0xFF6A1B9A) else Color.Gray,
            fontSize = 16.sp
        )
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

@Preview(showSystemUi = true)
@Composable
fun RecursosScreenPreview() {
    RecursosScreen(null)
}
