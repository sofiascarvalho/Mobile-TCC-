package com.example.analyticai.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
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

    val colorScheme = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Scaffold(containerColor = colorScheme.background) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colorScheme.surface,
                    contentColor = colorScheme.onSurface
                ),
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
                        style = typography.titleLarge?.copy(fontWeight = FontWeight.Bold)
                            ?: LocalTextStyle.current.copy(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                        color = colorScheme.onSurface,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    Text(
                        text = "Acesse sua conta de aluno",
                        style = typography.bodyMedium,
                        color = colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(bottom = 32.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    OutlinedTextField(
                        value = credencial,
                        onValueChange = { credencial = it; erroCredencial = null },
                        label = { Text("Matrícula", color = colorScheme.onSurfaceVariant) },
                        placeholder = { Text("00000000", color = colorScheme.onSurfaceVariant) },
                        isError = erroCredencial != null,
                        supportingText = {
                            erroCredencial?.let { Text(it, color = colorScheme.error) }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colorScheme.primary,
                            unfocusedBorderColor = colorScheme.outline,
                            cursorColor = colorScheme.primary,
                            focusedTextColor = colorScheme.onSurface,
                            unfocusedTextColor = colorScheme.onSurface,
                            focusedLabelColor = colorScheme.primary,
                            unfocusedLabelColor = colorScheme.onSurfaceVariant,
                            focusedContainerColor = colorScheme.surfaceVariant,
                            unfocusedContainerColor = colorScheme.surfaceVariant,
                            disabledContainerColor = colorScheme.surfaceVariant,
                            errorContainerColor = colorScheme.surfaceVariant
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = senha,
                        onValueChange = { senha = it; erroSenha = null },
                        label = { Text("Senha", color = colorScheme.onSurfaceVariant) },
                        visualTransformation = PasswordVisualTransformation(),
                        isError = erroSenha != null,
                        supportingText = {
                            erroSenha?.let { Text(it, color = colorScheme.error) }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colorScheme.primary,
                            unfocusedBorderColor = colorScheme.outline,
                            cursorColor = colorScheme.primary,
                            focusedTextColor = colorScheme.onSurface,
                            unfocusedTextColor = colorScheme.onSurface,
                            focusedLabelColor = colorScheme.primary,
                            unfocusedLabelColor = colorScheme.onSurfaceVariant,
                            focusedContainerColor = colorScheme.surfaceVariant,
                            unfocusedContainerColor = colorScheme.surfaceVariant,
                            disabledContainerColor = colorScheme.surfaceVariant,
                            errorContainerColor = colorScheme.surfaceVariant
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
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier.clickable { lembrarCredenciais = !lembrarCredenciais }
                        ) {
                            Checkbox(
                                checked = lembrarCredenciais,
                                onCheckedChange = { lembrarCredenciais = it },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = colorScheme.primary,
                                    uncheckedColor = colorScheme.outline,
                                    checkmarkColor = colorScheme.onPrimary
                                )
                            )
                            Text(
                                "Lembrar de mim",
                                fontSize = 14.sp,
                                color = colorScheme.onSurface
                            )
                        }
                    }

                    TextButton(
                        onClick = { navegacao?.navigate("recovery") },
                        colors = ButtonDefaults.textButtonColors(contentColor = colorScheme.primary)
                    ) {
                        Text("Esqueceu a senha?", fontSize = 14.sp, textDecoration = TextDecoration.Underline)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            erroCredencial = loginViewModel.validarCredencial(credencial)
                            erroSenha = loginViewModel.validarSenha(senha)

                            if (erroCredencial == null && erroSenha == null) {
                                isLoading = true
                                loginViewModel.login(
                                    credencial = credencial,
                                    senha = senha,
                                    onSuccess = { response ->
                                        isLoading = false
                                        val usuarioLogado = response.usuario

                                        if (usuarioLogado == null) {
                                            Toast.makeText(context, "Credenciais inválidas ou erro no servidor.", Toast.LENGTH_LONG).show()
                                            return@login
                                        }

                                        if (usuarioLogado.nivel_usuario?.equals("aluno", ignoreCase = true) == true) {
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
                                            Toast.makeText(context, "Acesso restrito apenas para alunos.", Toast.LENGTH_LONG).show()
                                        }
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
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorScheme.primary,
                            contentColor = colorScheme.onPrimary,
                            disabledContainerColor = colorScheme.primary.copy(alpha = 0.4f),
                            disabledContentColor = colorScheme.onPrimary.copy(alpha = 0.6f)
                        )
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                color = colorScheme.onPrimary,
                                modifier = Modifier.size(24.dp)
                            )
                        } else {
                            Text("Entrar", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
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