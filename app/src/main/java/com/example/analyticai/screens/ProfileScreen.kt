package com.example.analyticai.screens.components

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.analyticai.ui.theme.DarkGray
import com.example.analyticai.ui.theme.GrayDarkMedium
import com.example.analyticai.ui.theme.PurplePrimary

@Composable
fun ProfileScreen(navegacao: NavHostController?) {
    var nomeUsuario: TextFieldValue by remember { mutableStateOf(TextFieldValue("")) }
    var emailUsuario: TextFieldValue by remember { mutableStateOf(TextFieldValue("")) }
    var telefoneUsuario: TextFieldValue by remember { mutableStateOf(TextFieldValue("")) }
    var isDarkMode by remember { mutableStateOf(false) }

    val context= LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        BarraSuperiorProfile(navegacao)

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 82.dp, start = 12.dp, end = 12.dp, bottom = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(15.dp))

            Text(
                "Perfil de (Nome do Aluno)",
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = Color(0xff5b5b5b)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings"
                )
                Text(
                    "Configurações do Sistema",
                    modifier = Modifier.padding(start = 5.dp),
                    fontSize = 18.sp,
                    color = GrayDarkMedium,
                    fontWeight = FontWeight.Normal
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Card com as informações
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xfffcf5ff)),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)) {
                    Text("Informações da Escola", fontWeight = FontWeight.Bold, color = GrayDarkMedium)

                    Spacer(modifier = Modifier.height(2.dp))

                    // Nome
                    OutlinedTextField(
                        value = nomeUsuario,
                        onValueChange = { nomeUsuario = it },
                        label = { Text("Nome do Usuário", fontWeight = FontWeight.ExtraLight) },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color(0xffF9FAFB),
                            unfocusedContainerColor = Color(0xffF9FAFB),
                            focusedBorderColor = Color(0xffE1E4E7),
                            unfocusedBorderColor = Color(0xffE1E4E7),
                            focusedLabelColor = Color(0xffC2ACAF),
                            unfocusedLabelColor = Color(0xffC2ACAF)
                        )
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Email
                    OutlinedTextField(
                        value = emailUsuario,
                        onValueChange = { emailUsuario = it },
                        label = { Text("Email de Contato", fontWeight = FontWeight.ExtraLight) },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color(0xffF9FAFB),
                            unfocusedContainerColor = Color(0xffF9FAFB),
                            focusedBorderColor = Color(0xffE1E4E7),
                            unfocusedBorderColor = Color(0xffE1E4E7),
                            focusedLabelColor = Color(0xffC2ACAF),
                            unfocusedLabelColor = Color(0xffC2ACAF)
                        )
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Telefone
                    OutlinedTextField(
                        value = telefoneUsuario,
                        onValueChange = { telefoneUsuario = it },
                        label = { Text("Telefone de Contato") },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color(0xffF9FAFB),
                            unfocusedContainerColor = Color(0xffF9FAFB),
                            focusedBorderColor = Color(0xffE1E4E7),
                            unfocusedBorderColor = Color(0xffE1E4E7),
                            focusedLabelColor = Color(0xffC2ACAF),
                            unfocusedLabelColor = Color(0xffC2ACAF)
                        )
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    Divider()

                    Spacer(modifier = Modifier.height(10.dp))

                    Text("Aparência", fontWeight = FontWeight.Medium, color = GrayDarkMedium)

                    Spacer(modifier = Modifier.height(10.dp))

                    Text("Modo dark", fontWeight = FontWeight.Normal, color = GrayDarkMedium)

                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            "Troca as cores da tela para um modo escuro.",
                            modifier = Modifier
                                .weight(1f),
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp,
                            color = GrayDarkMedium
                        )
                        Switch(
                            checked = isDarkMode,
                            onCheckedChange = { checked: Boolean -> isDarkMode = checked }
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                }

                // Botão Salvar
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(end = 10.dp, bottom = 15.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Button(
                        onClick = {},
                        modifier = Modifier
                            .height(50.dp)
                            .width(180.dp),
                        shape = RoundedCornerShape(28.dp),
                        colors = ButtonDefaults.buttonColors(PurplePrimary)
                    ) {
                        Text("Salvar Alterações", color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun BarraSuperiorProfile(navegacao: NavHostController?) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(top = 10.dp, start = 5.dp, end = 5.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
                    .padding(start = 10.dp, end = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Foto",
                    tint = DarkGray,
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text("Nome Do Aluno", fontWeight = FontWeight.Medium, fontSize = 18.sp, color = DarkGray)
                Text("1º Ano B", fontSize = 14.sp, color = DarkGray, fontWeight = FontWeight.Light)
            }

            Row {
                Icon(
                    imageVector = Icons.Default.ExitToApp,
                    contentDescription = "",
                    tint = PurplePrimary,
                    modifier = Modifier.padding(start = 125.dp)
                        .clickable { navegacao?.navigate("login") }
                )
            }

        }
        Spacer(modifier = Modifier.height(10.dp))

        Divider(modifier = Modifier.height(1.dp).width(500.dp))
    }
}

@Preview
@Composable
private fun ProfileScreenPreview() {
    ProfileScreen(null)
}
