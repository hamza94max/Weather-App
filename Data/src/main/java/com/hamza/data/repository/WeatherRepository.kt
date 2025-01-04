package com.hamza.data.repository

import com.hamza.data.local.PreferenceManager
import com.hamza.data.remote.WeatherRemoteDataSource
import com.hamza.data.remote.models.currentWeather.CurrentWeatherResponse
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val remoteDataSource: WeatherRemoteDataSource,
    private val preferenceManager: PreferenceManager
) {

    fun saveCityInput(city: String) {
        preferenceManager.saveCityInput(city)
    }

    fun getCityInput(): String? {
        return preferenceManager.getCityInput()
    }

    suspend fun getCurrentWeather(city: String): CurrentWeatherResponse {
        return remoteDataSource.getCurrentWeather(city)
    }










}