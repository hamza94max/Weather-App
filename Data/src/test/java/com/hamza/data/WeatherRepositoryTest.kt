package com.hamza.data

import com.hamza.data.local.PreferenceManager
import com.hamza.data.remote.WeatherRemoteDataSource
import com.hamza.data.repository.WeatherRepository
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import com.google.common.truth.Truth.assertThat
import com.hamza.data.remote.models.currentWeather.CurrentWeatherResponse
import com.hamza.data.remote.models.currentWeather.Main
import com.hamza.data.remote.models.currentWeather.Weather
import com.hamza.data.remote.models.forecasts.DailyForecastData
import com.hamza.data.remote.models.forecasts.ForecastsResponse
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.verify
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherRepositoryTest {

    private val mockRemoteDataSource: WeatherRemoteDataSource = mockk()
    private val mockLocalDataSource: PreferenceManager = mockk()
    private lateinit var repository: WeatherRepository

    @Before
    fun setUp() {
        repository = WeatherRepository(
            remoteDataSource = mockRemoteDataSource,
            preferenceManager = mockLocalDataSource
        )
    }

    @Test
    fun `saveCityInput saves city input to SharedPreferences`() = runTest {
        val cityInput = "Test City"
        every { mockLocalDataSource.saveCityInput(cityInput) } just Runs
        repository.saveCityInput(cityInput)
        verify { mockLocalDataSource.saveCityInput(cityInput) }
    }

    @Test
    fun `getCityInput returns city input from SharedPreferences`() = runTest {
        val cityInput = "Test City"
        every { mockLocalDataSource.getCityInput() } returns cityInput
        val result = repository.getCityInput()
        assertThat(result).isEqualTo(cityInput)
        verify { mockLocalDataSource.getCityInput() }
    }

    @Test
    fun `getCurrentWeather returns weather data from remote data source`() = runTest {
        val mockWeatherResponse = CurrentWeatherResponse(
            name = "Test City",
            main = Main(temp = 25.0),
            weather = listOf(Weather(main = "Clear"))
        )
        coEvery { mockRemoteDataSource.getCurrentWeather("Test City") } returns mockWeatherResponse

        every { mockLocalDataSource.getCityInput() } returns "Test City"

        val result = repository.getCurrentWeather()

        assertThat(result).isEqualTo(mockWeatherResponse)

        coEvery { mockRemoteDataSource.getCurrentWeather("Test City") }
    }

    @Test
    fun `getSevenDaysForecasts returns forecasts data from remote data source`() = runTest {
        val mockForecastsResponse = ForecastsResponse(
            list = listOf(DailyForecastData(dt_txt = "Monday", main = Main(temp = 24.0)))
        )
        coEvery { mockRemoteDataSource.getSevenDaysForecasts("Test City") } returns mockForecastsResponse
        every { mockLocalDataSource.getCityInput() } returns "Test City"
        val result = repository.getSevenDaysForecasts()
        assertThat(result).isEqualTo(mockForecastsResponse)
        coEvery { mockRemoteDataSource.getSevenDaysForecasts("Test City") }
    }
}