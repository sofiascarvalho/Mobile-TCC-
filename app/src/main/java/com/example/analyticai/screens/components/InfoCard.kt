package com.example.analyticai.screens.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.analyticai.ui.theme.GrayDarkMedium

@Composable
fun InfoCard(
    matricula: String = "Matricula: ",
    data_nascimento: String = "Data Nascimento: ",
    responsavel: String = "Responsável: ",
    contato: String = "Contato: ",
    email: String = "Email: "
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row (
            modifier = Modifier
                .padding(start = 8.dp, top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "Info"
            )
            Text(
                "Informações Gerais",
                modifier = Modifier.padding(start = 3.dp),
                fontSize = 12.sp,
                color = GrayDarkMedium,
                fontWeight = FontWeight.Medium
            )
        }
        Column (
            modifier = Modifier
                .padding(start = 10 .dp, top = 8.dp)
        ){
            Text(text = matricula, fontSize = 10.sp)
            Text(text = data_nascimento, fontSize = 10.sp)
            Text(text = responsavel, fontSize = 10.sp)
            Text(text = contato, fontSize = 10.sp)
            Text(text = email, fontSize = 10.sp)
        }
    }
}

@Preview
@Composable
private fun InfoScreenPreview() {
    InfoCard()
}