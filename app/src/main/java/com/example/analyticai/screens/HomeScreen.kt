package com.example.analyticai.screens

import InfoCard
import android.widget.Space
import androidx.collection.mutableObjectIntMapOf
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.analyticai.R
import com.example.analyticai.ui.theme.Roboto


@Composable
fun HomeScreen() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // =========================
        // Seção 1: Cabeçalho + Conteúdo principal
        // =========================
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 5.dp, end = 5.dp, top = 18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Analytic AI",
                color = Color(0xff7D53F3),
                fontFamily = Roboto,
                fontWeight = FontWeight.ExtraBold
            )
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(Color(0xff7D53F3))
            ) {
                Text(
                    text = "Login",
                    fontWeight = FontWeight.Normal,
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(200.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "A inteligência que\ntransforma dados em\nresultados",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = Roboto,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Transformando dados em \naprendizagem inteligente.",
                fontSize = 12.sp,
                fontWeight = FontWeight.ExtraLight,
                fontFamily = Roboto,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(15.dp))

            Button(
                onClick = {},
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(2.dp, color = Color(0xff3347B0)),
                colors = ButtonDefaults.buttonColors(Color(0xff7D53F3)),
                modifier = Modifier.width(300.dp)
            ) {
                Text(
                    text = "Começar agora",
                    color = Color.White,
                    fontWeight = FontWeight.Normal
                )
            }
        }

        Spacer(modifier = Modifier.height(340.dp))

        // =========================
        // Seção 2: Imagem de fundo com conteúdo sobreposto
        // =========================
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(770.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.imagem_fundo),
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.height(60.dp))
                Text(
                    text = "Nossos Recursos",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(70.dp))

                // Zigzag cards
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center
                ) {
                    // Card 1 → esquerda
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        InfoCard(
                            image = R.drawable.grafic,
                            title = "Dashboard de Performance",
                            description = "Descrição do Card 1")
                    }

                    // Card 2 → direita
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        InfoCard(
                            image = R.drawable.notes,
                            title = "Planos Personalizados",
                            description = "Descrição do Card 2")
                    }

                    // Card 3 → esquerda
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        InfoCard(
                            image = R.drawable.notification,
                            title = "Notificações Inteligentes",
                            description = "Descrição do Card 3")
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
            }


        }


        Spacer(modifier = Modifier.height(40.dp))

        // =========================
        // Seção 3: Nova seção abaixo da imagem
        // =========================
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .height(740.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Benefícios para todos",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Famílias",
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(R.string.Família),
                fontSize = 8.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.ExtraLight,
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            Text(
                text = "Escolas",
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(R.string.Escolas),
                fontSize = 8.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.ExtraLight
            )

            Text(
                text = "Alunos",
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(R.string.Alunos),
                fontSize = 8.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.ExtraLight
            )

            Divider(modifier = Modifier.width(220.dp).height(2.dp))

        }
    }
}

@Preview(showBackground = true, heightDp = 4000)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
