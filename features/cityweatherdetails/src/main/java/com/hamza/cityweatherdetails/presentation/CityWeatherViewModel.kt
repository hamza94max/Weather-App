package com.hamza.cityweatherdetails.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamza.cityweatherdetails.usecases.GetCurrentWeatherUseCase
import com.hamza.cityweatherdetails.usecases.GetForecastsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow


@HiltViewModel
class CityWeatherViewModel @Inject constructor(
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val getForecastsUseCase: GetForecastsUseCase
) : ViewModel() {

    init {
        viewModelScope.launch {
            delay(100)
            fetchCurrentWeather()
            processSevenForecastsIntent()
        }
    }

    private val _currentWeatherUiState = MutableStateFlow(CurrentWeatherUiState())
    val currentWeatherUiState: StateFlow<CurrentWeatherUiState> = _currentWeatherUiState

    fun fetchCurrentWeather() {
        viewModelScope.launch {
            _currentWeatherUiState.value = _currentWeatherUiState.value.copy(isLoading = true)
            try {
                val weather = getCurrentWeatherUseCase()
                _currentWeatherUiState.value = CurrentWeatherUiState(
                    cityName = weather.name,
                    temperature = weather.main.temp,
                    condition = weather.weather[0].main,
                    isLoading = false
                )
            } catch (e: Exception) {
                _currentWeatherUiState.value =
                    _currentWeatherUiState.value.copy(error = e.message.toString(), isLoading = false)
            }
        }
    }

    private val _sevenForecastsUiState = MutableStateFlow(ForecastsUiState())
    val sevenForecastsUiState: StateFlow<ForecastsUiState> = _sevenForecastsUiState

    val sevenForecastsIntent = Channel<ForecastsIntent>(Channel.UNLIMITED)
    fun processSevenForecastsIntent() {
        viewModelScope.launch {
            sevenForecastsIntent.consumeAsFlow().collect {
                when (it) {
                    is ForecastsIntent.FetchSevenForecasts -> fetchSevenDaysForecasts()
                }
            }
        }
    }

    private fun fetchSevenDaysForecasts() {
        viewModelScope.launch {
            _sevenForecastsUiState.value = _sevenForecastsUiState.value.copy(isLoading = true)
            try {
                val forecasts = getForecastsUseCase()
                _sevenForecastsUiState.value = ForecastsUiState(
                    isLoading = false,
                    dailyForecastsData = forecasts.list
                )
            } catch (e: Exception) {
                _sevenForecastsUiState.value =
                    _sevenForecastsUiState.value.copy(isLoading = false, error = e.message)
            }
        }
    }
}

