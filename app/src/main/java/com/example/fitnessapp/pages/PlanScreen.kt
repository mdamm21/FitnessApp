package com.example.fitnessapp.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fitnessapp.FirebaseViewModel
import com.example.fitnessapp.data.UserProfile
import java.util.*

@Composable
fun PlanScreen(
    navController: NavController,
    vm: FirebaseViewModel,
    modifier: Modifier = Modifier
) {
    var userProfile by remember { mutableStateOf<UserProfile?>(null) }
    LaunchedEffect(Unit) {
        vm.loadUserProfile { profile ->
            userProfile = profile
        }
    }

    val defaultDays = listOf("Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag", "Sonntag")

    val currentDay = getCurrentDayName()

    val orderedDays = reorderWeekDays(currentDay, defaultDays)

    val trainingPlan = remember(userProfile) {
        userProfile?.let { profile ->
            val bmi = if (profile.height > 0f) {
                profile.weight / ((profile.height / 100) * (profile.height / 100))
            } else 0f
            if (bmi >= 25f) {
                mapOf(
                    "Montag" to "Cardio & Intervalltraining",
                    "Dienstag" to "Ganzkörper-Workout",
                    "Mittwoch" to "HIIT",
                    "Donnerstag" to "Leichtes Cardio",
                    "Freitag" to "Intervalltraining",
                    "Samstag" to "Ausdauertraining",
                    "Sonntag" to "Erholung"
                )
            } else {
                mapOf(
                    "Montag" to "Brust & Trizeps",
                    "Dienstag" to "Rücken & Bizeps",
                    "Mittwoch" to "Beine & Bauch",
                    "Donnerstag" to "Schultern & Core",
                    "Freitag" to "Brust & Trizeps",
                    "Samstag" to "Rücken & Bizeps",
                    "Sonntag" to "Erholung"
                )
            }
        } ?: emptyMap()
    }

    val progressMap = remember { mutableStateMapOf<String, Boolean>() }
    LaunchedEffect(Unit) {
        vm.loadPlanProgress { progress ->
            if (progress != null && progress.isNotEmpty()) {
                progressMap.clear()
                progressMap.putAll(progress)
            } else {
                defaultDays.forEach { day ->
                    progressMap[day] = false
                }
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xAE78E17B))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Text(
            text = "Trainingsplan",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(160.dp))
        orderedDays.forEach { day ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = day,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = trainingPlan[day] ?: "",
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
                val checked = progressMap[day] ?: false
                Checkbox(
                    checked = checked,
                    onCheckedChange = { isChecked ->
                        progressMap[day] = isChecked
                        vm.savePlanProgress(progressMap.toMap())
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color.Green,
                        uncheckedColor = Color.White
                    )
                )
            }
        }
    }
}

fun getCurrentDayName(): String {
    val calendar = Calendar.getInstance()
    return when (calendar.get(Calendar.DAY_OF_WEEK)) {
        Calendar.MONDAY -> "Montag"
        Calendar.TUESDAY -> "Dienstag"
        Calendar.WEDNESDAY -> "Mittwoch"
        Calendar.THURSDAY -> "Donnerstag"
        Calendar.FRIDAY -> "Freitag"
        Calendar.SATURDAY -> "Samstag"
        Calendar.SUNDAY -> "Sonntag"
        else -> ""
    }
}

fun reorderWeekDays(currentDay: String, days: List<String>): List<String> {
    val index = days.indexOf(currentDay)
    return if (index != -1) {
        days.subList(index, days.size) + days.subList(0, index)
    } else {
        days
    }
}
