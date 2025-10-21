package com.example.analyticai.screens

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.analyticai.ui.theme.PurplePrimary
import com.example.analyticai.viewmodel.LoginViewModel

@Composable
fun LoginScreen(navegacao: NavHostController?) {
    val context = LocalContext.current
    val loginViewModel: LoginViewModel = viewModel()

    val matriculaState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val rememberMe = remember { mutableStateOf(false) }

    var erroSenha by remember { mutableStateOf<String?>("") }
    var erroMatricula by remember { mutableStateOf<String?>("") }

    // Criar canal de notificação (necessário no Android 8+)
    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "LOGIN_CHANNEL_ID",
                "Canal de Login",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notificações relacionadas ao login"
            }
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Login",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Acesse sua conta para continuar",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                // Matrícula
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 5.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text("Matrícula")
                }
                OutlinedTextField(
                    value = matriculaState.value,
                    onValueChange = {
                        matriculaState.value = it
                        erroMatricula = null},
                    label = { Text("0000000000", fontSize = 14.sp) },
                    isError = erroMatricula!=null,
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
                if (erroMatricula!= null){
                    Text(
                        text = erroMatricula!!,
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(start = 5.dp, top = 2.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Senha
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 5.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text("Senha")
                }
                OutlinedTextField(
                    value = passwordState.value,
                    onValueChange = { passwordState.value = it
                                    erroSenha = null},
                    label = { Text("•••••••••••", fontSize = 14.sp) },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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
                if (erroSenha != null){
                    Text(
                        text = erroSenha!!,
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(start = 5.dp, top = 2.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Lembrar de mim + Esqueceu a senha
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = rememberMe.value,
                            onCheckedChange = { rememberMe.value = it }
                        )
                        Text("Lembrar de mim", fontSize = 14.sp)
                    }
                    TextButton(onClick = { navegacao?.navigate("recPasswd") }) {
                        Text(
                            "Esqueceu a senha?",
                            fontSize = 10.sp,
                            textDecoration = TextDecoration.Underline
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val erroC = loginViewModel.validarCredencial(matriculaState.value)
                        val erroS = loginViewModel.validarSenha(passwordState.value)
                        if (erroC!=null){
                            erroMatricula = erroC
                        }else if(erroS!=null){
                            erroSenha = erroS
                        }else{
                            loginViewModel.login(
                                credencial = matriculaState.value,
                                senha = passwordState.value,
                                onSuccess = {
                                    showLoginNotification(context)
                                    Toast.makeText(context, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show()
                                    navegacao?.navigate("dashboard")
                                },
                                onError = { mensagem ->
                                    Toast.makeText(context, mensagem, Toast.LENGTH_SHORT).show()
                                }
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary)
                ) {
                    Text("Entrar", fontSize = 18.sp, color = Color.White)
                }
            }
        }
    }
}

// 🔔 Exibe notificação de login com sucesso
fun showLoginNotification(context: Context) {
    val builder = NotificationCompat.Builder(context, "LOGIN_CHANNEL_ID")
        .setSmallIcon(android.R.drawable.ic_dialog_info)
        .setContentTitle("Login realizado")
        .setContentText("Bem-vindo de volta!")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setAutoCancel(true)

    with(NotificationManagerCompat.from(context)) {
        notify(1, builder.build())
    }
}

@Preview(showSystemUi = true)
@Composable
private fun LoginScreenPreview() {
    LoginScreen(null)
}
