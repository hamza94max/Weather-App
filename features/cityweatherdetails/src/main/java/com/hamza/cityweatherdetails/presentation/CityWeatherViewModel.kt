package com.hamza.cityweatherdetails.presentation


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamza.cityweatherdetails.usecases.GetCurrentWeatherUseCase
import com.hamza.cityweatherdetails.usecases.GetForecastsUseCase
import com.hamza.data.remote.models.forecasts.ForecastsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


@HiltViewModel
class CityWeatherViewModel @Inject constructor(
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val getForecastsUseCase: GetForecastsUseCase
) : ViewModel() {

    private val _currentWeatherUiState = MutableStateFlow(CurrentWeatherUiState())
    val currentWeatherUiState: StateFlow<CurrentWeatherUiState> = _currentWeatherUiState

    fun fetchCurrentWeather() {
        viewModelScope.launch {
            _currentWeatherUiState.value = _currentWeatherUiState.value.copy(isLoading = true)
            try {
                val weather = getCurrentWeatherUseCase()
                _currentWeatherUiState.value = CurrentWeatherUiState(
                    cityName = weather.sys.country,
                    temperature = weather.main.temp ,
                    condition = weather.weather[0].main,
                    isLoading = false
                )
            } catch (e: Exception) {
                _currentWeatherUiState.value = _currentWeatherUiState.value.copy(error = e.message, isLoading = false)
            }
        }
    }

    private val _sevenForecastsUiState = MutableStateFlow(ForecastsUiState())
    val sevenForecastsUiState: StateFlow<ForecastsUiState> = _sevenForecastsUiState

    fun fetchSevenForecasts() {
        viewModelScope.launch {
            _sevenForecastsUiState.value = _sevenForecastsUiState.value.copy(isLoading = true)
            try {
                val weather = getForecastsUseCase()
                _sevenForecastsUiState.value = ForecastsUiState(
                    dailyForecastsData = weather.list,
                    isLoading = false
                )
            } catch (e: Exception) {
                _sevenForecastsUiState.value = _sevenForecastsUiState.value.copy(error = e.message, isLoading = false)
            }
        }
    }
}

