@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.fitnessapp.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fitnessapp.FirebaseViewModel

@Composable
fun KaloriesScreen(
    navController: NavController,
    vm: FirebaseViewModel,
    modifier: Modifier = Modifier
) {
    var breakfast by remember { mutableStateOf("") }
    var lunch by remember { mutableStateOf("") }
    var dinner by remember { mutableStateOf("") }
    var snack by remember { mutableStateOf("") }


    LaunchedEffect(Unit) {
        vm.loadTodayCalories { entry ->
            entry?.let {
                breakfast = it.breakfast.toString()
                lunch = it.lunch.toString()
                dinner = it.dinner.toString()
                snack = it.snack.toString()
            }
        }
    }

    val total = (breakfast.toIntOrNull() ?: 0) +
            (lunch.toIntOrNull() ?: 0) +
            (dinner.toIntOrNull() ?: 0) +
            (snack.toIntOrNull() ?: 0)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xAE78E17B))
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Kalorien: $total",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(160.dp))

        MealInputRow(label = "Frühstück", value = breakfast, onValueChange = { breakfast = it })
        MealInputRow(label = "Mittagessen", value = lunch, onValueChange = { lunch = it })
        MealInputRow(label = "Abendessen", value = dinner, onValueChange = { dinner = it })
        MealInputRow(label = "Snack", value = snack, onValueChange = { snack = it })

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            vm.saveCalories(breakfast, lunch, dinner, snack)
        }) {
            Text("Speichern")
        }
    }
}

@Composable
fun MealInputRow(label: String, value: String, onValueChange: (String) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = Color.White,
            modifier = Modifier.weight(1f)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White,
                cursorColor = Color.White,
            ),
            modifier = Modifier.weight(1f)
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
}