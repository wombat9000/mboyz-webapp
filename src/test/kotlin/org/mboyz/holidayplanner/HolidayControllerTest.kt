package org.mboyz.holidayplanner

import org.junit.Assert.assertTrue
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mboyz.holidayplanner.holiday.Holiday
import org.mboyz.holidayplanner.holiday.HolidayRepository
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations.initMocks
import java.time.LocalDate
import javax.servlet.http.HttpServletResponse


class HolidayControllerTest {

    lateinit var testee: HolidayController

    @Mock
    lateinit var holidayRepository: HolidayRepository
    @Mock
    lateinit var httpServletResponse: HttpServletResponse

    @Before
    fun setUp() {
        initMocks(this)
        testee = HolidayController(holidayRepository)
    }

    @Test
    fun itShouldPersistCreatedHoliday() {
        val expectedHoliday = Holiday(0L, "someName", "someLocation", LocalDate.parse("1990-12-02"), LocalDate.parse("2100-12-03"))
        `when`(holidayRepository.save(expectedHoliday)).thenReturn(expectedHoliday)

        val someHoliday = Holiday(
                name = "someName",
                location = "someLocation",
                startDate = LocalDate.parse("1990-12-02"),
                endDate = LocalDate.parse("2100-12-03"))

        val createdHoliday = testee.create(someHoliday, httpServletResponse)

        verify(holidayRepository).save(expectedHoliday)
        assertThat(createdHoliday, `is`(expectedHoliday))
    }

    @Test
    fun shouldNotPersistWhenEnddateIsBeforeStartdate() {
        val someHoliday = Holiday(
                name = "someName",
                location = "someLocation",
                startDate = LocalDate.parse("1990-12-02"),
                endDate = LocalDate.parse("1990-12-01"))

        val createdHoliday: Holiday? = testee.create(someHoliday, httpServletResponse)

        verifyZeroInteractions(holidayRepository)

        verify(httpServletResponse).status = HttpServletResponse.SC_BAD_REQUEST
        assertTrue(createdHoliday == null)
    }

    @Test
    fun shouldGetHolidayById() {
        val someId: Long = 1L
        val expectedHoliday = Holiday(
                name = "someName",
                location = "someLocation",
                startDate = LocalDate.parse("1990-12-02"),
                endDate = LocalDate.parse("2100-12-03"))

        `when`(holidayRepository.findOne(someId)).thenReturn(expectedHoliday)

        val actualHoliday = testee.get(someId, httpServletResponse)

        assertThat(actualHoliday, `is`(expectedHoliday))
    }

    @Test
    fun shouldSetContentWhenHolidayNotFound() {
        `when`(holidayRepository.findOne(ArgumentMatchers.any())).thenReturn(null)

        val actualHoliday = testee.get(1L, httpServletResponse)

        assertTrue(actualHoliday == null)
        verify(httpServletResponse).status = HttpServletResponse.SC_NO_CONTENT
    }
}