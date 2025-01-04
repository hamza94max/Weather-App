package com.hamza.cityweatherdetails.usecases

import com.hamza.data.remote.models.currentWeather.CurrentWeatherResponse
import com.hamza.data.repository.WeatherRepository
import javax.inject.Inject

class GetCurrentWeatherUseCase  @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(): CurrentWeatherResponse {
        return  repository.getCurrentWeather()
    }
}