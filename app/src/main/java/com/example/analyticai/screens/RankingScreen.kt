import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.analyticai.data.SharedPreferencesManager
import com.example.analyticai.model.Login.Usuario
import com.example.analyticai.model.Ranking.RankingItem
import com.example.analyticai.ui.theme.BackgroundLightPink
import com.example.analyticai.ui.theme.DarkGray
import com.example.analyticai.viewmodel.RankingViewModel
import com.example.analyticai.viewmodel.RankingViewModelFactory

// --- 2. Composable Principal (RankingScreen) ---
@Composable
fun RankingScreen(navegacao: NavHostController?) {



    val context = LocalContext.current
    val sharedPrefs = remember { SharedPreferencesManager(context) }
    val usuario: Usuario? = sharedPrefs.getUsuario()

    // Inicializa a ViewModel usando uma Factory para passar o Context
    val viewModel: RankingViewModel = viewModel(
        factory = RankingViewModelFactory(context)
    )

    val userName = usuario?.nome ?: "Usuário"

    // ⭐️ SIMULAÇÃO DE DADOS: Em um aplicativo real, estes dados viriam de um ViewModel
    val rankingList = remember {
        listOf(
            RankingItem(1, 9.8, "João Silva"),
            RankingItem(2, 9.5, "Maria Santos"),
            RankingItem(3, 9.2, "Meu Usuário Logado"), // Usuário a ser destacado/desborrado
            RankingItem(4, 8.7, "Pedro Costa"),
            RankingItem(5, 8.5, "Ana Oliveira"),
            RankingItem(6, 8.3, "Carlos Pereira"),
            RankingItem(7, 8.1, "Sofia Almeida"),
            RankingItem(8, 7.9, "Rui Martins"),
            RankingItem(9, 7.5, "Lúcia Fernandes"),
            RankingItem(10, 7.2, "Paulo Gomes"),
            RankingItem(11, 7.0, "Eva Rodrigues"),
        )
    }

    // --- Estilos ---
    val borderColor = Color(0x66615F5F)
    val highlightColor = Color(0xFFD8C7FF) // lilás do Figma
    val headerBg = Color(0xFFF3EDF7) // fundo do cabeçalho
    val userBgColor = Color(0xffDCCDFC) // Cor de fundo da linha do usuário

    Column(
        modifier = Modifier
            .fillMaxWidth()
            // ✅ CORREÇÃO 1: Preencher a altura máxima do container para o peso funcionar
            .fillMaxHeight()
            .padding(vertical = 16.dp, horizontal = 12.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, Color.Transparent, RoundedCornerShape(16.dp))
            .background(BackgroundLightPink)
    ) {
        // --- Cabeçalho Fixo ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(BackgroundLightPink)
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            @Composable
            fun HeaderCell(text: String) {
                Text(
                    text = text,
                    modifier = Modifier.padding(end = 5.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkGray
                )
            }

            HeaderCell("RANKING")
            HeaderCell("MÉDIA")
            HeaderCell("NOME DO ALUNO")
        }

        Divider(color = borderColor, thickness = 1.dp)

        // --- Conteúdo da Tabela (Lista Rolável) ---
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                // ✅ CORREÇÃO 2: Usar weight(1f) para ocupar o espaço restante
                .weight(1f)
                .padding(bottom = 8.dp)
        ) {
            items(rankingList) { item ->
                val isUser = item.nome == userName

                Row(
                    // ⭐️ Construção do Modifier com Aplicação Condicional
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(if (isUser) Color(0xffDCCDFC) else Color.Transparent)
                        .padding(vertical = 10.dp, horizontal = 4.dp)
                        // Aplica o blur (desfoque) SE NÃO for o usuário logado
                        .then(if (!isUser) Modifier.blur(8.dp) else Modifier),

                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    @Composable
                    fun TableCell(text: String) {
                        Text(
                            text = text,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp,
                            color = DarkGray
                        )
                    }

                    TableCell("${item.posicao}º")
                    TableCell(String.format("%.1f", item.media))
                    TableCell(item.nome)
                }

                Divider(color = borderColor, thickness = 1.dp)
            }
        }
    }
}

// --- 3. Preview Funcional ---
@Preview(showBackground = true)
@Composable
fun PreviewRankingScreen() {
        RankingScreen(navegacao = null)
}