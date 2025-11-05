package com.example.analyticai.screens.components

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.analyticai.data.ApiRepository
import com.example.analyticai.data.UserPreferences
import com.example.analyticai.service.RetrofitFactory
import com.example.analyticai.ui.theme.DarkGray
import com.example.analyticai.viewmodel.AlunoViewModel
import com.example.analyticai.viewmodel.AlunoViewModelFactory
import com.example.analyticai.viewmodel.LoginViewModel

@Composable
fun BarraSuperior() {
    val context = LocalContext.current

    // ✅ Cria ApiService e Repository corretamente
    val apiService = RetrofitFactory.getApiService()
    val repository = ApiRepository(apiService)

    // ✅ Passa o repository para o ViewModel via Factory
    val alunoViewModel: AlunoViewModel = viewModel (
        factory = AlunoViewModelFactory(repository)
    )

    val aluno by alunoViewModel.alunoLogado.collectAsState()
    val loginViewModel: LoginViewModel = viewModel(
        factory = LoginViewModel.provideFactory(context)
    )
    val userPrefs = remember { UserPreferences(context) }
    val usuario by loginViewModel.usuarioFlow().collectAsState(initial = null)

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(40.dp))

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
                Text(
                    text = usuario ?: "Carregando...",
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                    color = DarkGray
                )
                Text(
                    text = "1º Ano B",
                    fontSize = 14.sp,
                    color = DarkGray,
                    fontWeight = FontWeight.Light
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Divider(modifier = Modifier.height(1.dp).width(380.dp))
    }
}
