package com.hamza.cityinput.presentation


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamza.cityinput.usecases.SaveInputCityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityInputViewModel @Inject constructor(
    private val saveInputCityUseCase: SaveInputCityUseCase
) : ViewModel() {

    fun saveCityInSharedPref(city: String) {
        viewModelScope.launch {
            saveInputCityUseCase(city)
        }
    }
}
