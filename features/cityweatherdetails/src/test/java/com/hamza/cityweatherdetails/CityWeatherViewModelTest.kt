package com.hamza.cityweatherdetails

import com.google.common.truth.Truth.assertThat
import com.hamza.cityweatherdetails.presentation.CityWeatherViewModel
import com.hamza.cityweatherdetails.presentation.CurrentWeatherUiState
import com.hamza.cityweatherdetails.usecases.GetCurrentWeatherUseCase
import com.hamza.cityweatherdetails.usecases.GetForecastsUseCase
import com.hamza.data.remote.models.currentWeather.CurrentWeatherResponse
import com.hamza.data.remote.models.currentWeather.Main
import com.hamza.data.remote.models.currentWeather.Weather
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CityWeatherViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val mockGetCurrentWeatherUseCase: GetCurrentWeatherUseCase = mockk()
    private val mockGetForecastsUseCase: GetForecastsUseCase = mockk()
    private lateinit var viewModel: CityWeatherViewModel

    @Before
    fun setUp() {
        viewModel = CityWeatherViewModel(
            getCurrentWeatherUseCase = mockGetCurrentWeatherUseCase,
            getForecastsUseCase = mockGetForecastsUseCase
        )
    }

    @Test
    fun `fetchCurrentWeather sets loading state and updates UI state on success`() = runTest {
        val stateList = mutableListOf<CurrentWeatherUiState>()

        val job = launch {
            viewModel.currentWeatherUiState.collect { stateList.add(it) }
        }

        val mockWeather = CurrentWeatherResponse(
            name = "Test City",
            main = Main(temp = 25.0),
            weather = listOf(Weather(main = "Clear"))
        )
        coEvery { mockGetCurrentWeatherUseCase() } returns mockWeather

        viewModel.fetchCurrentWeather()

        advanceUntilIdle()

        assertThat(stateList.last().isLoading).isFalse()
        assertThat(stateList.last().cityName).isEqualTo("Test City")
        assertThat(stateList.last().temperature).isEqualTo(25.0)

        job.cancel()
    }


    @Test
    fun `fetchCurrentWeather sets error state on failure`() = runTest {

        coEvery { mockGetCurrentWeatherUseCase() } throws Exception("Network error")

        viewModel.fetchCurrentWeather()

        advanceUntilIdle()

        val state = viewModel.currentWeatherUiState.value
        assertThat(state.isLoading).isFalse()
        assertThat(state.error).isEqualTo("Network error")
    }
}
