package com.hamza.weatherapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hamza.cityinput.presentation.CityInputScreen
import com.hamza.cityweatherdetails.presentation.CityWeatherDetailsScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.CityInput.route) {
        composable(Routes.CityInput.route) {
            CityInputScreen(
                onCitySubmitted = {
                    navController.navigate(Routes.CityWeatherDetails.route)
                }
            )
        }
        composable(Routes.CityWeatherDetails.route) {
            CityWeatherDetailsScreen(
                onClick = {
                }
            )
        }
    }

}
