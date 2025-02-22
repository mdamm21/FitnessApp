package com.example.fitnessapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fitnessapp.auth.LogInScreen
import com.example.fitnessapp.auth.MainScreen
import com.example.fitnessapp.auth.SignUpScreen
import com.example.fitnessapp.main.NotificationMessage
import com.example.fitnessapp.pages.HomeScreen
import com.example.fitnessapp.pages.UserProfileScreen
import com.example.fitnessapp.ui.theme.FitnessAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            window.statusBarColor = getColor(R.color.black)
            window.navigationBarColor = getColor(R.color.black)
            FitnessAppTheme {

                Authentication()
            }
        }
    }
}

sealed class DestinationScreen(val route: String){
    object Main: DestinationScreen(route = "main")
    object SignUp: DestinationScreen(route = "SignUp")
    object LogIn: DestinationScreen(route = "LogIn")
    object Home: DestinationScreen(route = "Home")
    object UserProfile: DestinationScreen(route = "User Profile")
}

@Composable
fun Authentication(){
    val vm = hiltViewModel<FirebaseViewModel>()
    val navController = rememberNavController()

    NotificationMessage(vm)

    NavHost(navController = navController, startDestination = DestinationScreen.Main.route){
        composable(DestinationScreen.Main.route){
            MainScreen(navController, vm)
        }
        composable(DestinationScreen.SignUp.route){
            SignUpScreen(navController, vm)
        }
        composable(DestinationScreen.LogIn.route){
            LogInScreen(navController, vm)
        }
        composable(DestinationScreen.Home.route) {
            BottomBarMainScreen(navController = navController, vm = vm, modifier = Modifier)
        }
        composable(DestinationScreen.UserProfile.route){
            UserProfileScreen(navController = navController, vm = vm, modifier = Modifier)
        }
    }
}