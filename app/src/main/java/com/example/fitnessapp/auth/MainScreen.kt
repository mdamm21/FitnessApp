package com.example.fitnessapp.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fitnessapp.DestinationScreen
import com.example.fitnessapp.FirebaseViewModel

@Composable
fun MainScreen (navController: NavController, vm:FirebaseViewModel){
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 100.dp)
    ){
        Text( text = "Willkommen",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp
        )
        Spacer(modifier = Modifier.height(160.dp))

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(50.dp))
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color(0xFFFFD700), Color.White, Color(0xFFFFD700))
                    )
                )
        ) {
            Button(onClick = {
                navController.navigate(DestinationScreen.SignUp.route)
            },
                colors = ButtonDefaults.buttonColors(
                    Color.Transparent
                ),
                modifier = Modifier.width(300.dp)
                ) {
                Text(
                    text = "Sign Up",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
                    )
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(50.dp))
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color(0xFFFFD700), Color.White, Color(0xFFFFD700))
                    )
                )
        ) {
            Button(onClick = {
                navController.navigate(DestinationScreen.LogIn.route)
            },
                colors = ButtonDefaults.buttonColors(
                    Color.Transparent
                ),
                modifier = Modifier.width(300.dp)
            ) {
                Text(
                    text = "Log In",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
                )
            }
        }
    }
}