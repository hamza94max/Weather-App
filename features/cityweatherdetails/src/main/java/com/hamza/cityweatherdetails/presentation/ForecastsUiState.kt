package com.hamza.cityweatherdetails.presentation

import com.hamza.data.remote.models.forecasts.DailyForecastData

data class ForecastsUiState(
    val dailyForecastsData : List<DailyForecastData> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
