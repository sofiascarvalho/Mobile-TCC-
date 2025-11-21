package com.example.analyticai.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.analyticai.data.SharedPreferencesManager
import com.example.analyticai.viewmodel.ProfileViewModel
import com.example.analyticai.viewmodel.ProfileViewModelFactory

@Composable
fun ProfileScreen(navegacao: NavHostController?) {
    // Manter estados e contexto
    val context = LocalContext.current
    val profileViewModel: ProfileViewModel = viewModel(factory = ProfileViewModelFactory(context))
    val userName = profileViewModel.nome
    val emailUsuario = profileViewModel.email
    val telefoneUsuario = profileViewModel.telefone
    val successMessage = profileViewModel.successMessage
    val errorMessage = profileViewModel.errorMessage
    val isSaving = profileViewModel.isLoading
    val hasChanges = profileViewModel.hasChanges

    val sharedPrefsManager = remember { SharedPreferencesManager(context) }

    var isDarkMode by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(scrollState) // A rolagem agora abrange todo o conteúdo
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        // Título Principal - Configurações do Aluno
        Text(
            text = "Configurações Do Aluno",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.Black
        )
        Text(
            text = "Gerencie suas informações e preferências do sistema",
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(25.dp))

        // Card principal contendo as seções E O BOTÃO SALVAR
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {

                // 1. Informações do Aluno
                Text(
                    "Informações do Aluno",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = PurplePrimary
                )

                Spacer(modifier = Modifier.height(16.dp))

                successMessage?.let {
                    Text(
                        text = it,
                        color = Color(0xFF0F9D58),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                errorMessage?.let {
                    Text(
                        text = it,
                        color = Color(0xFFD93025),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                // Nome
                OutlinedTextField(
                    value = userName,
                    onValueChange = { profileViewModel.updateNome(it) },
                    label = { Text("Nome do aluno", fontWeight = FontWeight.Normal) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = PurplePrimary,
                        unfocusedBorderColor = Color(0xffE1E4E7),
                        focusedLabelColor = PurplePrimary,
                        unfocusedLabelColor = Color.Gray
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Email
                OutlinedTextField(
                    value = emailUsuario,
                    onValueChange = { profileViewModel.updateEmail(it) },
                    label = { Text("Email de Contato", fontWeight = FontWeight.Normal) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = PurplePrimary,
                        unfocusedBorderColor = Color(0xffE1E4E7),
                        focusedLabelColor = PurplePrimary,
                        unfocusedLabelColor = Color.Gray
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Telefone
                OutlinedTextField(
                    value = telefoneUsuario,
                    onValueChange = { profileViewModel.updateTelefone(it) },
                    label = { Text("Telefone de Contato", fontWeight = FontWeight.Normal) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = PurplePrimary,
                        unfocusedBorderColor = Color(0xffE1E4E7),
                        focusedLabelColor = PurplePrimary,
                        unfocusedLabelColor = Color.Gray
                    )
                )

                Spacer(modifier = Modifier.height(30.dp))

                Divider()

                Spacer(modifier = Modifier.height(30.dp))

                // 2. Preferências do Aluno
                Text(
                    "Preferências do Aluno",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = PurplePrimary
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Modo Dark
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        "Modo Dark",
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "Troca as cores da tela para um modo escuro.",
                            modifier = Modifier.weight(1f),
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Switch(
                            checked = isDarkMode,
                            onCheckedChange = { checked: Boolean -> isDarkMode = checked },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,          // Cor da bolinha ligada
                                uncheckedThumbColor = Color.LightGray,    // Cor da bolinha desligada
                                checkedTrackColor = PurplePrimary,        // Cor da trilha ligada
                                uncheckedTrackColor = Color.Transparent,  // Cor da trilha desligada
                                checkedBorderColor = PurplePrimary,
                                uncheckedBorderColor = Color.LightGray
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))

                Divider()

                Spacer(modifier = Modifier.height(30.dp))


                // Botão "Salvar Alterações" (no rodapé do Card, alinhado à direita)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = { profileViewModel.salvarPerfil() },
                        modifier = Modifier
                            .height(45.dp)
                            .width(180.dp),
                        shape = RoundedCornerShape(8.dp),
                        enabled = hasChanges && !isSaving,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PurplePrimary,
                            disabledContainerColor = PurplePrimary.copy(alpha = 0.5f)
                        )
                    ) {
                        if (isSaving) {
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier.size(20.dp),
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text("Salvar Alterações", color = Color.White)
                        }
                    }
                }
            }
        }

        // --- BOTÃO SAIR DA CONTA (FORA DO CARD) ---
        Spacer(modifier = Modifier.height(30.dp))

        // Botão "Sair da conta" (alinhado à esquerda e fora do Card)
        Button(
            onClick = {
                sharedPrefsManager.clearCredentials()
                navegacao?.navigate("login") {
                    popUpTo("dashboard") { inclusive = true }
                }
            },
            modifier = Modifier
                .height(45.dp)
                .align(Alignment.CenterHorizontally), // Alinha o botão à esquerda dentro da Column
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, Color.LightGray),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent, // Fundo transparente
                contentColor = Color.Black
            ),
            elevation = ButtonDefaults.buttonElevation(0.dp) // Sem elevação
        ) {
            Text("Sair da conta", color = Color.Gray, fontWeight = FontWeight.Medium)
        }

        // Espaçador final
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Preview
@Composable
private fun ProfileScreenPreview() {
    ProfileScreen(null)
}