package com.example.analyticai.screens

import android.graphics.RenderEffect
import android.graphics.Shader
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.analyticai.model.Recurso

@Composable
fun RankingScreen(navegacao: NavHostController?) {
    val rankingList = listOf(
        Triple("1º", "9.8", "Aluno 1"),
        Triple("2º", "9.7", "Aluno 2"),
        Triple("3º", "9.7", "Nome do Aluno"),
        Triple("4º", "9.6", "Aluno 4"),
        Triple("5º", "9.5", "Aluno 5"),
        Triple("6º", "9.4", "Aluno 6"),
        Triple("7º", "9.3", "Aluno 7"),
        Triple("8º", "9.2", "Aluno 8"),
        Triple("9º", "9.1", "Aluno 9"),
        Triple("10º", "9.0", "Aluno 10"),
    )
    var recursoSelecionado by remember { mutableStateOf<Recurso?>(null) }
    var abaSelecionada by remember { mutableStateOf("Mural") }
    var disciplinaSelecionada by remember { mutableStateOf("Todas as disciplinas") }
    var periodoSelecionado by remember { mutableStateOf("1º Semestre") }


    val disciplinas = listOf("Todas as disciplinas", "Filosofia", "Matemática", "Biologia")
    val periodos = listOf("1º Semestre", "2º Semestre")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3EEF9))
            .padding(16.dp)
    ) {
        // Título
        Text(
            text = "Ranking de \"Nome do Aluno\"",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Campos de filtro (estáticos)
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

        Spacer(modifier = Modifier.height(40.dp))

        // Cabeçalho da tabela
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xffDCCDFC), RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                .padding(vertical = 8.dp)
        ) {
            Text(
                "RANKING",
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            Text(
                "MÉDIA",
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            Text(
                "NOME DO ALUNO",
                modifier = Modifier.weight(2f),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }

        // Corpo da tabela
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp))
                .background(Color.White)
        ) {
            items(rankingList) { (posicao, media, nome) ->
                val isUser = nome == "Nome do Aluno"

                // 🔮 Se não for o aluno atual, aplica blur
                Row(
                    modifier = if (!isUser) Modifier.blur(8.dp) else Modifier
                        .fillMaxWidth()
                        .background(if (isUser) Color(0xffDCCDFC) else Color.Transparent)
                        .padding(vertical = 8.dp)
                ) {
                    Text(
                        posicao,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                    Text(
                        media,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                    Text(
                        nome,
                        modifier = Modifier.weight(2f),
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp,
                        fontWeight = if (isUser) FontWeight.Bold else FontWeight.Normal,
                        color = Color.Black
                    )
                }

                Divider(color = Color(0xFFE6DDF7))
            }
        }
    }
}

@Preview
@Composable
private fun RankingScreenPreview() {
    RankingScreen(null)
}
