package com.example.fitnessapp.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fitnessapp.FirebaseViewModel

@Composable
fun PlanScreen(navController: NavController, vm: FirebaseViewModel, modifier: Modifier){
    Column (
        modifier = modifier.fillMaxSize()
            .background(Color(0xAE78E17B)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ){
        Text("Pläne",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFFFFFFF)
        )
    }
}