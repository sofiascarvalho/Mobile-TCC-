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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import android.widget.Toast
import com.example.analyticai.ViewModel.LoginViewModel // Importe seu ViewModel
import com.example.analyticai.data.SharedPreferencesManager // Importe seu Manager

/**
 * Tela de Login principal do aplicativo.
 * Contém a interface do usuário, a lógica de estado, validação e a chamada
 * para a função de login no LoginViewModel.
 *
 * @param navegacao O controlador de navegação para mudar de tela após o login.
 */
@Composable
fun LoginScreen(navegacao: NavHostController?) {
    val context = LocalContext.current
    // Inicializa o ViewModel usando a Factory customizada
    val loginViewModel: LoginViewModel = viewModel(
        factory = LoginViewModel.provideFactory()
    )

    // Estados para os campos de entrada (Matrícula/E-mail e Senha)
    var credencial by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }

    // Estado para o checkbox "Lembrar Credenciais"
    var lembrarCredenciais by remember { mutableStateOf(false) }

    // Estados para mensagens de erro nos campos
    var erroCredencial by remember { mutableStateOf<String?>(null) }
    var erroSenha by remember { mutableStateOf<String?>(null) }

    // Estado para controlar a exibição do indicador de loading
    var isLoading by remember { mutableStateOf(false) }

    // Instância do gerenciador de SharedPreferences para salvar a sessão
    val sharedPrefsManager = remember { SharedPreferencesManager(context) }

    // Cores baseadas no design (pode ser ajustado para usar seu Tema se preferir)
    val backgroundColor = Color(0xFFF7F7F7) // Fundo da tela
    val cardColor = Color.White // Cor do cartão
    val primaryColor = Color(0xFF673AB7) // Roxo primário para botões e foco

    Scaffold(containerColor = backgroundColor) { paddingValues ->
        // Box centraliza o conteúdo na tela
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            // Card arredondado que contém os campos de login
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = cardColor),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                modifier = Modifier
                    .fillMaxWidth(0.9f) // Ocupa 90% da largura
                    .padding(vertical = 32.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    // Título Principal
                    Text(
                        text = "Login",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    // Subtítulo
                    Text(
                        text = "Acesse sua conta para continuar",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 32.dp)
                    )

                    // Campo E-mail (usando 'credencial' internamente para matrícula/código)
                    OutlinedTextField(
                        value = credencial,
                        onValueChange = { credencial = it; erroCredencial = null },
                        label = { Text("Mátricula") },
                        placeholder = { Text("00000000") },
                        isError = erroCredencial != null,
                        supportingText = { erroCredencial?.let { Text(it) } },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = primaryColor,
                            unfocusedBorderColor = Color.LightGray,
                            // CORREÇÃO: Força o texto digitado a ser preto (Black)
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Campo Senha
                    OutlinedTextField(
                        value = senha,
                        onValueChange = { senha = it; erroSenha = null },
                        label = { Text("Senha") },
                        visualTransformation = PasswordVisualTransformation(), // Oculta a senha
                        isError = erroSenha != null,
                        supportingText = { erroSenha?.let { Text(it) } },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = primaryColor,
                            unfocusedBorderColor = Color.LightGray,
                            // CORREÇÃO: Força o texto digitado a ser preto (Black)
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black
                        )
                    )

                    // Linha para Lembrar Credenciais e Esqueci Minha Senha
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Checkbox Lembrar Credenciais
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { lembrarCredenciais = !lembrarCredenciais }) {
                            Checkbox(
                                checked = lembrarCredenciais,
                                onCheckedChange = { lembrarCredenciais = it },
                                colors = CheckboxDefaults.colors(checkedColor = primaryColor)
                            )
                            Text("Lembrar de mim", fontSize = 10.sp, color = Color.Black)
                        }

                        // Botão Esqueci Senha (Navega para a tela de recuperação)
                        TextButton(
                            onClick = {
                                // ⚠️ A rota "recuperacaoSenha" não está definida na sua MainActivity
                                navegacao?.navigate("recPasswd")
                            }
                        ) {
                            Text("Esqueceu a senha?", fontSize = 10.sp, color = primaryColor)
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // Botão de Login
                    Button(
                        onClick = {
                            // 1. Validação local dos campos
                            erroCredencial = loginViewModel.validarCredencial(credencial)
                            erroSenha = loginViewModel.validarSenha(senha)

                            if (erroCredencial == null && erroSenha == null) {
                                isLoading = true
                                // 2. Chamada à API de Login
                                loginViewModel.login(
                                    credencial = credencial,
                                    senha = senha,
                                    onSuccess = { response ->
                                        isLoading = false
                                        val usuarioLogado = response.usuario!!

                                        // 3. Salva a sessão se o checkbox estiver marcado
                                        if (lembrarCredenciais) {
                                            sharedPrefsManager.saveUserCredentials(
                                                credencial,
                                                usuarioLogado.nivel_usuario ?: "aluno"
                                            )
                                        } else {
                                            sharedPrefsManager.clearCredentials()
                                        }

                                        Toast.makeText(context, "Bem-vindo(a), ${usuarioLogado.nome}!", Toast.LENGTH_LONG).show()

                                        // 4. Navegação condicional baseada no Nível
                                        when (usuarioLogado.nivel_usuario?.lowercase()) {
                                            "aluno" -> navegacao?.navigate("dashboard") { popUpTo("login") { inclusive = true } }
                                            "professor" -> navegacao?.navigate("dashboard") { popUpTo("login") { inclusive = true } }
                                            "gestão" -> navegacao?.navigate("dashboard") { popUpTo("login") { inclusive = true } }
                                            else -> Toast.makeText(context, "Nível de usuário desconhecido.", Toast.LENGTH_LONG).show()
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
                        colors = ButtonDefaults.buttonColors(containerColor = primaryColor)
                    ) {
                        if (isLoading) {
                            // Indicador de progresso (loading)
                            CircularProgressIndicator(color = Color.Black, modifier = Modifier.size(24.dp))
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
    // Para ver o Preview, chame o Composable de LoginScreen
    LoginScreen(null)
}