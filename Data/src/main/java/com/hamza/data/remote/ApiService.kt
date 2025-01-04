package com.hamza.data.remote

import com.hamza.core.utils.Constants.API_KEY
import com.hamza.data.remote.models.currentWeather.CurrentWeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {


    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("q") city: String,
    ): CurrentWeatherResponse





}