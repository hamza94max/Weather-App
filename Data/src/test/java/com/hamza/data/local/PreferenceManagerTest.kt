package com.hamza.data.local

import android.content.SharedPreferences
import com.hamza.core.utils.Constants.CITY_KEY_PREF
import io.mockk.*
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class PreferenceManagerTest {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var preferenceManager: PreferenceManager

    @Before
    fun setUp() {
        // Create a mock of SharedPreferences
        sharedPreferences = mockk(relaxed = true)

        // Initialize the PreferenceManager with the mocked SharedPreferences
        preferenceManager = PreferenceManager(sharedPreferences)
    }

    @Test
    fun `saveCityInput saves city input to SharedPreferences`() {
        val cityInput = "Test City"

        // Create a mock of the SharedPreferences.Editor
        val editor = mockk<SharedPreferences.Editor>(relaxed = true)

        // Mock the behavior of sharedPreferences.edit() to return the mocked editor
        every { sharedPreferences.edit() } returns editor

        // Call the method to save the city input
        preferenceManager.saveCityInput(cityInput)

        // Verify that sharedPreferences.edit() was called
        verify { sharedPreferences.edit() }

        // Verify that putString() was called with the correct parameters
        verify { editor.putString(CITY_KEY_PREF, cityInput).apply() }

    }

    @Test
    fun `getCityInput retrieves city input from SharedPreferences`() {
        val cityInput = "Test City"

        // Mock the behavior of getString for the "CITY_KEY_PREF" key
        every { sharedPreferences.getString(CITY_KEY_PREF, "") } returns cityInput

        // Call the method to retrieve the city input
        val result = preferenceManager.getCityInput()

        // Assert the retrieved city input is correct
        assertEquals(cityInput, result)
    }

    @Test
    fun `getCityInput returns empty string when no city is saved`() {
        // Mock the behavior of getString when there is no value for the "CITY_KEY_PREF" key
        every { sharedPreferences.getString(CITY_KEY_PREF, "") } returns ""

        // Call the method to retrieve the city input
        val result = preferenceManager.getCityInput()

        // Assert that an empty string is returned when no city is saved
        assertEquals("", result)
    }
}
