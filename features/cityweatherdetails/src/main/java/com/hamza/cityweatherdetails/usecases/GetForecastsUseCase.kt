package com.hamza.cityweatherdetails.usecases

import com.hamza.data.remote.models.currentWeather.CurrentWeatherResponse
import com.hamza.data.remote.models.forecasts.ForecastsResponse
import com.hamza.data.repository.WeatherRepository
import javax.inject.Inject

class GetForecastsUseCase  @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(): ForecastsResponse {
        return repository.getSevenDaysForecasts()
    }
}