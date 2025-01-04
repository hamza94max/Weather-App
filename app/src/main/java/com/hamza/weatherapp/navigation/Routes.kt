package com.hamza.weatherapp.navigation

sealed class Routes(val route: String) {
    object CityInput : Routes("city_input")
    object CityWeatherDetails : Routes("current_weather")
}