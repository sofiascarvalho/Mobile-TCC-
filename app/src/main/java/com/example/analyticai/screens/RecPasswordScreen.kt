   package com.example.analyticai.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.analyticai.ui.theme.BackgroundGeneral
import com.example.analyticai.ui.theme.GrayBlueLight
import com.example.analyticai.ui.theme.GrayExtraLight
import com.example.analyticai.ui.theme.PurplePrimary

   @Composable
   fun RecPasswd(navegacao: NavHostController?) {
       val colorScheme = MaterialTheme.colorScheme

       Box(
           modifier = Modifier
               .fillMaxSize()
               .background(colorScheme.background)
       ) {
           Column(
               modifier = Modifier.fillMaxSize(),
               verticalArrangement = Arrangement.Center,
               horizontalAlignment = Alignment.CenterHorizontally
           ) {
               Card(
                   modifier = Modifier
                       .width(350.dp)
                       .height(300.dp),
                   elevation = CardDefaults.cardElevation(2.dp),
                   colors = CardDefaults.cardColors(
                       containerColor = colorScheme.surface
                   ),
                   shape = RoundedCornerShape(16.dp)
               ) {
                   Column(
                       modifier = Modifier
                           .fillMaxSize()
                           .padding(horizontal = 25.dp, vertical = 20.dp)
                   ) {
                       Row(
                           verticalAlignment = Alignment.CenterVertically
                       ) {
                           Icon(
                               imageVector = Icons.Default.ArrowBack,
                               contentDescription = "",
                               modifier = Modifier
                                   .clickable { navegacao?.popBackStack() },
                               tint = colorScheme.onSurface
                           )
                           Spacer(modifier = Modifier.width(30.dp))
                           Text(
                               text = "Recuperar Senha",
                               fontSize = 18.sp,
                               fontWeight = FontWeight.Bold,
                               color = colorScheme.onSurface
                           )
                       }

                       Spacer(modifier = Modifier.height(20.dp))
                       Text(
                           text = "Digite sua matrícula para enviarmos um e-mail de recuperação.",
                           color = colorScheme.onSurface.copy(alpha = 0.9f),
                           fontSize = 14.sp,
                           modifier = Modifier.padding(end = 30.dp)
                       )

                       Spacer(modifier = Modifier.height(25.dp))
                       Text(
                           text = "Matrícula",
                           color = colorScheme.onSurface,
                           fontWeight = FontWeight.SemiBold
                       )
                       Spacer(modifier = Modifier.height(8.dp))
                       OutlinedTextField(
                           value = "",
                           onValueChange = {},
                           placeholder = { Text("Sua Matrícula") },
                           modifier = Modifier.fillMaxWidth(),
                           colors = OutlinedTextFieldDefaults.colors(
                               focusedContainerColor = colorScheme.surface,
                               unfocusedContainerColor = colorScheme.surface,
                               focusedBorderColor = colorScheme.primary,
                               unfocusedBorderColor = colorScheme.secondary
                           ),
                           shape = RoundedCornerShape(12.dp)
                       )

                       Spacer(modifier = Modifier.height(30.dp))
                       Button(
                           onClick = { navegacao?.navigate("email") },
                           colors = ButtonDefaults.buttonColors(
                               containerColor = colorScheme.primary
                           ),
                           shape = RoundedCornerShape(13.dp),
                           modifier = Modifier.fillMaxWidth()
                       ) {
                           Text(text = "Enviar", color = colorScheme.onPrimary)
                       }
                   }
               }
           }
       }
   }


@Composable
fun ConfirmEmail(navegacao: NavHostController?) {
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(GrayExtraLight),
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
                    Spacer(modifier = Modifier.height(30.dp))
                    Text(
                        text = "Um e-mail para redefinição de senha foi enviado para seu e-mail educacional. Por favor, verifique a caixa de entrada.",
                        modifier = Modifier.padding(horizontal = 70.dp)
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    Button(
                        onClick = {
                            navegacao!!.navigate("login")
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
   fun RedefinirPasswd(navegacao: NavHostController?) {
       val colorScheme = MaterialTheme.colorScheme

       Box(
           modifier = Modifier
               .fillMaxSize()
               .background(colorScheme.background)
       ) {
           Column(
               modifier = Modifier.fillMaxSize(),
               verticalArrangement = Arrangement.Center,
               horizontalAlignment = Alignment.CenterHorizontally
           ) {
               Card(
                   modifier = Modifier
                       .width(350.dp)
                       .height(360.dp),
                   elevation = CardDefaults.cardElevation(2.dp),
                   colors = CardDefaults.cardColors(
                       containerColor = colorScheme.surface
                   ),
                   shape = RoundedCornerShape(16.dp)
               ) {
                   Column(
                       modifier = Modifier
                           .fillMaxSize()
                           .padding(horizontal = 25.dp, vertical = 20.dp)
                   ) {
                       Row(
                           verticalAlignment = Alignment.CenterVertically
                       ) {
                           Icon(
                               imageVector = Icons.Default.ArrowBack,
                               contentDescription = "",
                               modifier = Modifier
                                   .clickable { navegacao?.popBackStack() },
                               tint = colorScheme.onSurface
                           )
                           Spacer(modifier = Modifier.width(30.dp))
                           Text(
                               text = "Redefinir Senha",
                               fontSize = 18.sp,
                               fontWeight = FontWeight.Bold,
                               color = colorScheme.onSurface
                           )
                       }

                       Spacer(modifier = Modifier.height(20.dp))
                       Text(
                           text = "Crie sua nova senha",
                           color = colorScheme.onSurface.copy(alpha = 0.9f),
                           fontSize = 14.sp
                       )

                       Spacer(modifier = Modifier.height(25.dp))
                       Text(
                           text = "Nova Senha",
                           color = colorScheme.onSurface,
                           fontWeight = FontWeight.SemiBold
                       )
                       Spacer(modifier = Modifier.height(8.dp))
                       OutlinedTextField(
                           value = "",
                           onValueChange = {},
                           placeholder = { Text("********") },
                           modifier = Modifier.fillMaxWidth(),
                           visualTransformation = PasswordVisualTransformation(),
                           colors = OutlinedTextFieldDefaults.colors(
                               focusedContainerColor = colorScheme.surface,
                               unfocusedContainerColor = colorScheme.surface,
                               focusedBorderColor = colorScheme.primary,
                               unfocusedBorderColor = colorScheme.secondary
                           ),
                           shape = RoundedCornerShape(12.dp)
                       )

                       Spacer(modifier = Modifier.height(16.dp))
                       Text(
                           text = "Confirmar Senha",
                           color = colorScheme.onSurface,
                           fontWeight = FontWeight.SemiBold
                       )
                       Spacer(modifier = Modifier.height(8.dp))
                       OutlinedTextField(
                           value = "",
                           onValueChange = {},
                           placeholder = { Text("********") },
                           modifier = Modifier.fillMaxWidth(),
                           visualTransformation = PasswordVisualTransformation(),
                           colors = OutlinedTextFieldDefaults.colors(
                               focusedContainerColor = colorScheme.surface,
                               unfocusedContainerColor = colorScheme.surface,
                               focusedBorderColor = colorScheme.primary,
                               unfocusedBorderColor = colorScheme.secondary
                           ),
                           shape = RoundedCornerShape(12.dp)
                       )

                       Spacer(modifier = Modifier.height(30.dp))
                       Button(
                           onClick = { navegacao?.navigate("confirmRedefinicao") },
                           colors = ButtonDefaults.buttonColors(
                               containerColor = colorScheme.primary
                           ),
                           shape = RoundedCornerShape(13.dp),
                           modifier = Modifier.fillMaxWidth()
                       ) {
                           Text(text = "Redefinir", color = colorScheme.onPrimary)
                       }
                   }
               }
           }
       }
   }


@Composable
fun ConfirmRedefinicao(navegacao: NavHostController?) {
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(GrayExtraLight),
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


   @Preview
   @Composable
   private fun RedefPasswdPreview() {
       RedefinirPasswd(null)
   }   
   
   
   /*
@Preview
@Composable
private fun RecPasswdPreview() {
    RecPasswd(null)
}*/


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