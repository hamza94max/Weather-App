package com.hamza.data.remote

import com.hamza.data.remote.models.currentWeather.CurrentWeatherResponse
import com.hamza.data.remote.models.forecasts.ForecastsResponse
import javax.inject.Inject

class WeatherRemoteDataSource @Inject constructor (
    private val apiService: ApiService
){

    suspend fun getCurrentWeather(city: String): CurrentWeatherResponse {
        return apiService.getCurrentWeather(city)
    }


    suspend fun getSevenDaysForecasts(city: String): ForecastsResponse {
        return apiService.getSevenDaysForecasts(city)
    }



}