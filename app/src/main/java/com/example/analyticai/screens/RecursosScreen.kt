package com.example.analyticai.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.analyticai.model.Recurso
import com.example.analyticai.screens.components.ConfirmarDownload
import com.example.analyticai.screens.components.RecursoCard

@Composable
fun RecursosScreen(navegacao: NavHostController?) {
    var recursoSelecionado by remember { mutableStateOf<Recurso?>(null) }

    val recursos = remember { mutableStateListOf(
        Recurso(
            titulo = "Material Complementar: Filosofia",
            descricao = "Material para estudo complementar da aula de segunda-feira",
            disciplina = "Filosofia",
            periodo = "1º Semestre",
            arquivo = "materia-filosofia.pdf"
        ),
        Recurso(
            titulo = "Material Complementar: Filosofia",
            descricao = "Material para estudo complementar da aula de segunda-feira",
            disciplina = "Filosofia",
            periodo = "1º Semestre",
            arquivo = "materia-filosofia.pdf"
        ),
        Recurso(
            titulo = "Material Complementar: Filosofia",
            descricao = "Material para estudo complementar da aula de segunda-feira",
            disciplina = "Filosofia",
            periodo = "1º Semestre",
            arquivo = "materia-filosofia.pdf"
        ),
    )
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        LazyColumn (
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ){
            items(recursos.size){ index ->
                RecursoCard(
                    recursos[index],
                    onBaixarClick = {
                        recursoSelecionado = it
                    }
                )
            }
        }
        ConfirmarDownload(
            recurso = recursoSelecionado,
            onConfirmar = {
                recursoSelecionado = null
            },
            onCancelar = {
                recursoSelecionado = null
            }
        )
    }


}

@Preview
@Composable
private fun RecursosScreenPreview() {
    RecursosScreen(null)
}