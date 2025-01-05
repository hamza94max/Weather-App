package com.hamza.data.remote

import com.google.common.truth.Truth.assertThat
import com.hamza.data.remote.models.currentWeather.CurrentWeatherResponse
import com.hamza.data.remote.models.currentWeather.Main
import com.hamza.data.remote.models.currentWeather.Weather
import com.hamza.data.remote.models.forecasts.City
import com.hamza.data.remote.models.forecasts.DailyForecastData
import com.hamza.data.remote.models.forecasts.ForecastsResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherRemoteDataSourceTest {

    private val mockApiService: ApiService = mockk()
    private lateinit var remoteDataSource: WeatherRemoteDataSource

    @Before
    fun setUp() {
        remoteDataSource = WeatherRemoteDataSource(apiService = mockApiService)
    }

    @Test
    fun `getCurrentWeather returns current weather data`() = runTest {
        val mockResponse = CurrentWeatherResponse(
            name = "Test City",
            main = Main(temp = 25.0),
            weather = listOf(Weather(main = "Clear"))
        )
        coEvery { mockApiService.getCurrentWeather("Test City") } returns mockResponse

        val result = remoteDataSource.getCurrentWeather("Test City")

        assertThat(result).isEqualTo(mockResponse)
        coVerify { mockApiService.getCurrentWeather("Test City") }
    }

    @Test
    fun `getSevenDaysForecasts returns 7-day forecast data`() = runTest {
        val mockForecastResponse = ForecastsResponse(
            city = City(name = "Test City"),
            list = listOf(
                DailyForecastData(main = Main(temp = 15.0)),
                DailyForecastData(main = Main(temp = 18.0))
            )
        )
        coEvery { mockApiService.getSevenDaysForecasts("Test City") } returns mockForecastResponse

        val result = remoteDataSource.getSevenDaysForecasts("Test City")

        assertThat(result).isEqualTo(mockForecastResponse)
        coVerify { mockApiService.getSevenDaysForecasts("Test City") }
    }
}