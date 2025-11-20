package com.example.analyticai.screens

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.analyticai.data.SharedPreferencesManager
import com.example.analyticai.model.Dashboards.Materia
import com.example.analyticai.model.Dashboards.Semestre
import com.example.analyticai.model.Login.Usuario
import com.example.analyticai.ui.theme.DarkGray
import com.example.analyticai.ui.theme.PurplePrimary
import com.example.analyticai.screens.components.FilterDropdown
import com.example.analyticai.viewmodel.FiltrosViewModel
import com.example.analyticai.viewmodel.RankingViewModel
import com.example.analyticai.viewmodel.RankingViewModelFactory

// --- Mock Data Structures e Data (Mantidos) ---
data class RankItem(
    val ranking: String,
    val media: String,
    val nomeAluno: String,
    val isCurrentUser: Boolean = false
)

@Composable
fun RankingScreen(navegacao: NavHostController?) {

    val filtrosViewModel: FiltrosViewModel = hiltViewModel()
    val context = LocalContext.current
    val sharedPrefs = remember { SharedPreferencesManager(context) }
    val usuario: Usuario? = sharedPrefs.getUsuario()
    val rankingViewModel: RankingViewModel = viewModel(factory = RankingViewModelFactory(context))

    val userName = usuario?.nome ?: "Usuário"

    val materias by filtrosViewModel.materias.collectAsState()
    val semestres by filtrosViewModel.semestres.collectAsState()
    val isFiltrosLoading by filtrosViewModel.isLoading.collectAsState()

    var selectedMateria by remember { mutableStateOf<Materia?>(null) }
    var selectedSemestre by remember { mutableStateOf<Semestre?>(null) }

    val rankingItems = rankingViewModel.rankingItems
    val isRankingLoading = rankingViewModel.isLoading
    val rankingError = rankingViewModel.errorMessage

    val currentAlunoName = "$userName"

    val showRanking = selectedMateria != null && selectedSemestre != null

    LaunchedEffect(selectedMateria?.id_materia, selectedSemestre?.id_semestre) {
        val materiaId = selectedMateria?.id_materia
        val semestreId = selectedSemestre?.id_semestre
        if (materiaId != null && semestreId != null) {
            rankingViewModel.loadRanking(materiaId, semestreId)
        }
    }

    val tableItems = remember(rankingItems, usuario) {
        val currentName = usuario?.nome.orEmpty()
        rankingItems.map { item ->
            RankItem(
                ranking = "${item.posicao}º",
                media = String.format("%.1f", item.media),
                nomeAluno = item.nome,
                isCurrentUser = item.nome.equals(currentName, ignoreCase = true)
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // 1. Título e Subtítulo
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Ranking Do Aluno",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 22.sp,
                color = PurplePrimary
            )
            Divider(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(),
                color = Color(0xFFE0E3EB),
                thickness = 1.dp
            )
            Text(
                text = "Visão geral da sua classificação em relação aos colegas.",
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(20.dp))
        }

        // 2. Informações do Aluno e Filtros (Reorganizado para mobile)
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)) {

            if (isFiltrosLoading) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = PurplePrimary
                )
            }

            // B. Filtros
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                val materiaLabel = selectedMateria?.materia ?: if (materias.isEmpty()) "Carregando..." else "Selecione"
                FilterDropdown(
                    label = "Matéria",
                    selectedValue = materiaLabel,
                    options = materias.map { it.materia },
                    onSelect = { selectedName ->
                        selectedMateria = materias.find { it.materia == selectedName }
                    },
                    modifier = Modifier.weight(1f)
                )

                val semestreLabel = selectedSemestre?.semestre ?: if (semestres.isEmpty()) "Carregando..." else "Selecione"
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
        }

        Spacer(modifier = Modifier.height(20.dp))

        Divider(color = Color(0xFFE1E4E7))

        Spacer(modifier = Modifier.height(20.dp))

        // 3. Exibição Condicional da Tabela/Mensagem
        Box(modifier = Modifier.fillMaxSize()) {
            if (showRanking) {
                when {
                    isRankingLoading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            color = PurplePrimary
                        )
                    }

                    tableItems.isNotEmpty() -> {
                        RankingTable(rankings = tableItems)
                    }

                    else -> {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.Center)
                                .padding(bottom = 100.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = rankingError
                                    ?: "Não encontramos dados para os filtros selecionados.",
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Medium,
                                color = Color.Gray,
                                fontSize = 16.sp,
                                modifier = Modifier.padding(horizontal = 32.dp)
                            )
                        }
                    }
                }
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

@Composable
fun RankingTable(rankings: List<RankItem>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {
            RankingHeader()
            Divider(color = Color(0xFFE4E6EF), thickness = 1.dp, modifier = Modifier.padding(top = 12.dp))

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                itemsIndexed(rankings) { index, item ->
                    RankingRow(item = item)
                    if (index != rankings.lastIndex) {
                        Divider(
                            color = Color(0xFFEDEFF5),
                            thickness = 1.dp,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun RankingHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        HeaderCell("RANKING", Modifier.weight(0.25f))
        HeaderCell("MÉDIA", Modifier.weight(0.25f))
        HeaderCell("NOME DO ALUNO", Modifier.weight(0.5f))
    }
}

@Composable
fun HeaderCell(text: String, modifier: Modifier) {
    Text(
        text = text,
        modifier = modifier,
        fontWeight = FontWeight.Bold,
        fontSize = 13.sp,
        color = DarkGray.copy(alpha = 0.8f),
        textAlign = TextAlign.Start
    )
}

@Composable
fun RankingRow(item: RankItem) {
    val shape = RoundedCornerShape(32.dp)
    val isCurrent = item.isCurrentUser
    val background = if (isCurrent) PurplePrimary else Color.Transparent
    val borderColor = if (isCurrent) PurplePrimary else Color.Transparent
    val textColor = if (isCurrent) Color.White else DarkGray

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(background)
            .border(1.dp, borderColor, shape)
            .padding(horizontal = 20.dp, vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ContentCell(item.ranking, Modifier.weight(0.25f), textColor, isBold = true)
        ContentCell(item.media, Modifier.weight(0.25f), textColor, isBold = true)
        NameCell(
            text = item.nomeAluno,
            modifier = Modifier.weight(0.5f),
            isCurrentUser = isCurrent
        )
    }
}

@Composable
fun ContentCell(text: String, modifier: Modifier, color: Color, isBold: Boolean = false) {
    Text(
        text = text,
        modifier = modifier,
        fontWeight = if (isBold) FontWeight.SemiBold else FontWeight.Normal,
        fontSize = 14.sp,
        color = color,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
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
        Text(
            text = text,
            modifier = paddingModifier,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    } else {
        BlurredNameText(
            text = text,
            modifier = paddingModifier
        )
    }
}

@Composable
private fun BlurredNameText(text: String, modifier: Modifier = Modifier) {
    val baseModifier = modifier.fillMaxWidth()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        Text(
            text = text,
            modifier = baseModifier.blur(10.dp),
            color = DarkGray,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    } else {
        Text(
            text = "••••••••••",
            modifier = baseModifier,
            color = DarkGray.copy(alpha = 0.6f),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
