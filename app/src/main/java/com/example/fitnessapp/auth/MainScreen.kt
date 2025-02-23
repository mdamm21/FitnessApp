package com.example.fitnessapp.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fitnessapp.DestinationScreen
import com.example.fitnessapp.FirebaseViewModel

@Composable
fun MainScreen(navController: NavController, vm: FirebaseViewModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 100.dp)
    ) {
        Text(
            text = "Willkommen",
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = MaterialTheme.typography.headlineLarge.fontWeight,
            fontSize = 40.sp,
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(160.dp))
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(50.dp))
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.surface,
                            MaterialTheme.colorScheme.primary
                        )
                    )
                )
        ) {
            Button(
                onClick = {
                    navController.navigate(DestinationScreen.SignUp.route)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier.width(300.dp)
            ) {
                Text(
                    text = "Sign Up",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = MaterialTheme.typography.labelLarge.fontWeight,
                    fontSize = 30.sp,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(50.dp))
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.surface,
                            MaterialTheme.colorScheme.primary
                        )
                    )
                )
        ) {
            Button(
                onClick = {
                    navController.navigate(DestinationScreen.LogIn.route)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier.width(300.dp)
            ) {
                Text(
                    text = "Log In",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = MaterialTheme.typography.labelLarge.fontWeight,
                    fontSize = 30.sp,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}