package com.hamza.cityinput.usecases

import com.hamza.data.repository.WeatherRepository
import javax.inject.Inject

class SaveInputCityUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    operator fun invoke(cityName: String) {
        repository.saveCityInput(cityName)
    }
}
