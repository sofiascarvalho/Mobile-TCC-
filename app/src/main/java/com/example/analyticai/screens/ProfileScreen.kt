package com.example.analyticai.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.analyticai.screens.components.BarraInferior
import com.example.analyticai.screens.components.BarraSuperior

@Composable
fun ProfileScreen(navegacao: NavHostController?) {
    var nomeUsuario by remember { mutableStateOf(TextFieldValue("")) }
    var emailUsuario by remember { mutableStateOf(TextFieldValue("")) }
    var telefoneUsuario by remember { mutableStateOf(TextFieldValue("")) }
    var isDarkMode by remember { mutableStateOf(false) }

    val bgColor = MaterialTheme.colorScheme.background
    val cardColor = MaterialTheme.colorScheme.surface
    val textColor = MaterialTheme.colorScheme.onBackground
    val primaryColor = MaterialTheme.colorScheme.primary

    Scaffold(
        topBar = { BarraSuperior() },
        bottomBar = { BarraInferior(controleNavegacao = navegacao) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text("Perfil de \"Nome do Aluno\"", fontWeight = FontWeight.Medium, fontSize = 16.sp, color = Color(0xff5b5b5b))

            Spacer(modifier = Modifier.height(16.dp))
            Row (
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings"
                )
                Text("Configurações do Sistema", fontSize = 14.sp, color = Color(0xff5b5b5b), fontWeight = FontWeight.Normal)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Card com as informações
            Card(
                modifier = Modifier.fillMaxWidth()
                    .height(570.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xfffcf5ff)),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Informações da Escola", fontWeight = FontWeight.Bold)

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = nomeUsuario,
                        onValueChange = { nomeUsuario = it },
                        label = { Text("Nome do Usuário", fontWeight = FontWeight.ExtraLight) },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = emailUsuario,
                        onValueChange = { emailUsuario = it },
                        label = { Text("Email de Contato", fontWeight = FontWeight.ExtraLight ) },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = telefoneUsuario,
                        onValueChange = { telefoneUsuario = it },
                        label = { Text("Telefone de Contato") },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp)
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Divider()

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Aparência", fontWeight = FontWeight.Bold)

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Modo Dark", modifier = Modifier.weight(1f))
                        Switch(
                            checked = isDarkMode,
                            onCheckedChange = { isDarkMode = it }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(primaryColor)
                    ) {
                        Text("Salvar Alterações", color = Color.White)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ProfileScreenPreview() {
    ProfileScreen(null)
}

