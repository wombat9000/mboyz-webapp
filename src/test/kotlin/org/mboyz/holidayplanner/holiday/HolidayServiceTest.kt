package org.mboyz.holidayplanner.holiday

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verifyZeroInteractions
import org.mockito.MockitoAnnotations.initMocks
import java.time.LocalDate

class HolidayServiceTest {

    lateinit var testee: HolidayService

    @Mock
    lateinit var holidayRepository: HolidayRepository

    @Before
    fun setUp() {
    	initMocks(this)
    	testee = HolidayService(holidayRepository)
    }

    @Test
    fun shouldFindAll() {
        val someHoliday = Holiday()
        `when`(holidayRepository.findAll()).thenReturn(listOf(someHoliday))

        val result = testee.findAll()

       assertThat(result.any { it == someHoliday }, `is`(true))
    }

    @Test
    fun shouldFindSingle() {
        val someHoliday = Holiday()
        val someId: Long = 1
        `when`(holidayRepository.findOne(someId)).thenReturn(someHoliday)

        val result = testee.findOne(someId)

        assertThat(result, `is`(someHoliday))
    }

    @Test
    fun shouldSaveHoliday() {
        val someHoliday = Holiday(1, "someName", "someLocation")
        `when`(holidayRepository.save(someHoliday)).thenReturn(someHoliday)

    	val result = testee.save(someHoliday)

        assertThat(result, `is`(someHoliday))
    }

    @Test(expected = InvalidDateRangeException::class)
    fun shouldThrowExceptionIfHolidayHasInvalidDates() {
        val holidayWithInvalidDates = Holiday(
                name = "someName",
                location = "someLocation",
                startDate = LocalDate.parse("1990-12-02"),
                endDate = LocalDate.parse("1990-12-01"))

        try {
            testee.save(holidayWithInvalidDates)
        } catch (e: Exception) {
            verifyZeroInteractions(holidayRepository)
            throw(e)
        }
    }
}