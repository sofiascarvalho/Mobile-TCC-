package com.example.analyticai.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.analyticai.ui.theme.DarkGray
import com.example.analyticai.ui.theme.GrayDarkMedium
import com.example.analyticai.ui.theme.PurplePrimary

// --- Mock Data Structures e Data (Mantidos) ---
data class RankItem(
    val ranking: String,
    val media: String,
    val nomeAluno: String,
    val isCurrentUser: Boolean = false
)

data class FilterOption(
    val id: String,
    val name: String
)

val mockRankings = listOf(
    RankItem(ranking = "1º", media = "9.8", nomeAluno = "João Victor", isCurrentUser = true),
    RankItem(ranking = "2º", media = "9.2", nomeAluno = "Maria Eduarda", isCurrentUser = false),
    RankItem(ranking = "3º", media = "8.7", nomeAluno = "Helena Alves Lopes Alves", isCurrentUser = false),
    RankItem(ranking = "4º", media = "8.5", nomeAluno = "Pedro Henrique", isCurrentUser = false),
    RankItem(ranking = "5º", media = "8.2", nomeAluno = "Ana Carolina", isCurrentUser = false),
    RankItem(ranking = "6º", media = "7.9", nomeAluno = "Lucas Fernandes", isCurrentUser = false),
    RankItem(ranking = "7º", media = "7.6", nomeAluno = "Gabriela Silva", isCurrentUser = false)
)

val mockDisciplinas = listOf(
    FilterOption("placeholder", "Selecione"),
    FilterOption("math", "Matemática"),
    FilterOption("port", "Português"),
    FilterOption("hist", "História")
)

val mockPeriodos = listOf(
    FilterOption("placeholder", "Selecione"),
    FilterOption("1sem", "1º Semestre"),
    FilterOption("2sem", "2º Semestre")
)

@Composable
fun RankingScreen(navegacao: NavHostController?) {
    // --- States for Filters (Mantidos) ---
    var selectedDisciplina by remember { mutableStateOf(mockDisciplinas.first()) }
    var isDisciplinaExpanded by remember { mutableStateOf(false) }

    var selectedPeriodo by remember { mutableStateOf(mockPeriodos.first()) }
    var isPeriodoExpanded by remember { mutableStateOf(false) }

    val currentAlunoName = "João Victor"

    // 1. Lógica para mostrar o Ranking
    val showRanking = selectedDisciplina.id != "placeholder" && selectedPeriodo.id != "placeholder"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // 1. Título e Subtítulo
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Ranking Do Aluno: \"$currentAlunoName\"",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = PurplePrimary
            )
            Divider(modifier = Modifier.padding(vertical = 4.dp), color = PurplePrimary, thickness = 2.dp)
            Text(
                text = "Visão geral da sua classificação em relação aos colegas.",
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(20.dp))
        }

        // 2. Informações do Aluno e Filtros (Reorganizado para mobile)
        Column(modifier = Modifier.fillMaxWidth()) {

            // B. Filtros
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Filtro Disciplina
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Disciplina:", fontWeight = FontWeight.Medium, fontSize = 14.sp, color = DarkGray)
                    Spacer(modifier = Modifier.width(4.dp))
                    DropdownFilter(
                        selectedOption = selectedDisciplina,
                        options = mockDisciplinas,
                        onOptionSelected = { selectedDisciplina = it },
                        isExpanded = isDisciplinaExpanded,
                        onExpandChange = { isDisciplinaExpanded = it }
                    )
                }

                // Filtro Período
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Período:", fontWeight = FontWeight.Medium, fontSize = 14.sp, color = DarkGray)
                    Spacer(modifier = Modifier.width(4.dp))
                    DropdownFilter(
                        selectedOption = selectedPeriodo,
                        options = mockPeriodos,
                        onOptionSelected = { selectedPeriodo = it },
                        isExpanded = isPeriodoExpanded,
                        onExpandChange = { isPeriodoExpanded = it }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Divider(color = Color(0xFFE1E4E7))

        Spacer(modifier = Modifier.height(20.dp))

        // 3. Exibição Condicional da Tabela/Mensagem (Mantida)
        Box(modifier = Modifier.fillMaxSize()) {
            if (showRanking) {
                RankingTable(rankings = mockRankings)
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .padding(bottom = 100.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Selecione uma disciplina e um período para visualizar o ranking.",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(horizontal = 32.dp)
                    )
                }
            }
        }
    }
}

// --- Componentes Reutilizáveis ---

@Composable
fun DropdownFilter(
    selectedOption: FilterOption,
    options: List<FilterOption>,
    onOptionSelected: (FilterOption) -> Unit,
    isExpanded: Boolean,
    onExpandChange: (Boolean) -> Unit
) {
    Box {
        OutlinedButton(
            onClick = { onExpandChange(true) },
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.White,
                contentColor = DarkGray
            ),
            border = BorderStroke(1.dp, Color(0xFFE1E4E7)),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(selectedOption.name, fontSize = 14.sp)
                Icon(Icons.Default.KeyboardArrowDown, contentDescription = null, Modifier.size(18.dp))
            }
        }

        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { onExpandChange(false) }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option.name) },
                    onClick = {
                        onOptionSelected(option)
                        onExpandChange(false)
                    }
                )
            }
        }
    }
}

@Composable
fun RankingTable(rankings: List<RankItem>) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Cabeçalho da Tabela
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray.copy(alpha = 0.2f))
                .padding(vertical = 12.dp)
        ) {
            HeaderCell("RANKING", Modifier.weight(0.2f))
            HeaderCell("MÉDIA", Modifier.weight(0.2f))
            HeaderCell("NOME DO ALUNO", Modifier.weight(0.6f))
        }

        // Corpo da Tabela (LazyColumn preenchendo o restante)
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            itemsIndexed(rankings) { index, item ->
                val rowColor = if (item.isCurrentUser) PurplePrimary else Color.White
                val contentColor = if (item.isCurrentUser) Color.White else DarkGray

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                        .background(rowColor)
                        .border(
                            width = 1.dp,
                            color = if (item.isCurrentUser) PurplePrimary else Color(0xFFE1E4E7)
                        )
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ContentCell(item.ranking, Modifier.weight(0.2f), contentColor)
                    ContentCell(item.media, Modifier.weight(0.2f), contentColor)
                    // Célula do Nome, agora chamando a versão com Tarja
                    NameCell(item.nomeAluno, Modifier.weight(0.6f), item.isCurrentUser)
                }
            }
        }
    }
}

@Composable
fun HeaderCell(text: String, modifier: Modifier) {
    Text(
        text = text,
        modifier = modifier.padding(horizontal = 8.dp),
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        color = DarkGray,
        textAlign = TextAlign.Start
    )
}

@Composable
fun ContentCell(text: String, modifier: Modifier, color: Color, isBold: Boolean = false) {
    Text(
        text = text,
        modifier = modifier.padding(horizontal = 8.dp),
        fontWeight = if (isBold) FontWeight.SemiBold else FontWeight.Normal,
        fontSize = 14.sp,
        color = color,
        textAlign = TextAlign.Start
    )
}

// NOVO COMPONENTE: Simula a tarja de censura no nome.
@Composable
fun NameCell(
    text: String,
    modifier: Modifier,
    isCurrentUser: Boolean
) {
    val paddingModifier = modifier.padding(horizontal = 8.dp)

    if (isCurrentUser) {
        // Aluno logado: Exibe o nome normalmente, em branco (devido ao fundo roxo)
        Text(
            text = text,
            modifier = paddingModifier,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            color = Color.White,
            textAlign = TextAlign.Start
        )
    } else {
        // Outros alunos: Exibe uma tarja cinza claro sobre o espaço do nome.
        // O efeito da imagem de PC é uma tarja que cobre o nome.
        Box(
            modifier = paddingModifier
                .fillMaxHeight()
                .fillMaxWidth(0.9f) // Ocupa a maior parte da largura da célula, como uma tarja
        ) {
            // Tarja Cinza Clara (simulando a cor do desfoque/tarja)
            // Usamos DarkGray com baixa opacidade para simular o efeito "apagado"
            Spacer(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(4.dp)) // Borda sutilmente arredondada
                    .background(DarkGray.copy(alpha = 0.2f))
            )

            // Opcional: Adicionar um texto de placeholder/censurado leve para dar peso à tarja
            Text(
                text = "Censurado",
                color = DarkGray.copy(alpha = 0.0f), // Quase invisível, apenas para dar um tamanho base
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RankingScreenPreview() {
    RankingScreen(null)
}