package org.mboyz.holidayplanner.holiday

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mboyz.holidayplanner.holiday.persistence.HolidayEntity
import org.mboyz.holidayplanner.holiday.persistence.HolidayRepository
import org.mboyz.holidayplanner.user.persistence.UserRepository
import org.mockito.BDDMockito.*
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations.initMocks
import java.time.LocalDate

class HolidayServiceTest {

    lateinit var testee: HolidayService

    @Mock
    lateinit var userRepository: UserRepository

    @Mock
    lateinit var holidayRepository: HolidayRepository

    @Before
    fun setUp() {
        initMocks(this)
        testee = HolidayService(holidayRepository, userRepository)
    }

    @Test
    fun shouldFindAll() {
        val someHoliday = HolidayEntity()
        given(holidayRepository.findAll()).willReturn(listOf(someHoliday))

        val result = testee.findAll()

        assertThat(result.any { it == someHoliday }, `is`(true))
    }

    @Test
    fun shouldFindSingle() {
        val someHoliday = HolidayEntity()
        val someId: Long = 1
        given(holidayRepository.findOne(someId)).willReturn(someHoliday)

        val result = testee.findOne(someId)

        assertThat(result, `is`(someHoliday))
    }

    @Test(expected = HolidayNotFoundException::class)
    fun shouldThrowExceptionIfHolidayNotFound() {
        given(holidayRepository.findOne(any())).willReturn(null)
        testee.findOne(1L)
    }

    @Test
    fun shouldSaveHoliday() {
        val someHoliday = HolidayEntity(1, "someName", "someLocation")
        given(holidayRepository.save(someHoliday)).willReturn(someHoliday)

        val result = testee.save(someHoliday)

        assertThat(result, `is`(someHoliday))
    }

    @Test(expected = InvalidDateRangeException::class)
    fun shouldThrowExceptionIfHolidayHasInvalidDates() {
        val holidayWithInvalidDates = HolidayEntity(
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

    @Test
    fun shouldCreateHolidayWithSameStartAndEndDate() {
        val daytrip = HolidayEntity(
                name = "someName",
                location = "someLocation",
                startDate = LocalDate.parse("1990-12-01"),
                endDate = LocalDate.parse("1990-12-01"))
        given(holidayRepository.save(daytrip)).willReturn(daytrip)

        testee.save(daytrip)

        verify(holidayRepository).save(daytrip)
    }

    @Test
    fun shouldCreateHolidayWithoutStartDate() {
        val holidayWithoutStartDate = HolidayEntity(
                name = "someName",
                location = "someLocation",
                startDate = null,
                endDate = LocalDate.parse("1990-12-01"))
        given(holidayRepository.save(holidayWithoutStartDate)).willReturn(holidayWithoutStartDate)

        testee.save(holidayWithoutStartDate)

        verify(holidayRepository).save(holidayWithoutStartDate)
    }

    @Test
    fun shouldCreateHolidayWithoutEndDate() {
        val holidayWithoutEndDate = HolidayEntity(
                name = "someName",
                location = "someLocation",
                startDate = LocalDate.parse("1990-12-01"),
                endDate = null)
        given(holidayRepository.save(holidayWithoutEndDate)).willReturn(holidayWithoutEndDate)

        testee.save(holidayWithoutEndDate)

        verify(holidayRepository).save(holidayWithoutEndDate)
    }
}
