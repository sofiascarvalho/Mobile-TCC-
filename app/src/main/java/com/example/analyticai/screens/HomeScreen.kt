package com.example.analyticai.screens

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
        // Cabeçalho
        // =========================
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
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

        // =========================
        // Conteúdo principal
        // =========================
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "A inteligência que\ntransforma dados em\nresultados",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = Roboto,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Transformando dados em aprendizagem inteligente.",
                fontSize = 12.sp,
                fontWeight = FontWeight.ExtraLight,
                fontFamily = Roboto,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(30.dp))

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

        Spacer(
            modifier = Modifier.height(355.dp)
        )
        // =========================
        // Segunda seção com imagem de fundo
        // =========================
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(600.dp) // altura da seção que será coberta pela imagem
        ) {
            // Imagem de fundo
            Image(
                painter = painterResource(R.drawable.imagem_fundo),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
            )

            // Conteúdo sobre a imagem
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Seção adicional sobre a imagem",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Aqui você pode adicionar mais textos, botões ou outros elementos sobre a imagem.",
                    fontSize = 14.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {},
                    modifier = Modifier.width(200.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xffFFC23D))
                ) {
                    Text("Outro botão")
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp)) // espaço final
    }
}



@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}