package com.hamza.data.remote.models.forecasts

import com.hamza.data.remote.models.currentWeather.Clouds
import com.hamza.data.remote.models.currentWeather.Coord
import com.hamza.data.remote.models.currentWeather.Main
import com.hamza.data.remote.models.currentWeather.Weather
import com.hamza.data.remote.models.currentWeather.Wind

data class ForecastsResponse(
    val city: City = City(),
    val cnt: Int = 0,
    val cod: String = "",
    val list: List<DailyForecastData> = listOf(),
    val message: Int = 0
)

data class City(
    val coord: Coord = Coord(),
    val country: String = "",
    val id: Int = 0,
    val name: String = "",
    val population: Int = 0,
    val sunrise: Int = 0,
    val sunset: Int = 0,
    val timezone: Int = 0
)

data class DailyForecastData(
    val clouds: Clouds = Clouds(),
    val dt: Int = 0,
    val dt_txt: String = "",
    val main: Main = Main(),
    val pop: Double = 0.0,
    val sys: Sys = Sys(),
    val visibility: Int = 0,
    val weather: List<Weather> = listOf(),
    val wind: Wind = Wind()
)

data class Sys(
    val pod: String = ""
)