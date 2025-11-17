package com.example.analyticai.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import android.widget.Toast
import com.example.analyticai.viewmodel.LoginViewModel
import com.example.analyticai.data.SharedPreferencesManager

/**
 * Tela de Login principal do aplicativo.
 * Restringe o acesso apenas a usuários com nível 'aluno'.
 *
 * @param navegacao O controlador de navegação para mudar de tela após o login.
 */
@Composable
fun LoginScreen(navegacao: NavHostController?) {
    val context = LocalContext.current
    // FIX: Chamada correta do ViewModel injetado pelo Hilt.
    val loginViewModel: LoginViewModel = hiltViewModel()

    var credencial by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var lembrarCredenciais by remember { mutableStateOf(false) }
    var erroCredencial by remember { mutableStateOf<String?>(null) }
    var erroSenha by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    val sharedPrefsManager = remember { SharedPreferencesManager(context) }

    val backgroundColor = Color(0xFFF7F7F7)
    val cardColor = Color.White
    val primaryColor = Color(0xFF673AB7)

    Scaffold(containerColor = backgroundColor) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = cardColor),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(vertical = 32.dp)
            ) {
                Column(
                    modifier = Modifier.padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Text(
                        text = "Login",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    Text(
                        text = "Acesse sua conta de aluno", // Texto ajustado para refletir a restrição
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 32.dp)
                    )

                    OutlinedTextField(
                        value = credencial,
                        onValueChange = { credencial = it; erroCredencial = null },
                        label = { Text("Matrícula") },
                        placeholder = { Text("00000000") },
                        isError = erroCredencial != null,
                        supportingText = { erroCredencial?.let { Text(it) } },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = primaryColor,
                            unfocusedBorderColor = Color.LightGray,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = senha,
                        onValueChange = { senha = it; erroSenha = null },
                        label = { Text("Senha") },
                        visualTransformation = PasswordVisualTransformation(),
                        isError = erroSenha != null,
                        supportingText = { erroSenha?.let { Text(it) } },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = primaryColor,
                            unfocusedBorderColor = Color.LightGray,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black
                        )
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable { lembrarCredenciais = !lembrarCredenciais }
                        ) {
                            Checkbox(
                                checked = lembrarCredenciais,
                                onCheckedChange = { lembrarCredenciais = it },
                                colors = CheckboxDefaults.colors(checkedColor = primaryColor)
                            )
                            Text("Lembrar de mim", fontSize = 14.sp, color = Color.Black) // Aumentei um pouco a fonte para melhor legibilidade
                        }

                        TextButton(onClick = { navegacao?.navigate("recPasswd") }) {
                            Text("Esqueceu a senha?", fontSize = 14.sp, color = primaryColor) // Aumentei um pouco a fonte
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = {
                            erroCredencial = loginViewModel.validarCredencial(credencial)
                            erroSenha = loginViewModel.validarSenha(senha)

                            if (erroCredencial == null && erroSenha == null) {
                                isLoading = true
                                loginViewModel.login(
                                    credencial = credencial,
                                    senha = senha,
                                    // A assinatura corrigida agora recebe 'LoginResponse'
                                    onSuccess = { response ->
                                        isLoading = false
                                        // O response.usuario agora pode ser nulo (tratado abaixo)
                                        val usuarioLogado = response.usuario

                                        if (usuarioLogado == null) {
                                            // Caso o login falhe, mas o repository não lance exceção (e retorne null)
                                            Toast.makeText(context, "Credenciais inválidas ou erro no servidor.", Toast.LENGTH_LONG).show()
                                            return@login
                                        }

                                        // --- INÍCIO DA VALIDAÇÃO DE NÍVEL ---
                                        // Verifica se o nível do usuário é estritamente "aluno" (ignorando maiúsculas/minúsculas)
                                        if (usuarioLogado.nivel_usuario?.equals("aluno", ignoreCase = true) == true) {
                                            // É aluno: prossegue com o login
                                            if (lembrarCredenciais) {
                                                sharedPrefsManager.saveUserCredentials(
                                                    credencial,
                                                    usuarioLogado.nivel_usuario
                                                )
                                            } else {
                                                sharedPrefsManager.clearCredentials()
                                            }
                                            sharedPrefsManager.saveUsuario(usuarioLogado)

                                            Toast.makeText(context, "Bem-vindo(a), ${usuarioLogado.nome}!", Toast.LENGTH_SHORT).show()
                                            navegacao?.navigate("dashboard") {
                                                popUpTo("login") { inclusive = true }
                                            }
                                        } else {
                                            // Não é aluno: exibe mensagem de erro e não navega
                                            Toast.makeText(context, "Acesso restrito apenas para alunos.", Toast.LENGTH_LONG).show()
                                        }
                                        // --- FIM DA VALIDAÇÃO DE NÍVEL ---
                                    },
                                    onError = { mensagem ->
                                        isLoading = false
                                        Toast.makeText(context, mensagem, Toast.LENGTH_LONG).show()
                                    }
                                )
                            }
                        },
                        enabled = !isLoading,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = primaryColor)
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp)) // Mudei a cor do loading para branco para contrastar com o botão roxo
                        } else {
                            Text("Entrar", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun LoginScreenPreview() {
    LoginScreen(null)
}