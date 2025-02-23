package com.example.fitnessapp.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fitnessapp.FirebaseViewModel
import com.example.fitnessapp.data.UserProfile

@Composable
fun UserProfileScreen(
    navController: NavController,
    vm: FirebaseViewModel,
    modifier: Modifier = Modifier
) {
    var gender by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var goal by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Profil erstellen", fontSize = 30.sp, color = MaterialTheme.colorScheme.onBackground)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = gender,
            onValueChange = { gender = it },
            label = { Text("Geschlecht (m/w)") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(50.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("Alter") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = height,
            onValueChange = { height = it },
            label = { Text("Körpergröße (cm)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(50.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = weight,
            onValueChange = { weight = it },
            label = { Text("Körpergewicht (kg)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(50.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = goal,
            onValueChange = { goal = it },
            label = { Text("Ziel: Abnehmen oder Zunehmen") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(50.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val profile = UserProfile(
                    gender = gender,
                    age = age.toIntOrNull() ?: 0,
                    height = height.toFloatOrNull() ?: 0f,
                    weight = weight.toFloatOrNull() ?: 0f,
                    goal = goal
                )
                vm.saveUserProfile(profile) {
                    navController.navigate("home") {
                        popUpTo("userProfile") { inclusive = true }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Profil speichern")
        }
    }
}
