package com.hamza.core

import com.hamza.core.utils.DateFormatter
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.assertj.core.api.Assertions.assertThat
import org.mockito.Mockito.mockStatic
import java.time.LocalDateTime

class DateFormatterTest {

    @Test
    fun `getDayName returns correct day name from valid date string`() {
        val dateString = "2025-01-05 10:00:00"
        val dayName = DateFormatter.getDayName(dateString)
        assertThat(dayName).isEqualTo("Sunday")
    }

    @Test
    fun `getDayName returns correct day name from another valid date string`() {
        val dateString = "2025-07-23 15:30:00"
        val dayName = DateFormatter.getDayName(dateString)
        assertThat(dayName).isEqualTo("Wednesday")
    }

    @Test
    fun `getDayName handles invalid date format gracefully`() {
        val invalidDateString = "2025-01-32 10:00:00"
        try {
            DateFormatter.getDayName(invalidDateString)
        } catch (e: Exception) {
            assertThat(e).isInstanceOf(java.time.format.DateTimeParseException::class.java)
        }
    }
}
