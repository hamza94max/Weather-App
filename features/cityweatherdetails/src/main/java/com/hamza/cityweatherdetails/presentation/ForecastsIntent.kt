package com.hamza.cityweatherdetails.presentation

sealed class ForecastsIntent {
    object FetchSevenForecasts : ForecastsIntent()
}