package com.example.fitnessapp.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fitnessapp.CalorieEntry
import com.example.fitnessapp.DestinationScreen
import com.example.fitnessapp.FirebaseViewModel
import com.example.fitnessapp.data.UserProfile

@Composable
fun HomeScreen(
    navController: NavController,
    vm: FirebaseViewModel,
    modifier: Modifier = Modifier
) {
    var userProfile by remember { mutableStateOf<UserProfile?>(null) }
    var calorieEntry by remember { mutableStateOf<CalorieEntry?>(null) } // Angenommen, CalorieEntry ist deine Datenklasse für Kalorienwerte

    LaunchedEffect(Unit) {
        vm.loadUserProfile { profile ->
            userProfile = profile
        }
        vm.loadTodayCalories { entry ->
            calorieEntry = entry
        }
    }

    val bmi = userProfile?.let { profile ->
        if (profile.height > 0f)
            profile.weight / ((profile.height / 100) * (profile.height / 100))
        else 0f
    } ?: 0f

    val recommendedCalories = userProfile?.let { profile ->
        if (profile.goal.lowercase() == "abnehmen") 2000 else 2500
    } ?: 0

    val actualCalories = calorieEntry?.let { entry ->
        entry.breakfast + entry.lunch + entry.dinner + entry.snack
    } ?: 0

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Home",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(160.dp))

        Text(
            text = "Kalorien: $actualCalories / $recommendedCalories",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "BMI: ${"%.1f".format(bmi)}",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        if(vm.signedIn.value){
            navController.navigate(DestinationScreen.Home.route)
        }
    }
}
