package com.example.weatherapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.presentation.LoginScreen
import com.example.weatherapp.presentation.SignupScreen
import com.example.weatherapp.presentation.WeatherPage
import com.example.weatherapp.viewmodel.AuthViewModel
import com.example.weatherapp.viewmodel.WeatherViewModel

@Composable
fun AppNavigation(modifier: Modifier = Modifier, authViewModel: AuthViewModel , weatherViewModel: WeatherViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login", builder = {

        composable ("login"){
            LoginScreen(modifier, navController, authViewModel)
        }

        composable ("signup"){
            SignupScreen(modifier, navController, authViewModel)
        }
        
        composable("home") {
            WeatherPage(
                viewModel = weatherViewModel,
                authViewModel = authViewModel,
                navController = navController
            )
        }

    })
    
}