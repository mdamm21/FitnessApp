package com.example.fitnessapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.fitnessapp.pages.HomeScreen
import com.example.fitnessapp.pages.KaloriesScreen
import com.example.fitnessapp.pages.PlanScreen

@Composable
fun BottomBarMainScreen(navController: NavController,
                        vm: FirebaseViewModel,
                        modifier: Modifier = Modifier
) {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf("Tracken", "Home", "Pläne")
    val selectedIcons = listOf(Icons.Filled.Add, Icons.Filled.Home, Icons.Filled.DateRange)
    val unselectedIcons =
        listOf(Icons.Outlined.Add, Icons.Outlined.Home, Icons.Outlined.DateRange)

    Scaffold (
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                if (selectedItem == index) selectedIcons[index] else unselectedIcons[index],
                                contentDescription = item
                            )
                        },
                        label = { Text(item) },
                        selected = selectedItem == index,
                        onClick = { selectedItem = index }
                    )
                }
            }
        }

    ) { innerPadding ->
        ContentScreen(
            selectedItem = selectedItem,
            navController = navController,
            vm = vm,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun ContentScreen(
    modifier: Modifier = Modifier,
    selectedItem: Int,
    navController: NavController,
    vm:FirebaseViewModel
){
    when(selectedItem){
        1-> HomeScreen(navController, vm, modifier)
        0-> KaloriesScreen(navController, vm , modifier)
        2-> PlanScreen(navController, vm, modifier)
    }

}