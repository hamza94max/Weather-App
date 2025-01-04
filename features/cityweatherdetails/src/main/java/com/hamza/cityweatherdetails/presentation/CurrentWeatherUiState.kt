package com.hamza.cityweatherdetails.presentation

data class CurrentWeatherUiState(
    val cityName: String = "",
    val temperature: Double = 0.0,
    val condition: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)
