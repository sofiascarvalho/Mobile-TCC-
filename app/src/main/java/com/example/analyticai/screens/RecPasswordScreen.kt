package com.example.analyticai.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.analyticai.ui.theme.GrayExtraLight
import com.example.analyticai.ui.theme.PurplePrimary

@Composable
fun RecPasswd(modifier: Modifier = Modifier) {
    
}

@Composable
fun ConfirmEmail(navegacao: NavHostController?) {
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Column (
            modifier = Modifier.fillMaxSize().background(GrayExtraLight),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Card(
                modifier = Modifier.size(350.dp, 300.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column (
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(
                        text = "E-mail Enviado!",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(45.dp))
                    Text(
                        text = "Um e-mail para redefinição de senha foi enviado para seu e-mail educacional. Por favor, verifique a caixa de entrada.",
                        modifier = Modifier.padding(horizontal = 70.dp)
                    )
                    Spacer(modifier = Modifier.height(45.dp))
                    Button(
                        onClick = {
                            navegacao!!.navigate("home")
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PurplePrimary
                        )
                    ) {
                        Text(
                            text = "Voltar para o login"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RedefinirPasswd(modifier: Modifier = Modifier) {
    
}

@Composable
fun ConfirmRedefinicao(navegacao: NavHostController?) {
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Column (
            modifier = Modifier.fillMaxSize().background(GrayExtraLight),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Card(
                modifier = Modifier.size(350.dp, 300.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column (
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(
                        text = "Senha Redefinida!",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(45.dp))
                    Text(
                        text = "Sua senha foi redefinida com sucesso. Você já pode voltar para a tela de login.",
                        modifier = Modifier.padding(horizontal = 50.dp)
                    )
                    Spacer(modifier = Modifier.height(45.dp))
                    Button(
                        onClick = {
                            navegacao!!.navigate("home")
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PurplePrimary
                        )
                    ) {
                        Text(
                            text = "Voltar para o login"
                        )
                    }
                }
            }
        }
    }
}



/*
@Preview
@Composable
private fun ConfirmEmailPreview() {
    ConfirmEmail(null)
}*/


/*
@Preview
@Composable
private fun ConfirmRedefPreview() {
    ConfirmRedefinicao(null)
}*/