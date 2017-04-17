package org.mboyz.holidayplanner

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mboyz.holidayplanner.holiday.Holiday
import org.mboyz.holidayplanner.holiday.HolidayRepository
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations.initMocks
import java.time.LocalDate


class HolidayControllerTest {

    lateinit var testee: HolidayController

    @Mock
    lateinit var holidayRepository: HolidayRepository

    @Before
    fun setUp() {
        initMocks(this)
        testee = HolidayController(holidayRepository)
    }

    @Test
    fun itShouldPersistCreatedHoliday() {
        val expectedHoliday = Holiday(0L, "someName", "someLocation", LocalDate.parse("1990-12-02"), LocalDate.parse("2100-12-03"))
        `when`(holidayRepository.save(expectedHoliday)).thenReturn(expectedHoliday)


        val createdHoliday = testee.create("someName", "someLocation", "1990-12-02", "2100-12-03")

        verify(holidayRepository).save(expectedHoliday)
        assertThat(createdHoliday, `is`(expectedHoliday))
    }
}