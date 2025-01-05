package com.hamza.data.local

import android.content.SharedPreferences
import com.hamza.core.utils.Constants.CITY_KEY_PREF
import javax.inject.Inject

class PreferenceManager @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    fun saveCityInput(city: String) {
        sharedPreferences.edit().putString(CITY_KEY_PREF, city).apply()
    }


    fun getCityInput(): String? {
        return sharedPreferences.getString(CITY_KEY_PREF, "")
    }



}